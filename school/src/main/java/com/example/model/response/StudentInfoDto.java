package com.example.model.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfoDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String fatherName;

    private String phoneNumber;

    private String docNumber;

    private double paymentAmount;

    private String accountNumber;

    private Integer photoId;

    private double amountOfDebit;

    private double balance;

    private double discount;

    private boolean paidInFull;
}
