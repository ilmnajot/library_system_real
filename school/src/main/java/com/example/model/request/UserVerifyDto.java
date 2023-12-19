package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerifyDto {

    private String phoneNumber;

    private int verificationCode;
}
