package com.example.exception;

public class TimeExceededException extends RuntimeException{
    public TimeExceededException(String massage) {
        super(massage);
    }
}
