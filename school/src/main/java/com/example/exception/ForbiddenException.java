package com.example.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ControllerAdvice
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    private String type;
    private String massage;

    public ForbiddenException(String type, String massage) {
        this.type = type;
        this.massage = massage;
    }
}
