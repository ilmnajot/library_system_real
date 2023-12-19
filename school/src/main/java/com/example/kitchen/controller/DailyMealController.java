package com.example.kitchen.controller;

import com.example.kitchen.model.request.DailyMealRequest;
import com.example.kitchen.service.DailyMealService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dailyMeal/")
public class DailyMealController {

    private final DailyMealService dailyMealService;


    @PostMapping
    public ApiResponse save(@RequestBody DailyMealRequest dailyMealRequest) {
        return dailyMealService.create(dailyMealRequest);
    }

    @GetMapping("{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return dailyMealService.getById(id);
    }

    @GetMapping("getAllByBranchId/{branchId}")
    public ApiResponse getAllByBranchId(@PathVariable Integer branchId,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "5") int size) {
        return dailyMealService.getByAllBranchId(branchId, page, size);
    }

    @GetMapping("getAll")
    public ApiResponse getByAll() {
        return dailyMealService.getByAll();
    }

    @PutMapping
    public ApiResponse update(@RequestBody DailyMealRequest dailyMealRequest) {
        return dailyMealService.update(dailyMealRequest);
    }

    @DeleteMapping("{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return dailyMealService.delete(id);
    }
}
