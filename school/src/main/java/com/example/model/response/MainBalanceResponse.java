package com.example.model.response;

import com.example.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainBalanceResponse {

    private boolean active;

    private Integer id;

    private double balance;

    private double cashBalance;

    private double plasticBalance;

    private int accountNumber;

    private Branch branch;

    private String date;
}
