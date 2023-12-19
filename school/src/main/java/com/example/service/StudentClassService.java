package com.example.service;

import com.example.entity.*;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.StudentClassRequest;
import com.example.model.response.StudentClassResponse;
import com.example.model.response.UserResponse;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class StudentClassService implements BaseService<StudentClassRequest, Integer> {

    private final StudentClassRepository studentClassRepository;
    private final BranchRepository branchRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final LevelRepository levelRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse create(StudentClassRequest studentClassRequest) {
        StudentClass studentClass = modelMapper.map(studentClassRequest, StudentClass.class);
        setStudentClass(studentClassRequest, studentClass);
        studentClassRepository.save(studentClass);
        StudentClassResponse response = getStudentClassResponse(studentClass);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        StudentClass studentClass = studentClassRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));
        StudentClassResponse response = getStudentClassResponse(studentClass);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse update(StudentClassRequest studentClassRequest) {
        StudentClass studentClass = studentClassRepository.findById(studentClassRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));
        setStudentClass(studentClassRequest, studentClass);
        studentClassRepository.save(studentClass);
        StudentClassResponse response = getStudentClassResponse(studentClass);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        StudentClass studentClass = studentClassRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));
        studentClass.setActive(false);
        studentClassRepository.save(studentClass);
        StudentClassResponse response = getStudentClassResponse(studentClass);
        return new ApiResponse(DELETED, true, response);
    }

    public ApiResponse getAllActiveClasses(Integer branchId) {
        List<StudentClass> allByActiveTrue = studentClassRepository
                .findAllByActiveTrueAndBranchId(branchId, Sort.by(Sort.Direction.DESC, "id"));
        List<StudentClassResponse> allResponse = getStudentClassResponses(allByActiveTrue);
        return new ApiResponse(SUCCESSFULLY, true, allResponse);
    }

    private List<StudentClassResponse> getStudentClassResponses(List<StudentClass> allByActiveTrue) {
        List<StudentClassResponse> allResponse = new ArrayList<>();
        allByActiveTrue.forEach(studentClass -> {
            allResponse.add(getStudentClassResponse(studentClass));
        });
        return allResponse;
    }

    public ApiResponse getAllNeActiveClassesByYear(LocalDate startDate, LocalDate endDate, int id) {
        List<StudentClass> all = studentClassRepository
                .findAllByBranchIdAndStartDateAfterAndEndDateBeforeAndActiveFalse(
                        id, startDate, endDate,
                        Sort.by(Sort.Direction.DESC, "id"));
        List<StudentClassResponse> responses = getStudentClassResponses(all);
        return new ApiResponse(SUCCESSFULLY, true, responses);
    }

    private void setStudentClass(StudentClassRequest studentClassRequest, StudentClass studentClass) {
        Branch branch = branchRepository.findById(studentClassRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        Room room = roomRepository.findById(studentClassRequest.getRoomId())
                .orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
        User teacher = userRepository.findById(studentClassRequest.getClassLeaderId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        Level level = levelRepository.findById(studentClassRequest.getLevelId())
                .orElseThrow(() -> new RecordNotFoundException(LEVEL_NOT_FOUND));

        studentClass.setCreatedDate(LocalDateTime.now());
        studentClass.setActive(true);
        studentClass.setBranch(branch);
        studentClass.setRoom(room);
        studentClass.setClassLeader(teacher);
        studentClass.setLevel(level);
        studentClass.setOverallSum(studentClassRequest.getOverallSum());
    }

    private StudentClassResponse getStudentClassResponse(StudentClass studentClass) {
        StudentClassResponse response = modelMapper.map(studentClass, StudentClassResponse.class);
        UserResponse userResponse = modelMapper.map(studentClass.getClassLeader(), UserResponse.class);
        response.setClassLeader(userResponse);
        response.setStartDate(studentClass.getStartDate().toString());
        response.setEndDate(studentClass.getEndDate().toString());
        return response;
    }
}
