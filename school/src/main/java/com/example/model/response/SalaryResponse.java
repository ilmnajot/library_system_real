package com.example.model.response;

import com.example.entity.Branch;
import lombok.Data;

@Data
public class SalaryResponse {

    private Integer id;

    private String date;

    private UserResponse user;

    private Branch branch;

    private double fix;

    private double partlySalary;

    private double givenSalary;

    private double salary;

    private double cashAdvance;

    private double amountDebt;

    private double classLeaderSalary;

    private Integer mainBalanceId;
}
