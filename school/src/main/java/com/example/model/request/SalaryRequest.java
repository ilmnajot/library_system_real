package com.example.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryRequest {

    private Integer id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    private Integer userId;

    private String phoneNumber;

    private Integer branchId;

    private Integer mainBalanceId;

    private double fix;

    private double currentMonthSalary;

    private double partlySalary;

    private double givenSalary;

    private double salary;

    private double cashAdvance;

    private double debtAmount;

    private double classLeaderSalary;
}
