package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.MainBalanceRequest;
import com.example.service.MainBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mainBalance")
public class MainBalanceController {

    private final MainBalanceService mainBalanceService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody MainBalanceRequest balance) {
        return mainBalanceService.create(balance);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return mainBalanceService.getById(id);
    }

    @GetMapping("/getByBranchId/{branchId}")
    public ApiResponse getByBranchId(@PathVariable Integer branchId) {
        return mainBalanceService.getByBranchId(branchId);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody MainBalanceRequest balance) {
        return mainBalanceService.update(balance);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return mainBalanceService.delete(id);
    }
}
