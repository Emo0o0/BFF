package com.example.bff.core.exceptions;

public class NoItemsInCartException extends RuntimeException {
    public NoItemsInCartException(String message) {
        super(message);
    }
}
