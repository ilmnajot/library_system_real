package com.example.service;

import com.example.entity.Branch;
import com.example.entity.Family;
import com.example.entity.Student;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.FamilyRequest;
import com.example.model.response.FamilyResponse;
import com.example.model.response.FamilyResponsePage;
import com.example.model.response.StudentResponse;
import com.example.repository.BranchRepository;
import com.example.repository.FamilyRepository;
import com.example.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.enums.Constants.*;

@RequiredArgsConstructor
@Service
public class FamilyService implements BaseService<FamilyRequest, Integer> {

    private final FamilyRepository familyRepository;
    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse create(FamilyRequest familyRequest) {
        Family family = modelMapper.map(familyRequest, Family.class);
        setFamily(familyRequest, family);
        familyRepository.save(family);
        FamilyResponse response = getFamilyResponse(family);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    private FamilyResponse getFamilyResponse(Family family) {
        FamilyResponse response = modelMapper.map(family, FamilyResponse.class);
        response.setStudentResponses(getStudentResponses(family.getStudents()));
        response.setCreatedDate(family.getRegisteredDate().toString());
        return response;
    }

    private void setFamily(FamilyRequest familyRequest, Family family) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(familyRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        List<Student> students = studentRepository
                .findAllByIdInAndActiveTrue(familyRequest.getStudentIdList());

        family.setActive(true);
        family.setBranch(branch);
        family.setStudents(students);
        family.setRegisteredDate(LocalDateTime.now());
    }

    @Override
    public ApiResponse getById(Integer integer) {
        Family family = familyRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new UserNotFoundException(FAMILY_NOT_FOUND));
        FamilyResponse response = getFamilyResponse(family);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse update(FamilyRequest familyRequest) {
        Family old = familyRepository.findByIdAndActiveTrue(familyRequest.getId())
                .orElseThrow(() -> new UserNotFoundException(FAMILY_NOT_FOUND));
        Family family = modelMapper.map(familyRequest, Family.class);
        setFamily(familyRequest, family);
        family.setId(familyRequest.getId());
        family.setFireBaseToken(old.getFireBaseToken());
        familyRepository.save(family);
        FamilyResponse response = getFamilyResponse(family);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        Family family = familyRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new UserNotFoundException(FAMILY_NOT_FOUND));
        familyRepository.delete(family);
        FamilyResponse response = getFamilyResponse(family);
        return new ApiResponse(DELETED, true, response);
    }


    public ApiResponse getList(int page, int size, int branchId) {
        Page<Family> all = familyRepository
                .findAllByBranchIdAndActiveTrue(branchId, PageRequest.of(page, size));
        FamilyResponsePage response = getFamilyResponsePage(all);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    private FamilyResponsePage getFamilyResponsePage(Page<Family> all) {
        List<FamilyResponse> familyResponses = new ArrayList<>();
        all.forEach(family -> {
            familyResponses.add(getFamilyResponse(family));
        });
        FamilyResponsePage familyResponsePage = new FamilyResponsePage();
        familyResponsePage.setFamilyResponseDtoList(familyResponses);
        familyResponsePage.setTotalPage(all.getTotalPages());
        familyResponsePage.setTotalElement(all.getTotalElements());

        return familyResponsePage;
    }

    public ApiResponse familyLogIn(String phoneNumber, String password) {
        Family family = familyRepository.findByPhoneNumberAndPassword(phoneNumber, password)
                .orElseThrow(() -> new UserNotFoundException(FAMILY_NOT_FOUND));
        FamilyResponse response = getFamilyResponse(family);
        return new ApiResponse(SUCCESSFULLY, true, response
        );
    }

    private List<Integer> getStudentResponses(List<Student> students) {
        List<Integer> responses = new ArrayList<>();
        students.forEach(student -> {
            responses.add(student.getId());
        });
        return responses;
    }
}