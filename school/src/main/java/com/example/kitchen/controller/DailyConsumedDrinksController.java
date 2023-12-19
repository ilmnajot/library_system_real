package com.example.kitchen.controller;

import com.example.kitchen.model.request.DailyConsumedDrinksRequest;
import com.example.kitchen.service.DailyConsumedDrinksService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dailyConsumedDrinks")
public class DailyConsumedDrinksController {

    private final DailyConsumedDrinksService dailyConsumedDrinksService;

    @PostMapping
    public ApiResponse create(@RequestBody DailyConsumedDrinksRequest dailyConsumedDrinksRequest) {
        return dailyConsumedDrinksService.create(dailyConsumedDrinksRequest);
    }

    @GetMapping("{dailyConsumedDrinksId}")
    public ApiResponse getById(@PathVariable Integer dailyConsumedDrinksId) {
        return dailyConsumedDrinksService.getById(dailyConsumedDrinksId);
    }

    @GetMapping("getAllByWarehouseId/{warehouseId}")
    public ApiResponse getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @PathVariable Integer warehouseId) {
        return dailyConsumedDrinksService.getAllByWarehouseId(warehouseId, page, size);
    }

    @GetMapping("getAllByBranchId/{branchId}")
    public ApiResponse getAllByBranchId(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        @PathVariable Integer branchId) {
        return dailyConsumedDrinksService.getAllByBranchId(branchId, page, size);
    }

    @PutMapping
    public ApiResponse update(@RequestBody DailyConsumedDrinksRequest dailyConsumedDrinksRequest) {
        return dailyConsumedDrinksService.update(dailyConsumedDrinksRequest);
    }

    @DeleteMapping("{dailyConsumedDrinksId}")
    public ApiResponse delete(@PathVariable Integer dailyConsumedDrinksId) {
        return dailyConsumedDrinksService.delete(dailyConsumedDrinksId);
    }
}
