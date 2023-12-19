package com.example.kitchen.controller;

import com.example.kitchen.model.request.WareHouseRequest;
import com.example.kitchen.service.WareHouseService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wareHouses")
@RequiredArgsConstructor
public class WareHouseController {

    private final WareHouseService wareHouseService;

    @PostMapping
    public ApiResponse create(@RequestBody WareHouseRequest wareHouseRequest) {
        return wareHouseService.create(wareHouseRequest);
    }

    @GetMapping("{wareHouseId}")
    public ApiResponse getById(@PathVariable Integer wareHouseId) {
        return wareHouseService.getById(wareHouseId);
    }

    @GetMapping("getAllWareHouses")
    public ApiResponse getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size) {
        return wareHouseService.getAll(page, size);
    }

    @GetMapping("getAllByBranchId/{branchId}")
    public ApiResponse getAllByBranchId(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        @PathVariable Integer branchId) {
        return wareHouseService.getAllByBranchId(branchId, page, size);
    }

    @PutMapping
    public ApiResponse update(@RequestBody WareHouseRequest wareHouseRequest) {
        return wareHouseService.update(wareHouseRequest);
    }

    @DeleteMapping("{wareHouseId}")
    public ApiResponse delete(@PathVariable Integer wareHouseId) {
        return wareHouseService.delete(wareHouseId);
    }
}
