package com.example.controller;

import com.example.enums.PaymentType;
import com.example.model.common.ApiResponse;
import com.example.model.request.SalaryRequest;
import com.example.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/salary/")
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping("save")
    public ApiResponse save(@RequestBody SalaryRequest salaryRequest) {
        return salaryService.create(salaryRequest);
    }


    @PostMapping("giveCashAdvance")
    public ApiResponse giveCashAdvance(@RequestParam Integer id,
                                       @RequestParam double cashSalary,
                                       @RequestParam PaymentType paymentType) {
        return salaryService.giveCashAdvance(id, cashSalary, paymentType);
    }

    @PostMapping("giveDebtToEmployee")
    public ApiResponse giveDebtToEmployee(@RequestParam Integer id,
                                          @RequestParam double debitAmount,
                                          @RequestParam PaymentType paymentType) {
        return salaryService.giveDebt(id, debitAmount, paymentType);
    }

    @PostMapping("debtRepayment")
    public ApiResponse debtRepayment(@RequestParam Integer id) {
        return salaryService.debtRepayment(id);
    }

    @PostMapping("givePartlySalary")
    public ApiResponse givePartlySalary(@RequestParam Integer id,
                                        @RequestParam double partlySalary,
                                        @RequestParam PaymentType paymentType) {
        return salaryService.givePartlySalary(id, partlySalary, paymentType);
    }

    @PostMapping("giveSalary")
    public ApiResponse giveSalary(@RequestParam Integer id,
                                  @RequestParam boolean withholdingOfDebtIfAny,
                                  @RequestParam PaymentType paymentType) {
        return salaryService.giveSalary(id, withholdingOfDebtIfAny, paymentType);
    }

    @GetMapping("getAllByBranchId/{branchId}")
    public ApiResponse getAllByBranchId(@PathVariable Integer branchId) {
        return salaryService.getAllByBranchId(branchId);
    }

    @GetMapping("getByUserId/{id}")
    public ApiResponse getByUserId(@PathVariable Integer id) {
        return salaryService.getById(id);
    }

    @PutMapping("update")
    public ApiResponse update(@RequestBody SalaryRequest salaryRequest) {
        return salaryService.update(salaryRequest);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return salaryService.delete(id);
    }

}
