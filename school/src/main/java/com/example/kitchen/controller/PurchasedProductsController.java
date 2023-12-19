package com.example.kitchen.controller;

import com.example.kitchen.model.request.PurchasedProductsRequest;
import com.example.kitchen.service.PurchasedProductsService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchasedProducts")
public class PurchasedProductsController {

    private final PurchasedProductsService purchasedProductsService;

    @PostMapping
    public ApiResponse create(@RequestBody PurchasedProductsRequest purchasedProductsRequest) {
        return purchasedProductsService.create(purchasedProductsRequest);
    }

    @GetMapping("{purchasedProductsId}")
    public ApiResponse getById(@PathVariable Integer purchasedProductsId) {
        return purchasedProductsService.getById(purchasedProductsId);
    }

    @GetMapping("getAllByWarehouseId/{warehouseId}")
    public ApiResponse getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @PathVariable Integer warehouseId) {
        return purchasedProductsService.getAllByWarehouseId(warehouseId, page, size);
    }

    @GetMapping("getAllByBranchId/{branchId}")
    public ApiResponse getAllByBranchId(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        @PathVariable Integer branchId) {
        return purchasedProductsService.getAllByBranchId(branchId, page, size);
    }

    @PutMapping
    public ApiResponse update(@RequestBody PurchasedProductsRequest purchasedProductsRequest) {
        return purchasedProductsService.update(purchasedProductsRequest);
    }

    @DeleteMapping("{purchasedProductsId}")
    public ApiResponse delete(@PathVariable Integer purchasedProductsId) {
        return purchasedProductsService.delete(purchasedProductsId);
    }
}
