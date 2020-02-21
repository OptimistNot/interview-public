package com.devexperts.error.exception;

public class InsufficientAccountBalance extends RuntimeException {

    public InsufficientAccountBalance(String message) {
        super(message);
    }
}
