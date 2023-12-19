package com.example.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAccountDto {

    private Integer id;

    private Double balance;

    private Integer studentId;

    private Integer branchId;
}
