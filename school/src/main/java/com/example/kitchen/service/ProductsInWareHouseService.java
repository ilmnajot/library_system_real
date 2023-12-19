package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.enums.Constants;
import com.example.enums.MeasurementType;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.DailyConsumedProducts;
import com.example.kitchen.entity.ProductsInWareHouse;
import com.example.kitchen.entity.PurchasedProducts;
import com.example.kitchen.entity.Warehouse;
import com.example.kitchen.model.response.ProductsInWareHouseResponse;
import com.example.kitchen.model.request.DailyConsumedProductsRequest;
import com.example.kitchen.model.request.ProductsInWareHouseRequest;
import com.example.kitchen.model.request.PurchasedProductsRequest;
import com.example.kitchen.model.response.ProductsInWareHouseResponsePage;
import com.example.kitchen.repository.ProductsInWareHouseRepository;
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
public class ProductsInWareHouseService {

    private final ProductsInWareHouseRepository productsInWareHouseRepository;
    private final WareHouseRepository wareHouseRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;


    public ProductsInWareHouse create(ProductsInWareHouseRequest request) {
        ProductsInWareHouse productsInWareHouse = modelMapper.map(request, ProductsInWareHouse.class);
        setProductInWarehouse(request, productsInWareHouse);
        return productsInWareHouseRepository.save(productsInWareHouse);
    }

    public void storageOfPurchasedProducts(PurchasedProductsRequest request) {
        Optional<ProductsInWareHouse> productsInWareHouseOptional =
                productsInWareHouseRepository.findByNameAndMeasurementTypeAndBranchIdAndWarehouseId(
                        request.getName(),
                        request.getMeasurementType(),
                        request.getBranchId(),
                        request.getWarehouseId());

        if (productsInWareHouseOptional.isPresent()) {
            ProductsInWareHouse productsInWareHouse = productsInWareHouseOptional.get();
            productsInWareHouse.setActive(true);
            productsInWareHouse.setQuantity(productsInWareHouse.getQuantity() + request.getQuantity());
            productsInWareHouseRepository.save(productsInWareHouse);
        } else {
            ProductsInWareHouseRequest productsInWareHouseRequest = modelMapper.map(request, ProductsInWareHouseRequest.class);
            create(productsInWareHouseRequest);
        }
    }

    public void rollBackPurchasedProductsFromWarehouse(PurchasedProducts old) {
        ProductsInWareHouse productsInWareHouse = getProductsInWareHouse(
                old.getName(),
                old.getMeasurementType(),
                old.getBranch().getId(),
                old.getWarehouse().getId());

        reduceProductsQuantity(productsInWareHouse, old.getQuantity());
        productsInWareHouseRepository.save(productsInWareHouse);
    }

    public void consumedProducts(DailyConsumedProductsRequest request) {
        ProductsInWareHouse productsInWareHouse = getProductsInWareHouse(
                request.getName(),
                request.getMeasurementType(),
                request.getBranchId(),
                request.getWarehouseId());

        reduceProductsQuantity(productsInWareHouse, request.getQuantity());
        productsInWareHouseRepository.save(productsInWareHouse);
    }

    public void rollBackConsumedProducts(DailyConsumedProducts consumedProducts) {
        ProductsInWareHouse productsInWareHouse = getProductsInWareHouse(
                consumedProducts.getName(),
                consumedProducts.getMeasurementType(),
                consumedProducts.getBranch().getId(),
                consumedProducts.getWarehouse().getId());

        productsInWareHouse.setQuantity(productsInWareHouse.getQuantity() + consumedProducts.getQuantity());
        productsInWareHouse.setActive(true);
        productsInWareHouseRepository.save(productsInWareHouse);
    }

    public ApiResponse findByIdAndActiveTrue(Integer productInWarehouseId) {
        ProductsInWareHouse productsInWareHouse = productsInWareHouseRepository.findByIdAndActiveTrue(productInWarehouseId)
                .orElseThrow(() -> new RecordNotFoundException(Constants.PRODUCTS_IN_WAREHOUSE_NOT_FOUND));
        ProductsInWareHouseResponse wareHouseResponse =
                modelMapper.map(productsInWareHouse, ProductsInWareHouseResponse.class);
        return new ApiResponse(Constants.SUCCESSFULLY, true, wareHouseResponse);
    }

    public ApiResponse getAllByWarehouseIdAndActiveTrue(Integer wareHouseID, int page, int size) {
        Page<ProductsInWareHouse> all = productsInWareHouseRepository
                .findAllByWarehouseIdAndActiveTrue(wareHouseID, PageRequest.of(page, size));
        ProductsInWareHouseResponsePage wareHouseResponses = getResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, wareHouseResponses);
    }

    public ApiResponse getAllByBranchId(Integer branchId, int page, int size) {
        Page<ProductsInWareHouse> all = productsInWareHouseRepository
                .findAllByBranchIdAndActiveTrue(branchId, PageRequest.of(page, size));
        ProductsInWareHouseResponsePage wareHouseResponses = getResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, wareHouseResponses);
    }

    private void setProductInWarehouse(ProductsInWareHouseRequest request, ProductsInWareHouse productsInWareHouse) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(request.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        Warehouse warehouse = wareHouseRepository.findByIdAndActiveTrue(request.getWarehouseId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.WAREHOUSE_NOT_FOUND));

        productsInWareHouse.setActive(true);
        productsInWareHouse.setBranch(branch);
        productsInWareHouse.setWarehouse(warehouse);
    }

    private static void reduceProductsQuantity(ProductsInWareHouse productsInWareHouse, double quantity) {
        if (productsInWareHouse.getQuantity() >= quantity) {
            productsInWareHouse.setQuantity(productsInWareHouse.getQuantity() - quantity);
        } else {
            throw new RecordNotFoundException(Constants.PRODUCT_NOT_ENOUGH_QUANTITY);
        }
        if (productsInWareHouse.getQuantity() == 0) {
            productsInWareHouse.setActive(false);
        }
    }

    private ProductsInWareHouseResponsePage getResponses(Page<ProductsInWareHouse> all) {
        ProductsInWareHouseResponsePage page = new ProductsInWareHouseResponsePage();
        List<ProductsInWareHouseResponse> wareHouseResponses = new ArrayList<>();
        all.map(productsInWareHouse ->
                wareHouseResponses.add(modelMapper.map(productsInWareHouse, ProductsInWareHouseResponse.class)));
        page.setProductsInWareHouseResponses(wareHouseResponses);
        page.setTotalPage(all.getTotalPages());
        page.setTotalElement(all.getTotalElements());
        return page;
    }

    private ProductsInWareHouse getProductsInWareHouse(String name,
                                                       MeasurementType measurementType,
                                                       Integer branchId,
                                                       Integer warehouseId) {
        return productsInWareHouseRepository
                .findByNameAndMeasurementTypeAndBranchIdAndWarehouseId(
                        name,
                        measurementType,
                        branchId,
                        warehouseId)
                .orElseThrow(() -> new RecordNotFoundException(Constants.PRODUCTS_IN_WAREHOUSE_NOT_FOUND));
    }
}
