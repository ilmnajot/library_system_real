package com.example.model.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentAccountCreate {
    private String accountNumber;
    private String newAccountNumber;
    private String description;
    private Integer discount;
    private Integer branchId;
    private Integer studentId;
    private Integer mainBalanceId;
}
