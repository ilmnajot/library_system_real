package com.example.kitchen.controller;

import com.example.kitchen.model.request.DailyConsumedProductsRequest;
import com.example.kitchen.service.DailyConsumedProductsService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dailyConsumedProducts")
public class DailyConsumedProductsController {

    private final DailyConsumedProductsService dailyConsumedProductsService;

    @PostMapping
    public ApiResponse create(@RequestBody DailyConsumedProductsRequest dailyConsumedProductsRequest) {
        return dailyConsumedProductsService.create(dailyConsumedProductsRequest);
    }

    @GetMapping("{dailyConsumedProductsId}")
    public ApiResponse getById(@PathVariable Integer dailyConsumedProductsId) {
        return dailyConsumedProductsService.getById(dailyConsumedProductsId);
    }

    @GetMapping("getAllByWarehouseId/{warehouseId}")
    public ApiResponse getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @PathVariable Integer warehouseId) {
        return dailyConsumedProductsService.getAllByWarehouseId(warehouseId, page, size);
    }

    @GetMapping("getAllByBranchId/{branchId}")
    public ApiResponse getAllByBranchId(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        @PathVariable Integer branchId) {
        return dailyConsumedProductsService.getAllByBranchId(branchId, page, size);
    }

    @PutMapping
    public ApiResponse update(@RequestBody DailyConsumedProductsRequest dailyConsumedProductsRequest) {
        return dailyConsumedProductsService.update(dailyConsumedProductsRequest);
    }

    @DeleteMapping("{dailyConsumedProductsId}")
    public ApiResponse delete(@PathVariable Integer dailyConsumedProductsId) {
        return dailyConsumedProductsService.delete(dailyConsumedProductsId);
    }
}
