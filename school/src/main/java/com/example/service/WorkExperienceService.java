package com.example.service;

import com.example.entity.WorkExperience;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.WorkExperienceRequest;
import com.example.model.response.UserResponse;
import com.example.model.response.WorkExperienceResponse;
import com.example.model.response.WorkExperienceResponsePage;
import com.example.repository.UserRepository;
import com.example.repository.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkExperienceService implements BaseService<WorkExperienceRequest, Integer> {

    private final WorkExperienceRepository workExperienceRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse create(WorkExperienceRequest workExperienceRequest) {
        checkIfExist(workExperienceRequest);
        WorkExperience workExperience = modelMapper.map(workExperienceRequest,WorkExperience.class);
        workExperience.setEmployee(userRepository.findById(workExperienceRequest.getEmployeeId())
                .orElseThrow(() -> new UserNotFoundException(Constants.USER_NOT_FOUND)));
        workExperienceRepository.save(workExperience);
        WorkExperienceResponse response = getWorkExperienceResponse(workExperience);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer id) {
        WorkExperience workExperience = checkById(id);
        WorkExperienceResponse response = getWorkExperienceResponse(workExperience);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse update(WorkExperienceRequest workExperienceRequest) {
        checkById(workExperienceRequest.getId());
        WorkExperience workExperience = modelMapper.map(workExperienceRequest,WorkExperience.class);
        workExperience.setEmployee(userRepository.findById(workExperienceRequest.getEmployeeId())
                .orElseThrow(() -> new UserNotFoundException(Constants.USER_NOT_FOUND)));
        workExperience.setId(workExperienceRequest.getId());
        workExperienceRepository.save(workExperience);
        WorkExperienceResponse response = getWorkExperienceResponse(workExperience);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse delete(Integer id) {
        WorkExperience workExperience = checkById(id);
        workExperienceRepository.deleteById(id);
        WorkExperienceResponse response = getWorkExperienceResponse(workExperience);
        return new ApiResponse(Constants.DELETED, true, response);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllByUserId(Integer id, int page, int size) {
        Page<WorkExperience> all = workExperienceRepository
                .findAllByEmployeeId(id, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        WorkExperienceResponsePage allResponse = getWorkExperienceResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, allResponse);
    }

    private WorkExperienceResponsePage getWorkExperienceResponses(Page<WorkExperience> all) {
        WorkExperienceResponsePage page = new WorkExperienceResponsePage();
        List<WorkExperienceResponse> allResponse = new ArrayList<>();
        all.forEach(workExperience -> {
            allResponse.add(getWorkExperienceResponse(workExperience));
        });
        page.setWorkExperienceResponses(allResponse);
        page.setTotalPage(all.getTotalPages());
        page.setTotalElement(all.getTotalElements());
        return page;
    }

    private void checkIfExist(WorkExperienceRequest workExperienceRequest) {
        boolean present = workExperienceRepository
                .findByPlaceOfWorkAndPositionAndEmployeeIdAndStartDateAndEndDate(
                        workExperienceRequest.getPlaceOfWork(),
                        workExperienceRequest.getPosition(),
                        workExperienceRequest.getEmployeeId(),
                        workExperienceRequest.getStartDate(),
                        workExperienceRequest.getEndDate()).isPresent();
        if (present) {
            throw new RecordAlreadyExistException(Constants.WORK_EXPERIENCE_ALREADY_EXIST);
        }
    }

    public WorkExperience checkById(Integer id) {
        return workExperienceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Constants.WORK_EXPERIENCE_NOT_FOUND));
    }

    private WorkExperienceResponse getWorkExperienceResponse(WorkExperience workExperience) {
        WorkExperienceResponse response = modelMapper.map(workExperience, WorkExperienceResponse.class);
        response.setStartDate(workExperience.getStartDate().toString());
        response.setEndDate(workExperience.getEndDate().toString());
        response.setUserResponse(modelMapper.map(workExperience.getEmployee(), UserResponse.class));
        return response;
    }
}
