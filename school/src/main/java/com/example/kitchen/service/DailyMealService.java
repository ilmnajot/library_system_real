package com.example.kitchen.service;

import com.example.entity.Attachment;
import com.example.entity.Branch;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.DailyMeal;
import com.example.kitchen.model.request.DailyMealRequest;
import com.example.kitchen.model.response.DailyMealResponse;
import com.example.kitchen.model.response.DailyMealResponsePage;
import com.example.kitchen.repository.DailyMealRepository;
import com.example.model.common.ApiResponse;
import com.example.repository.AttachmentRepository;
import com.example.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyMealService implements BaseService<DailyMealRequest, Integer> {

    private final DailyMealRepository dailyMealRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final AttachmentRepository attachmentRepository;


    @Override
    public ApiResponse create(DailyMealRequest dailyMealRequest) {
        DailyMeal dailyMeal = modelMapper.map(dailyMealRequest, DailyMeal.class);
        setDailyMeal(dailyMealRequest, dailyMeal);
        dailyMealRepository.save(dailyMeal);
        DailyMealResponse response = getDailyMealResponse(dailyMeal);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        DailyMeal dailyMeal = dailyMealRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.DAILY_MEAL));
        DailyMealResponse response = getDailyMealResponse(dailyMeal);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    public ApiResponse getByAllBranchId(Integer branchId, int page, int size) {
        Page<DailyMeal> all = dailyMealRepository
                .findAllByBranchId(branchId, PageRequest.of(page, size));

        DailyMealResponsePage dailyMealResponsePage = getDailyMealResponsePage(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, dailyMealResponsePage);
    }

    public ApiResponse getByAll() {
        List<DailyMeal> all = dailyMealRepository.findAll();
        return new ApiResponse(Constants.SUCCESSFULLY, true, all);
    }


    private DailyMealResponsePage getDailyMealResponsePage(Page<DailyMeal> all) {
        List<DailyMealResponse> responses = new ArrayList<>();
        DailyMealResponsePage dailyMealResponsePage = new DailyMealResponsePage();
        dailyMealResponsePage.setDailyMealResponses(responses);
        dailyMealResponsePage.setTotalPage(all.getTotalPages());
        dailyMealResponsePage.setTotalElement(all.getTotalElements());
        all.forEach(dailyMeal -> {
            responses.add(getDailyMealResponse(dailyMeal));
        });
        return dailyMealResponsePage;
    }

    @Override
    public ApiResponse update(DailyMealRequest dailyMealRequest) {
        DailyMeal dailyMeal = dailyMealRepository.findById(dailyMealRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.DAILY_MEAL));
        updateFields(dailyMealRequest, dailyMeal);
        DailyMealResponse response = getDailyMealResponse(dailyMeal);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        DailyMeal dailyMeal = dailyMealRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.DAILY_MEAL));
        dailyMealRepository.delete(dailyMeal);
        DailyMealResponse response = getDailyMealResponse(dailyMeal);
        return new ApiResponse(Constants.DELETED, true, response);
    }

    private void updateFields(DailyMealRequest dailyMealRequest, DailyMeal dailyMeal) {
        dailyMeal.setTime(dailyMealRequest.getTime());
        dailyMeal.setName(dailyMealRequest.getName());
        dailyMeal.setDay(dailyMealRequest.getDay());
        setDailyMeal(dailyMealRequest, dailyMeal);
    }

    private DailyMealResponse getDailyMealResponse(DailyMeal dailyMeal) {
        DailyMealResponse response = modelMapper.map(dailyMeal, DailyMealResponse.class);
        if (response.getPhotoId() != null)
            response.setPhotoId(dailyMeal.getPhoto().getId());
        return response;
    }

    private void setDailyMeal(DailyMealRequest dailyMealRequest, DailyMeal dailyMeal) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(dailyMealRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.DAILY_MEAL));
        if (dailyMealRequest.getPhotoId() != null)
            attachmentRepository.findAllById(dailyMealRequest.getPhotoId()).ifPresent(dailyMeal::setPhoto);

        dailyMeal.setBranch(branch);
    }
}
