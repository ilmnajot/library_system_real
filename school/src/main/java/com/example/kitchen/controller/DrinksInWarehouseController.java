package com.example.kitchen.controller;

import com.example.kitchen.service.DrinksInWareHouseService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/drinksInWarehouse")
public class DrinksInWarehouseController {

    private final DrinksInWareHouseService drinksInWareHouseService;


    @GetMapping("{drinksInWareHouseId}")
    public ApiResponse getById(@PathVariable Integer drinksInWareHouseId) {
        return drinksInWareHouseService.getById(drinksInWareHouseId);
    }

    @GetMapping("getAllByWarehouseId")
    public ApiResponse getAllByWarehouseId(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestParam(value = "wareHouseId") Integer wareHouseId) {
        return drinksInWareHouseService.getAllByWarehouseId(wareHouseId, page, size);
    }

    @GetMapping("getAllByBranchIdId")
    public ApiResponse getAllByBranchIdId(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestParam(value = "branchId") Integer branchId) {
        return drinksInWareHouseService.getAllByBranchId(branchId, page, size);
    }
}
