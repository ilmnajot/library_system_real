package com.example.model.request;

import com.example.entity.Salary;
import com.example.enums.ExpenseType;
import com.example.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String phoneNumber;
    private double money;
    private PaymentType paymentType;
    private ExpenseType ExpenseType;
    private Salary salary;
    private String message;
}
