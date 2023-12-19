package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireBaseTokenRegisterDto {

    private Integer userId;

    private String fireBaseToken;
}
