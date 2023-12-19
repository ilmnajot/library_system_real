package com.example.service;

import com.example.entity.*;
import com.example.enums.Constants;
import com.example.enums.ExpenseType;
import com.example.enums.PaymentType;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.SalaryRequest;
import com.example.model.request.Transaction;
import com.example.model.request.TransactionHistoryRequest;
import com.example.model.response.SalaryResponse;
import com.example.model.response.UserResponse;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaryService  {

    private final ModelMapper modelMapper;
    private final SalaryRepository salaryRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final MainBalanceRepository mainBalanceRepository;
    private final StudentClassRepository studentClassRepository;
    private final TransactionHistoryService transactionHistoryService;

    public ApiResponse create(SalaryRequest salaryRequest) {
        Optional<Salary> optionalSalary = salaryRepository.findByUserIdAndActiveTrue(salaryRequest.getUserId());
        if (optionalSalary.isPresent()) {
            return new ApiResponse("this user salary already exist", false);
        }
        Salary salary = modelMapper.map(salaryRequest, Salary.class);

        if (studentClassRepository.findByClassLeaderPhoneNumberAndActiveTrue(salaryRequest.getPhoneNumber()).isPresent()) {
            salary.setClassLeaderSalary(salaryRequest.getClassLeaderSalary());
            salary.setSalary(salary.getSalary() + salary.getClassLeaderSalary());
        }
        setSalary(salaryRequest, salary);
        salaryRepository.save(salary);

        return new ApiResponse(Constants.SUCCESSFULLY, true);
    }


    public ApiResponse getById(Integer id) {
        Salary salary = getByUserPhoneNumberAndActiveTrue(id);
        SalaryResponse response = getResponse(salary);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    public ApiResponse update(SalaryRequest salaryRequest) {
        Salary salary = modelMapper.map(salaryRequest, Salary.class);
        checkingUpdate(salaryRequest);
        salary.setId(salaryRequest.getId());
        setSalary(salaryRequest, salary);
        salaryRepository.save(salary);
        return new ApiResponse(Constants.SUCCESSFULLY, true);
    }


    public ApiResponse delete(Integer id) {
        Salary salary = getByUserPhoneNumberAndActiveTrue(id);
        salary.setActive(false);
        salaryRepository.save(salary);
        SalaryResponse response = getResponse(salary);
        return new ApiResponse(Constants.DELETED, true, response);
    }

    @Transactional(rollbackFor = {RecordNotFoundException.class, Exception.class})
    public ApiResponse giveCashAdvance(Integer id, double cashSalary, PaymentType paymentType) {
        Salary salary = getByUserPhoneNumberAndActiveTrue(id);
        toDoGiveCash(cashSalary, salary, paymentType);
        salaryRepository.save(salary);
        return new ApiResponse(Constants.SUCCESSFULLY, true, getResponse(salary));
    }


    private void toDoGiveCash(double cashSalary, Salary salary, PaymentType paymentType) {
        salary.setCashAdvance(salary.getCashAdvance() + cashSalary);
        salary.setGivenSalary(salary.getGivenSalary() + cashSalary);
        if (salary.getSalary() >= cashSalary) {
            salary.setSalary(salary.getSalary() - cashSalary);
        } else {
            throw new RecordNotFoundException(Constants.SALARY_NOT_ENOUGH);
        }
        transaction(salary.getUser().getPhoneNumber(), cashSalary, paymentType, salary, "Hodimga naqd pul berildi");
    }

    @Transactional(rollbackFor = {RecordNotFoundException.class, RecordNotFoundException.class})
    public ApiResponse givePartlySalary(Integer id, double partlySalary, PaymentType paymentType) {
        Salary salary = getByUserPhoneNumberAndActiveTrue(id);
        toDoGivePartlySalary(partlySalary, salary, paymentType);
        salaryRepository.save(salary);
        createNewSalary(salary);
        return new ApiResponse(Constants.SUCCESSFULLY, true, getResponse(salary));
    }


    private void toDoGivePartlySalary(double partlySalary, Salary salary, PaymentType paymentType) {
        salary.setPartlySalary(salary.getPartlySalary() + partlySalary);
        salary.setGivenSalary(salary.getGivenSalary() + partlySalary);
        if (salary.getSalary() >= partlySalary) {
            salary.setSalary(salary.getSalary() - partlySalary);
        } else {
            throw new RecordNotFoundException(Constants.SALARY_NOT_ENOUGH);
        }
        salary.setActive(false);
        transaction(salary.getUser().getPhoneNumber(), partlySalary, paymentType, salary, "Hodimga qisman oylik berildi");
    }


    @Transactional(rollbackFor = {Exception.class, RecordNotFoundException.class})
    public ApiResponse giveSalary(Integer id, boolean debtCollection, PaymentType paymentType) {
        Salary salary = getByUserPhoneNumberAndActiveTrue(id);

        if (salary.getDate().getMonth() != LocalDate.now().getMonth()) {
            throw new RecordAlreadyExistException(Constants.SALARY_ALREADY_GIVEN_FOR_THIS_MONTH);
        }
        double salaryWithoutDebt = salary.getSalary() - salary.getAmountDebt();
        String phoneNumber = salary.getUser().getPhoneNumber();
        if (salaryWithoutDebt > 0) {
            transaction(phoneNumber, salaryWithoutDebt, paymentType, salary, "Hodimga oylik berildi");
        }
        repaymentOfDebtIfAny(debtCollection, salary);

        salary.setGivenSalary(salary.getGivenSalary() + salary.getSalary());
        salary.setSalary(0);
        salary.setActive(false);

        createNewSalary(salary);
        salaryRepository.save(salary);
        String message = getMessage(debtCollection, salary, salaryWithoutDebt > 0 ? salaryWithoutDebt : 0);
        return new ApiResponse(message, true, getResponse(salary));
    }


    @Transactional(rollbackFor = {Exception.class, RecordNotFoundException.class})
    public ApiResponse giveDebt(Integer id, double debtAmount, PaymentType paymentType) {
        Salary salary = getByUserPhoneNumberAndActiveTrue(id);
        String phoneNumber = salary.getUser().getPhoneNumber();
        transaction(phoneNumber, debtAmount, paymentType, salary, "Hodim pul oldi");
        toDoGiveDebt(debtAmount, salary);
        salaryRepository.save(salary);
        return new ApiResponse(Constants.SUCCESSFULLY, true, getResponse(salary));
    }

    private void toDoGiveDebt(double debtAmount, Salary salary) {
        double money = salary.getSalary() - debtAmount;
        if (money >= 0) {
            salary.setSalary(money);
        } else {
            salary.setSalary(0);
            salary.setAmountDebt(salary.getAmountDebt() + Math.abs(money));
        }
        salary.setGivenSalary(salary.getGivenSalary() + debtAmount);
    }

    @Transactional(rollbackFor = {Exception.class, RecordNotFoundException.class})
    public ApiResponse debtRepayment(Integer id) {
        Salary salary = getByUserPhoneNumberAndActiveTrue(id);
        boolean isDebtAvailable = salary.getAmountDebt() > 0;
        repaymentOfDebtIfAny(true, salary);
        salaryRepository.save(salary);
        String message = !isDebtAvailable ? Constants.NO_DEBT_EXISTS
                : salary.getSalary() - salary.getAmountDebt() >= 0 ? Constants.DEBT_WAS_COLLECTED
                : Constants.SALARY_NOT_ENOUGH_REMAINING_DEBT + salary.getAmountDebt();
        return new ApiResponse(message, true, getResponse(salary));
    }

    private void transaction(String phoneNumber, double cashSalary, PaymentType paymentType, Salary salary, String message) {
        transactionHistoryService.create(modelMapper.map(
                new Transaction(phoneNumber,
                        cashSalary,
                        paymentType,
                        ExpenseType.ADDITIONAL_EXPENSE,
                        salary,
                        message), TransactionHistoryRequest.class));
    }

    private String getMessage(boolean debtCollection, Salary salary, double givingSalary) {
        String message = debtCollection ? salary.getAmountDebt() + " qarz ushlab qolindi.  " : "";
        message += "bu oyligi :  " + givingSalary;
        return message;
    }

    private void createNewSalary(Salary salary) {
        Salary newSalary = new Salary();
        newSalary.setClassLeaderSalary(salary.getClassLeaderSalary());
        newSalary.setActive(true);
        newSalary.setDate(salary.getDate().plusMonths(1));
        newSalary.setFix(salary.getFix());
        newSalary.setBranch(salary.getBranch());
        newSalary.setUser(salary.getUser());
        newSalary.setSalary(salary.getSalary());
        newSalary.setAmountDebt(salary.getAmountDebt() > 0 ? salary.getAmountDebt() : 0);
        salaryRepository.save(newSalary);
    }

    private void repaymentOfDebtIfAny(boolean debtCollection, Salary salary) {
        if (debtCollection) {
            double salaryWithoutDebit = salary.getSalary() - salary.getAmountDebt();
            if (salaryWithoutDebit >= 0) {
                salary.setSalary(salaryWithoutDebit);
                salary.setAmountDebt(0);
            } else {
                salary.setSalary(0);
                salary.setAmountDebt(Math.abs(salaryWithoutDebit));
            }
        }
    }

    private void setSalary(SalaryRequest salaryRequest, Salary salary) {
        User user = userRepository.findByIdAndBlockedFalse(salaryRequest.getUserId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND));
        MainBalance mainBalance = mainBalanceRepository.findByIdAndActiveTrue(salaryRequest.getMainBalanceId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.MAIN_BALANCE_NOT_FOUND));
        Branch branch = branchRepository.findByIdAndDeleteFalse(salaryRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));

        salary.setActive(true);
        salary.setUser(user);
        salary.setMainBalance(mainBalance);
        salary.setBranch(branch);
    }

    public ApiResponse getAllByBranchId(Integer branchId) {
        List<SalaryResponse> salaryResponses = new ArrayList<>();
        List<Salary> all = salaryRepository.findAllByBranch_IdAndActiveTrue(branchId);
        all.forEach(salary -> {
            SalaryResponse response = getResponse(salary);
            salaryResponses.add(response);
        });
        return new ApiResponse(Constants.SUCCESSFULLY, true, salaryResponses);
    }

    public SalaryResponse getResponse(Salary salary) {
        SalaryResponse salaryResponse = modelMapper.map(salary, SalaryResponse.class);
        salaryResponse.setDate(salary.getDate().toString());
        salaryResponse.setUser(modelMapper.map(salary.getUser(), UserResponse.class));
        return salaryResponse;
    }

    private Salary getByUserPhoneNumberAndActiveTrue(Integer id) {
        return salaryRepository.findByUserIdAndActiveTrue(id)
                .orElseThrow(() -> new RecordNotFoundException(Constants.SALARY_NOT_FOUND));
    }

    private void checkingUpdate(SalaryRequest salaryRequest) {
        Salary old = salaryRepository.findById(salaryRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.SALARY_NOT_FOUND));

        if (old.getDate().getDayOfMonth() != LocalDate.now().getDayOfMonth()) {
            throw new RecordNotFoundException(Constants.DO_NOT_CHANGE_BECAUSE_TIME_EXPIRED);
        }

    }
}
