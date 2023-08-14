package com.example.bff.core.exceptions;

public class ItemAlreadyInCartException extends RuntimeException {

    public ItemAlreadyInCartException(String message) {
        super(message);
    }
}
