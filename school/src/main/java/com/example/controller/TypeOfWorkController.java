package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.TypeOfWorkRequest;
import com.example.service.TypeOfWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/typeOfWork/")
public class TypeOfWorkController {

    private final TypeOfWorkService typeOfWorkService;

    @PostMapping("save")
    public ApiResponse save(@RequestBody TypeOfWorkRequest typeOfWorkRequest) {
        return typeOfWorkService.create(typeOfWorkRequest);
    }

    @GetMapping("getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return typeOfWorkService.getById(id);
    }

    @GetMapping("getAllByBranchId/{id}")
    public ApiResponse getAllByBranchId(@PathVariable Integer id) {
        return typeOfWorkService.getAllByBranchId(id);
    }

    @PutMapping("update")
    public ApiResponse update(@RequestBody TypeOfWorkRequest typeOfWorkRequest) {
        return typeOfWorkService.update(typeOfWorkRequest);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return typeOfWorkService.delete(id);
    }
}