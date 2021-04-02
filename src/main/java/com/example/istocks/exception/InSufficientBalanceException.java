package com.example.istocks.exception;

public class InSufficientBalanceException extends Exception {
    public InSufficientBalanceException(String message) {
        super(message);
    }
}
