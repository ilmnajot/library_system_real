package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.JournalRequest;
import com.example.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/journal")
public class JournalController {

    private final JournalService journalService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody JournalRequest journalRequest) {
        return journalService.create(journalRequest);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return journalService.getById(id);
    }

    @GetMapping("/getAllByIdBranchIdAndActiveTrue/{branchId}")
    public ApiResponse getAllByIdBranchIdAndActiveTrue(@PathVariable Integer branchId,
                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "5") int size) {
        return journalService.getAllByIdBranchIdAndActiveTrue(branchId, page, size);
    }

    @GetMapping("/getAllByBranchId/{branchId}")
    public ApiResponse getAllByBranchId(@PathVariable Integer branchId,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "5") int size) {
        return journalService.getAllByBranchId(branchId, page, size);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody JournalRequest journalRequest) {
        return journalService.update(journalRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return journalService.delete(id);
    }

}
