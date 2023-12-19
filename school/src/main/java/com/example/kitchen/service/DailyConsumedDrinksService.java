package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.entity.User;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.DailyConsumedDrinks;
import com.example.kitchen.entity.Warehouse;
import com.example.kitchen.model.response.DailyConsumedDrinksResponse;
import com.example.kitchen.model.request.DailyConsumedDrinksRequest;
import com.example.kitchen.model.response.DailyConsumedDrinksResponsePage;
import com.example.kitchen.repository.DailyConsumedDrinksRepository;
import com.example.kitchen.repository.WareHouseRepository;
import com.example.model.common.ApiResponse;
import com.example.repository.BranchRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyConsumedDrinksService implements BaseService<DailyConsumedDrinksRequest, Integer> {

    private final DailyConsumedDrinksRepository dailyConsumedDrinksRepository;
    private final DrinksInWareHouseService drinksInWareHouseService;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final WareHouseRepository wareHouseRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = {RecordNotFoundException.class, Exception.class})
    public ApiResponse create(DailyConsumedDrinksRequest request) {
        drinksInWareHouseService.consumedDrinksFromWarehouse(request);
        DailyConsumedDrinks dailyConsumedDrinks = modelMapper.map(request, DailyConsumedDrinks.class);
        setDailyConsumedDrinks(request, dailyConsumedDrinks);
        dailyConsumedDrinksRepository.save(dailyConsumedDrinks);
        DailyConsumedDrinksResponse response = getResponse(dailyConsumedDrinks);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        DailyConsumedDrinks dailyConsumedDrinks = getByID(integer);
        DailyConsumedDrinksResponse response = getResponse(dailyConsumedDrinks);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    @Transactional(rollbackFor = {RecordNotFoundException.class, Exception.class})
    public ApiResponse update(DailyConsumedDrinksRequest request) {
        updateWarehouse(request);
        DailyConsumedDrinks dailyConsumedDrinks = modelMapper.map(request, DailyConsumedDrinks.class);
        dailyConsumedDrinks.setId(request.getId());
        setDailyConsumedDrinks(request, dailyConsumedDrinks);
        dailyConsumedDrinksRepository.save(dailyConsumedDrinks);
        DailyConsumedDrinksResponse response = getResponse(dailyConsumedDrinks);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        DailyConsumedDrinks dailyConsumedDrinks = getByID(integer);
        drinksInWareHouseService.rollbackConsumedDrinksToWarehouse(dailyConsumedDrinks);
        dailyConsumedDrinks.setDelete(true);
        dailyConsumedDrinksRepository.save(dailyConsumedDrinks);
        DailyConsumedDrinksResponse response = getResponse(dailyConsumedDrinks);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    public ApiResponse getAllByBranchId(Integer branchId, int page, int size) {
        Page<DailyConsumedDrinks> all = dailyConsumedDrinksRepository
                .findAllByBranch_IdAndDeleteFalse(branchId, PageRequest.of(page, size));
        DailyConsumedDrinksResponsePage allResponse = toAllResponse(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, allResponse);
    }

    public ApiResponse getAllByWarehouseId(Integer warehouseId, int page, int size) {
        Page<DailyConsumedDrinks> all = dailyConsumedDrinksRepository
                .findAllByWarehouseIdAndDeleteFalse(warehouseId, PageRequest.of(page, size));
        DailyConsumedDrinksResponsePage allResponse = toAllResponse(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, allResponse);
    }

    private DailyConsumedDrinksResponsePage toAllResponse(Page<DailyConsumedDrinks> all) {
        DailyConsumedDrinksResponsePage responsePage = new DailyConsumedDrinksResponsePage();
        responsePage.setTotalElement(all.getTotalElements());
        responsePage.setTotalPage(all.getTotalPages());

        List<DailyConsumedDrinksResponse> drinksResponses = new ArrayList<>();
        all.forEach(dailyConsumedDrinks -> {
            DailyConsumedDrinksResponse response = getResponse(dailyConsumedDrinks);
            drinksResponses.add(response);
        });
        responsePage.setResponseList(drinksResponses);
        return responsePage;
    }

    private DailyConsumedDrinksResponse getResponse(DailyConsumedDrinks dailyConsumedDrinks) {
        DailyConsumedDrinksResponse response =
                modelMapper.map(dailyConsumedDrinks, DailyConsumedDrinksResponse.class);
        response.setLocalDateTime(dailyConsumedDrinks.getLocalDateTime().toString());
        return response;
    }

    private void setDailyConsumedDrinks(DailyConsumedDrinksRequest request, DailyConsumedDrinks dailyConsumedDrinks) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(request.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        Warehouse warehouse = wareHouseRepository.findByIdAndActiveTrue(request.getWarehouseId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.WAREHOUSE_NOT_FOUND));
        User user = userRepository.findByIdAndBlockedFalse(request.getEmployeeId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND));

        dailyConsumedDrinks.setLocalDateTime(LocalDateTime.now());
        dailyConsumedDrinks.setDelete(false);
        dailyConsumedDrinks.setBranch(branch);
        dailyConsumedDrinks.setWarehouse(warehouse);
        dailyConsumedDrinks.setEmployee(user);
    }

    private void updateWarehouse(DailyConsumedDrinksRequest request) {
        DailyConsumedDrinks old = getByID(request.getId());
        drinksInWareHouseService.rollbackConsumedDrinksToWarehouse(old);
        drinksInWareHouseService.consumedDrinksFromWarehouse(request);
    }

    private DailyConsumedDrinks getByID(Integer integer) {
        return dailyConsumedDrinksRepository
                .findByIdAndDeleteFalse(integer).orElseThrow(() -> new RecordNotFoundException(Constants.DAILY_DRINKS_CONSUMED_NOT_FOUND));
    }
}
