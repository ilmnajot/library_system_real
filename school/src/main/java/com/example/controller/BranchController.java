package com.example.controller;

import com.example.model.request.BranchDto;
import com.example.model.common.ApiResponse;
import com.example.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/branch")
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody BranchDto branchDto) {
        return branchService.create(branchDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return branchService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody @Validated BranchDto branchDto) {
        return branchService.update(branchDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return branchService.delete(id);
    }

    @GetMapping("/getByBusinessId/{id}")
    public ApiResponse getByBusinessId(@PathVariable Integer id) {
        return branchService.getByBusinessId(id);
    }


    @GetMapping("/getAll")
    public ApiResponse getAllBranches(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        return branchService.getAll(page, size);
    }
}
