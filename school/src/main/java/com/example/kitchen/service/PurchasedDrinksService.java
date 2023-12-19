package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.entity.User;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.PurchasedDrinks;
import com.example.kitchen.entity.Warehouse;
import com.example.kitchen.model.response.PurchasedDrinksResponse;
import com.example.kitchen.model.request.PurchasedDrinksRequest;
import com.example.kitchen.model.response.PurchasedDrinksResponsePage;
import com.example.kitchen.repository.PurchasedDrinksRepository;
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
public class PurchasedDrinksService implements BaseService<PurchasedDrinksRequest, Integer> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BranchRepository branchRepository;
    private final WareHouseRepository wareHouseRepository;
    private final DrinksInWareHouseService drinksInWareHouseService;
    private final PurchasedDrinksRepository purchasedDrinksRepository;

    @Override
    public ApiResponse create(PurchasedDrinksRequest request) {
        drinksInWareHouseService.storageOfPurchasedDrinks(request);
        PurchasedDrinks purchasedDrinks = modelMapper.map(request, PurchasedDrinks.class);
        setPurchasedDrinks(request, purchasedDrinks);
        purchasedDrinksRepository.save(purchasedDrinks);
        PurchasedDrinksResponse response = getResponse(purchasedDrinks);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer id) {
        PurchasedDrinks purchasedDrinks = getByPurchasedDrinksId(id);
        PurchasedDrinksResponse response = getResponse(purchasedDrinks);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RecordNotFoundException.class})
    public ApiResponse update(PurchasedDrinksRequest request) {
        updateWareHouse(request);
        PurchasedDrinks purchasedDrinks = modelMapper.map(request, PurchasedDrinks.class);
        purchasedDrinks.setId(request.getId());
        setPurchasedDrinks(request, purchasedDrinks);
        purchasedDrinksRepository.save(purchasedDrinks);
        PurchasedDrinksResponse response = getResponse(purchasedDrinks);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    private void updateWareHouse(PurchasedDrinksRequest request) {
        PurchasedDrinks oldPurchasedDrinks = getByPurchasedDrinksId(request.getId());
        drinksInWareHouseService.rollBackPurchasedDrinks(oldPurchasedDrinks);
        drinksInWareHouseService.storageOfPurchasedDrinks(request);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RecordNotFoundException.class})
    public ApiResponse delete(Integer integer) {
        PurchasedDrinks purchasedDrinks = getByPurchasedDrinksId(integer);
        drinksInWareHouseService.rollBackPurchasedDrinks(purchasedDrinks);
        purchasedDrinks.setDelete(true);
        purchasedDrinksRepository.save(purchasedDrinks);
        PurchasedDrinksResponse response = getResponse(purchasedDrinks);
        return new ApiResponse(Constants.DELETED, true, response);
    }

    public ApiResponse getAllByBranchId(Integer branchId, int page, int size) {
        Page<PurchasedDrinks> all = purchasedDrinksRepository
                .findAllByBranch_IdAndDeleteFalse(branchId, PageRequest.of(page, size));
        PurchasedDrinksResponsePage drinksResponses = getDrinksResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, drinksResponses);
    }

    public ApiResponse getAllByWarehouseId(Integer warehouseId, int page, int size) {
        Page<PurchasedDrinks> all = purchasedDrinksRepository
                .findAllByWarehouseIdAndDeleteFalse(warehouseId, PageRequest.of(page, size));
        PurchasedDrinksResponsePage responses = getDrinksResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, responses);
    }

    private PurchasedDrinksResponsePage getDrinksResponses(Page<PurchasedDrinks> all) {
        PurchasedDrinksResponsePage responsePage = new PurchasedDrinksResponsePage();
        responsePage.setTotalPage(all.getTotalPages());
        responsePage.setTotalElements(all.getTotalElements());

        List<PurchasedDrinksResponse> responses = new ArrayList<>();
        all.forEach(purchasedDrinks -> {
            PurchasedDrinksResponse response = getResponse(purchasedDrinks);
            responses.add(response);
        });
        responsePage.setPurchasedDrinksResponses(responses);
        return responsePage;
    }

    private PurchasedDrinksResponse getResponse(PurchasedDrinks purchasedDrinks) {
        PurchasedDrinksResponse response = modelMapper.map(purchasedDrinks, PurchasedDrinksResponse.class);
        response.setLocalDateTime(purchasedDrinks.getLocalDateTime().toString());
        return response;
    }

    private void setPurchasedDrinks(PurchasedDrinksRequest request, PurchasedDrinks purchasedDrinks) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(request.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        User user = userRepository.findByIdAndBlockedFalse(request.getEmployeeId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND));
        Warehouse warehouse = wareHouseRepository.findByIdAndActiveTrue(request.getWarehouseId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.WAREHOUSE_NOT_FOUND));

        purchasedDrinks.setDelete(false);
        purchasedDrinks.setBranch(branch);
        purchasedDrinks.setEmployee(user);
        purchasedDrinks.setWarehouse(warehouse);
        purchasedDrinks.setLocalDateTime(LocalDateTime.now());
    }

    private PurchasedDrinks getByPurchasedDrinksId(Integer integer) {
        return purchasedDrinksRepository.findByIdAndDeleteFalse(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.PURCHASED_DRINKS_NOT_FOUND));
    }
}
