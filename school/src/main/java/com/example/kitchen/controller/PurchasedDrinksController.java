package com.example.kitchen.controller;

import com.example.kitchen.model.request.PurchasedDrinksRequest;
import com.example.kitchen.service.PurchasedDrinksService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchasedDrinks")
public class PurchasedDrinksController {

    private final PurchasedDrinksService purchasedDrinksService;

    @PostMapping
    public ApiResponse create(@RequestBody PurchasedDrinksRequest purchasedDrinksRequest) {
        return purchasedDrinksService.create(purchasedDrinksRequest);
    }

    @GetMapping("{purchasedDrinksId}")
    public ApiResponse getById(@PathVariable Integer purchasedDrinksId) {
        return purchasedDrinksService.getById(purchasedDrinksId);
    }

    @GetMapping("getAllByWareHouseId{warehouseId}")
    public ApiResponse getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @PathVariable Integer warehouseId) {
        return purchasedDrinksService.getAllByWarehouseId(warehouseId,page, size);
    }

    @GetMapping("getAllByBranchId/{branchId}")
    public ApiResponse getAllByBranchId(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        @PathVariable Integer branchId) {
        return purchasedDrinksService.getAllByBranchId(branchId, page, size);
    }

    @PutMapping
    public ApiResponse update(@RequestBody PurchasedDrinksRequest purchasedDrinksRequest) {
        return purchasedDrinksService.update(purchasedDrinksRequest);
    }

    @DeleteMapping("{purchasedDrinksId}")
    public ApiResponse delete(@PathVariable Integer purchasedDrinksId) {
        return purchasedDrinksService.delete(purchasedDrinksId);
    }
}
