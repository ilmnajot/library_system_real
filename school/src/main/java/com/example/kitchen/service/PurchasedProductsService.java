package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.entity.User;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.PurchasedProducts;
import com.example.kitchen.entity.Warehouse;
import com.example.kitchen.model.response.PurchasedProductsResponse;
import com.example.kitchen.model.request.PurchasedProductsRequest;
import com.example.kitchen.model.response.PurchasedProductsResponsePage;
import com.example.kitchen.repository.PurchasedProductsRepository;
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
public class PurchasedProductsService implements BaseService<PurchasedProductsRequest, Integer> {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final WareHouseRepository wareHouseRepository;
    private final PurchasedProductsRepository purchasedProductsRepository;
    private final ProductsInWareHouseService productsInWareHouseService;

    @Override
    @Transactional(rollbackFor = {Exception.class, RecordNotFoundException.class})
    public ApiResponse create(PurchasedProductsRequest request) {
        PurchasedProducts purchasedProducts = modelMapper.map(request, PurchasedProducts.class);
        setPurchasedProducts(request, purchasedProducts);
        purchasedProductsRepository.save(purchasedProducts);
        productsInWareHouseService.storageOfPurchasedProducts(request);
        PurchasedProductsResponse response = getResponse(purchasedProducts);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        PurchasedProducts purchasedProducts = getByPurchasedProductId(integer);
        PurchasedProductsResponse response = getResponse(purchasedProducts);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RecordNotFoundException.class})
    public ApiResponse update(PurchasedProductsRequest request) {
        updateWarehouse(request);
        PurchasedProducts purchasedProducts = modelMapper.map(request, PurchasedProducts.class);
        purchasedProducts.setId(request.getId());
        setPurchasedProducts(request, purchasedProducts);
        purchasedProductsRepository.save(purchasedProducts);
        PurchasedProductsResponse response = getResponse(purchasedProducts);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    private void updateWarehouse(PurchasedProductsRequest request) {
            PurchasedProducts oldPurchasedProducts = getByPurchasedProductId(request.getId());
            productsInWareHouseService.rollBackPurchasedProductsFromWarehouse(oldPurchasedProducts);
            productsInWareHouseService.storageOfPurchasedProducts(request);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RecordNotFoundException.class})
    public ApiResponse delete(Integer integer) {
        PurchasedProducts purchasedProducts = getByPurchasedProductId(integer);
        purchasedProducts.setDelete(true);
        purchasedProductsRepository.save(purchasedProducts);
        productsInWareHouseService.rollBackPurchasedProductsFromWarehouse(purchasedProducts);
        PurchasedProductsResponse response = getResponse(purchasedProducts);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    private void setPurchasedProducts(PurchasedProductsRequest request, PurchasedProducts purchasedProducts) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(request.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        User user = userRepository.findByIdAndBlockedFalse(request.getEmployeeId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND));
        Warehouse warehouse = wareHouseRepository.findByIdAndActiveTrue(request.getWarehouseId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.WAREHOUSE_NOT_FOUND));

        purchasedProducts.setDelete(false);
        purchasedProducts.setBranch(branch);
        purchasedProducts.setEmployee(user);
        purchasedProducts.setWarehouse(warehouse);
        purchasedProducts.setLocalDateTime(LocalDateTime.now());
    }

    public ApiResponse getAllByWarehouseId(Integer warehouseId, int page, int size) {
        Page<PurchasedProducts> all = purchasedProductsRepository
                .findAllByWarehouseIdAndDeleteFalse(warehouseId, PageRequest.of(page, size));
        PurchasedProductsResponsePage responses = getPurchasedProductsResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, responses);
    }

    public ApiResponse getAllByBranchId(Integer branchId, int page, int size) {
        Page<PurchasedProducts> all = purchasedProductsRepository
                .findAllByBranch_IdAndDeleteFalse(branchId, PageRequest.of(page, size));
        PurchasedProductsResponsePage responses = getPurchasedProductsResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, responses);
    }

    private PurchasedProductsResponsePage getPurchasedProductsResponses(Page<PurchasedProducts> all) {
        PurchasedProductsResponsePage responsePage = new PurchasedProductsResponsePage();
        responsePage.setTotalPage(all.getTotalPages());
        responsePage.setTotalElements(all.getTotalElements());

        List<PurchasedProductsResponse> responses = new ArrayList<>();
        all.forEach(purchasedProducts -> {
            PurchasedProductsResponse response = getResponse(purchasedProducts);
            responses.add(response);
        });
        responsePage.setPurchasedProductsResponses(responses);
        return responsePage;
    }

    private PurchasedProductsResponse getResponse(PurchasedProducts purchasedProducts) {
        PurchasedProductsResponse response = modelMapper.map(purchasedProducts, PurchasedProductsResponse.class);
        response.setLocalDateTime(purchasedProducts.getLocalDateTime().toString());
        return response;
    }

    private PurchasedProducts getByPurchasedProductId(Integer integer) {
        return purchasedProductsRepository.findByIdAndDeleteFalse(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.PURCHASED_PRODUCTS_NOT_FOUND));
    }
}
