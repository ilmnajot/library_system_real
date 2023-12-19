package com.example.model.response;

import com.example.entity.Branch;
import com.example.enums.ExpenseType;
import com.example.enums.PaymentType;
import lombok.Data;


@Data
public class TransactionHistoryResponse {

    private Integer id;

    private double moneyAmount;

    private String accountNumber;

    private String comment;

    private String date;

    private boolean active;

    private boolean paidInFull;

    private ExpenseType expenseType;

    private PaymentType paymentType;

    private UserResponse taker;

    private StudentResponse student;

    private MainBalanceResponse mainBalanceResponse;

    private Branch branch;
}
