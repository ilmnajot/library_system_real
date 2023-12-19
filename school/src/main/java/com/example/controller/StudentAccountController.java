package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.StudentAccountCreate;
import com.example.model.request.StudentAccountRequest;
import com.example.service.StudentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studentAccount")
public class StudentAccountController {

    private final StudentAccountService studentAccountService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody StudentAccountCreate studentAccountCreate) {
        return studentAccountService.create(studentAccountCreate);
    }

    @GetMapping("/getById/{accountNumber}")
    public ApiResponse getById(@PathVariable Integer accountNumber) {
        return studentAccountService.getById(accountNumber);
    }

    @PostMapping("/payment")
    public ApiResponse payment(@RequestBody StudentAccountRequest studentAccountRequest) {
        return studentAccountService.payment(studentAccountRequest);
    }

    @PutMapping("/updatePayment")
    public ApiResponse updatePayment(@RequestBody StudentAccountRequest studentAccountRequest) {
        return studentAccountService.updatePayment(studentAccountRequest);
    }

    @GetMapping("/getByBranchId/{branchId}")
    public HttpEntity<?> getByBranchId(@PathVariable Integer branchId,
                                       @RequestParam(required = false) Integer classId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {

        ApiResponse apiResponse = studentAccountService.getByBranchId(branchId, classId, page, size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getAllByDebtActive/{branchId}")
    public HttpEntity<?> getAllByDebtActive(@PathVariable Integer branchId,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        ApiResponse apiResponse = studentAccountService.getAllByDebtActive(branchId,page,size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@RequestBody StudentAccountCreate studentAccountRequest,
                                @PathVariable Integer id) {
        ApiResponse apiResponse = studentAccountService.update(studentAccountRequest, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = studentAccountService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/searchStudentAccount")
    public HttpEntity<?> searchStudentAccount(@RequestParam String name,
                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "5") int size) {
        ApiResponse apiResponse = studentAccountService.searchStudentAccount(name, page, size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
