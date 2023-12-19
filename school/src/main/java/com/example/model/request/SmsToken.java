package com.example.model.request;

import com.example.entity.FireBaseToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsToken {

    private String message;

    @JsonProperty("data")
    private FireBaseToken data;

    private String token_type;
}
