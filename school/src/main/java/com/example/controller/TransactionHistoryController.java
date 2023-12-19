package com.example.controller;

import com.example.annotations.CheckPermission;
import com.example.model.common.ApiResponse;
import com.example.model.request.TransactionHistoryRequest;
import com.example.service.TransactionHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactionHistory")
@RequiredArgsConstructor
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @PostMapping("/save")
    public ApiResponse save(@RequestBody @Valid TransactionHistoryRequest transactionHistoryRequest) {
        return transactionHistoryService.create(transactionHistoryRequest);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return transactionHistoryService.getById(id);
    }


    @GetMapping("/findAllByBranch_IdAndActiveTrue/{branchId}")
    public ApiResponse findAllByBranch_IdAndActiveTrue(@PathVariable Integer branchId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return transactionHistoryService.findAllByBranch_IdAndActiveTrue(branchId, page,size);
    }


    @GetMapping("/getByBranchIdAndByStudent/{branchId}")
    public ApiResponse getByBranchIdAndByStudent(@PathVariable Integer branchId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return transactionHistoryService.getByBranchIdAndByStudent(branchId, page,size);
    }

    @CheckPermission("GET")
    @GetMapping("/findAllByActiveTrue")
    public ApiResponse findAllByActiveTrue() {
        return transactionHistoryService.findAllByActiveTrue();
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody @Valid TransactionHistoryRequest transactionHistoryRequest) {
        return transactionHistoryService.update(transactionHistoryRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse update(@PathVariable Integer id) {
        return transactionHistoryService.delete(id);
    }

    @GetMapping("/getByStudentId/{id}")
    public HttpEntity<?> getByStudentId(@PathVariable Integer id,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "5") int size) {
        ApiResponse apiResponse = transactionHistoryService.getByStudentId(id, page, size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
