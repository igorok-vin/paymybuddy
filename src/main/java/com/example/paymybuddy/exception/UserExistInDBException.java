package com.example.paymybuddy.exception;

public class UserExistInDBException extends Exception{
    public UserExistInDBException(String message) {
        super(message);
    }
}
