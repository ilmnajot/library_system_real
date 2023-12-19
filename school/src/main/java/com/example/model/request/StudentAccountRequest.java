package com.example.model.request;

import com.example.enums.ExpenseType;
import com.example.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAccountRequest {

    private String accountNumber;

    private String money;

    private boolean paidInFull;

    private PaymentType paymentType;

    private ExpenseType expenseType;

    private String comment;

    private Integer branchId;

    private Integer mainBalanceId;
}
