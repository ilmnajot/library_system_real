package com.example.model.response;

import com.example.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAccountResponse {

    private Integer id;

    private String accountNumber;

    private double amountOfDebit;

    private double balance;

    private String discount;

    private String description;

    private String date;

    private boolean active;

    private boolean paidInFull;

    private MainBalanceResponse mainBalance;

    private StudentResponse student;

    private Branch branch;
}
