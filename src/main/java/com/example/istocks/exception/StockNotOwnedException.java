package com.example.istocks.exception;

public class StockNotOwnedException extends Exception {
    public StockNotOwnedException(String message) {
        super(message);
    }
}
