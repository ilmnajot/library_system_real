package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainBalanceRequest {

    private Integer id;

    private Integer branchId;

    private double balance;

    private double cashBalance;

    private double plasticBalance;

    //    @Min(value = 1, message = "must be accountNumber")
    private int accountNumber;
}