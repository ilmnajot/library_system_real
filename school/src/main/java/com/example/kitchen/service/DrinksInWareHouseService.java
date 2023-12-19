package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.DailyConsumedDrinks;
import com.example.kitchen.entity.DrinksInWareHouse;
import com.example.kitchen.entity.PurchasedDrinks;
import com.example.kitchen.entity.Warehouse;
import com.example.kitchen.model.response.DrinksInWareHouseResponse;
import com.example.kitchen.model.request.DailyConsumedDrinksRequest;
import com.example.kitchen.model.request.DrinksInWareHouseRequest;
import com.example.kitchen.model.request.PurchasedDrinksRequest;
import com.example.kitchen.model.response.DrinksInWareHouseResponsePage;
import com.example.kitchen.repository.DrinksInWareHouseRepository;
import com.example.kitchen.repository.WareHouseRepository;
import com.example.model.common.ApiResponse;
import com.example.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DrinksInWareHouseService {

    private final ModelMapper modelMapper;
    private final BranchRepository branchRepository;
    private final WareHouseRepository wareHouseRepository;
    private final DrinksInWareHouseRepository drinksInWareHouseRepository;

    public DrinksInWareHouse create(DrinksInWareHouseRequest request) {
        DrinksInWareHouse drinksInWareHouse = modelMapper.map(request, DrinksInWareHouse.class);
        setDrinksWarehouse(request, drinksInWareHouse);
        return drinksInWareHouseRepository.save(drinksInWareHouse);
    }


    public void storageOfPurchasedDrinks(PurchasedDrinksRequest request) {
        Optional<DrinksInWareHouse> drinksInWareHouseOptional = drinksInWareHouseRepository
                .findByNameAndLiterQuantityAndBranchIdAndWarehouseId(
                        request.getName(),
                        request.getLiterQuantity(),
                        request.getBranchId(),
                        request.getWarehouseId());

        if (drinksInWareHouseOptional.isPresent()) {
            DrinksInWareHouse drinksInWareHouse = drinksInWareHouseOptional.get();
            drinksInWareHouse.setActive(true);
            drinksInWareHouse.setCount(drinksInWareHouse.getCount() + request.getCount());
            drinksInWareHouseRepository.save(drinksInWareHouse);
        } else {
            DrinksInWareHouseRequest drinksInWareHouseRequest =
                    modelMapper.map(request, DrinksInWareHouseRequest.class);
            create(drinksInWareHouseRequest);
        }
    }

    public void rollBackPurchasedDrinks(PurchasedDrinks purchasedDrinks) {
        DrinksInWareHouse drinksInWareHouse = getDrinksInWareHouse(
                purchasedDrinks.getName(),
                purchasedDrinks.getLiterQuantity(),
                purchasedDrinks.getBranch().getId(),
                purchasedDrinks.getWarehouse().getId());

        reduceCount(drinksInWareHouse, purchasedDrinks.getCount());
        drinksInWareHouseRepository.save(drinksInWareHouse);
    }

    public void consumedDrinksFromWarehouse(DailyConsumedDrinksRequest request) {
        DrinksInWareHouse drinksInWareHouse = getDrinksInWareHouse(
                request.getName(),
                request.getLiterQuantity(),
                request.getBranchId(),
                request.getWarehouseId());

        reduceCount(drinksInWareHouse, request.getCount());
        drinksInWareHouseRepository.save(drinksInWareHouse);
    }

    public void rollbackConsumedDrinksToWarehouse(DailyConsumedDrinks old) {
        DrinksInWareHouse drinksInWareHouse = getDrinksInWareHouse(
                old.getName(),
                old.getLiterQuantity(),
                old.getBranch().getId(),
                old.getWarehouse().getId());

        drinksInWareHouse.setActive(true);
        drinksInWareHouse.setCount(drinksInWareHouse.getCount() + old.getCount());
        drinksInWareHouseRepository.save(drinksInWareHouse);
    }

    public ApiResponse getAllByWarehouseId(Integer warehouseId, int page, int size) {
        Page<DrinksInWareHouse> all = drinksInWareHouseRepository
                .findAllByWarehouseIdAndActiveTrue(warehouseId, PageRequest.of(page, size));
        DrinksInWareHouseResponsePage responses = getDrinksInWareHouseResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, responses);
    }

    public ApiResponse getAllByBranchId(Integer branchId, int page, int size) {
        Page<DrinksInWareHouse> all = drinksInWareHouseRepository
                .findAllByBranchIdAndActiveTrue(branchId, PageRequest.of(page, size));
        DrinksInWareHouseResponsePage response = getDrinksInWareHouseResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    public ApiResponse getById(Integer drinksInWarehouseId) {
        DrinksInWareHouse drinksInWareHouse = drinksInWareHouseRepository.findByIdAndActiveTrue(drinksInWarehouseId)
                .orElseThrow(() -> new RecordNotFoundException(Constants.DRINKS_IN_WAREHOUSE_NOT_FOUND));
        DrinksInWareHouseResponse response =
                modelMapper.map(drinksInWareHouse, DrinksInWareHouseResponse.class);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }


    private static void reduceCount(DrinksInWareHouse drinksInWareHouse, int count) {
        if (drinksInWareHouse.getCount() >= count) {
            drinksInWareHouse.setCount(drinksInWareHouse.getCount() - count);
        } else {
            throw new RecordNotFoundException(Constants.DRINKS_IN_WAREHOUSE_NOT_ENOUGH);
        }
        if (drinksInWareHouse.getCount() == 0) {
            drinksInWareHouse.setActive(false);
        }
    }

    private DrinksInWareHouse getDrinksInWareHouse(String name,
                                                   int literQuantity,
                                                   Integer branchId,
                                                   Integer warehouseId) {
        return drinksInWareHouseRepository
                .findByNameAndLiterQuantityAndBranchIdAndWarehouseId(
                        name,
                        literQuantity,
                        branchId,
                        warehouseId)
                .orElseThrow(() -> new RecordNotFoundException(Constants.DRINKS_IN_WAREHOUSE_NOT_FOUND));
    }

    private DrinksInWareHouseResponsePage getDrinksInWareHouseResponses(Page<DrinksInWareHouse> all) {
        DrinksInWareHouseResponsePage page = new DrinksInWareHouseResponsePage();
        List<DrinksInWareHouseResponse> response = new ArrayList<>();
        all.forEach(drinksInWareHouse -> {
            DrinksInWareHouseResponse houseResponse =
                    modelMapper.map(drinksInWareHouse, DrinksInWareHouseResponse.class);
            response.add(houseResponse);
        });
        page.setDrinksInWareHouseResponses(response);
        page.setTotalPage(all.getTotalPages());
        page.setTotalElement(all.getTotalElements());
        return page;
    }

    private void setDrinksWarehouse(DrinksInWareHouseRequest request, DrinksInWareHouse drinksInWareHouse) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(request.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        Warehouse warehouse = wareHouseRepository.findByIdAndActiveTrue(request.getWarehouseId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.WAREHOUSE_NOT_FOUND));

        drinksInWareHouse.setActive(true);
        drinksInWareHouse.setBranch(branch);
        drinksInWareHouse.setWarehouse(warehouse);
    }
}
