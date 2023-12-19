package com.example.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private boolean success;
    private Object data;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(Object data, boolean success) {
        this.data = data;
        this.success = success;
    }
}
