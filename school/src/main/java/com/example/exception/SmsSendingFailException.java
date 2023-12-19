package com.example.exception;

public class SmsSendingFailException extends RuntimeException {
    public SmsSendingFailException(String massage) {
        super(massage);
    }
}
