package com.example.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String userNotFound) {
    }
}
