package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.entity.User;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.DailyConsumedProducts;
import com.example.kitchen.entity.Warehouse;
import com.example.kitchen.model.response.DailyConsumedProductsResponse;
import com.example.kitchen.model.request.DailyConsumedProductsRequest;
import com.example.kitchen.model.response.DailyConsumedProductsResponsePage;
import com.example.kitchen.repository.DailyConsumedProductsRepository;
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
public class DailyConsumedProductsService implements BaseService<DailyConsumedProductsRequest, Integer> {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final WareHouseRepository wareHouseRepository;
    private final ProductsInWareHouseService productsInWareHouseService;
    private final DailyConsumedProductsRepository dailyConsumedProductsRepository;

    @Override
    @Transactional(rollbackFor = {RecordNotFoundException.class, Exception.class})
    public ApiResponse create(DailyConsumedProductsRequest request) {
        DailyConsumedProducts consumedProducts = getDailyConsumedProducts(request);
        setConsumedProducts(request, consumedProducts);
        dailyConsumedProductsRepository.save(consumedProducts);
        DailyConsumedProductsResponse response = getResponse(consumedProducts);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        DailyConsumedProducts dailyConsumedProducts = getByID(integer);
        DailyConsumedProductsResponse response = getResponse(dailyConsumedProducts);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    @Transactional(rollbackFor = {RecordNotFoundException.class, Exception.class})
    public ApiResponse update(DailyConsumedProductsRequest request) {
        updateWareHouse(request);
        DailyConsumedProducts consumedProducts = modelMapper.map(request, DailyConsumedProducts.class);
        consumedProducts.setId(request.getId());
        setConsumedProducts(request, consumedProducts);
        dailyConsumedProductsRepository.save(consumedProducts);
        DailyConsumedProductsResponse response = getResponse(consumedProducts);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        DailyConsumedProducts dailyConsumedProducts = getByID(integer);
        productsInWareHouseService.rollBackConsumedProducts(dailyConsumedProducts);
        dailyConsumedProducts.setDelete(true);
        dailyConsumedProductsRepository.save(dailyConsumedProducts);
        DailyConsumedProductsResponse response = getResponse(dailyConsumedProducts);
        return new ApiResponse(Constants.DELETED, true, response);
    }

    public ApiResponse getAllByWarehouseId(Integer warehouseId, int page, int size) {
        Page<DailyConsumedProducts> all = dailyConsumedProductsRepository
                .findAllByWarehouseIdAndDeleteFalse(warehouseId, PageRequest.of(page, size));
        DailyConsumedProductsResponsePage responses = toAllResponse(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, responses);
    }

    public ApiResponse getAllByBranchId(Integer branchId, int page, int size) {
        Page<DailyConsumedProducts> all = dailyConsumedProductsRepository
                .findAllByBranchIdAndDeleteFalse(branchId, PageRequest.of(page, size));
        DailyConsumedProductsResponsePage responses = toAllResponse(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, responses);
    }

    private DailyConsumedProductsResponsePage toAllResponse(Page<DailyConsumedProducts> all) {
        DailyConsumedProductsResponsePage responsePage = new DailyConsumedProductsResponsePage();
        responsePage.setTotalPage(all.getTotalPages());
        responsePage.setTotalElements(all.getTotalElements());

        List<DailyConsumedProductsResponse> responses = new ArrayList<>();
        all.forEach(dailyConsumedProducts -> {
            DailyConsumedProductsResponse response = getResponse(dailyConsumedProducts);
            responses.add(response);
        });
        responsePage.setDailyConsumedProductsResponses(responses);
        return responsePage;
    }

    private void updateWareHouse(DailyConsumedProductsRequest request) {
        DailyConsumedProducts old = getByID(request.getId());
        productsInWareHouseService.rollBackConsumedProducts(old);
        productsInWareHouseService.consumedProducts(request);
    }

    private DailyConsumedProductsResponse getResponse(DailyConsumedProducts consumedProducts) {
        DailyConsumedProductsResponse response =
                modelMapper.map(consumedProducts, DailyConsumedProductsResponse.class);
        response.setLocalDateTime(consumedProducts.getLocalDateTime().toString());
        return response;
    }

    private void setConsumedProducts(DailyConsumedProductsRequest request, DailyConsumedProducts consumedProducts) {
        User user = userRepository.findByIdAndBlockedFalse(request.getEmployeeId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND));
        Branch branch = branchRepository.findByIdAndDeleteFalse(request.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        Warehouse warehouse = wareHouseRepository.findByIdAndActiveTrue(request.getWarehouseId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.WAREHOUSE_NOT_FOUND));

        consumedProducts.setDelete(false);
        consumedProducts.setBranch(branch);
        consumedProducts.setEmployee(user);
        consumedProducts.setWarehouse(warehouse);
        consumedProducts.setLocalDateTime(LocalDateTime.now());
    }

    private DailyConsumedProducts getDailyConsumedProducts(DailyConsumedProductsRequest request) {
        productsInWareHouseService.consumedProducts(request);
        return modelMapper.map(request, DailyConsumedProducts.class);
    }

    private DailyConsumedProducts getByID(Integer integer) {
        return dailyConsumedProductsRepository.findByIdAndDeleteFalse(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.CONSUMED_PRODUCTS_NOT_FOUND));
    }
}
