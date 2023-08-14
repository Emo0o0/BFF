package com.example.bff.core.exceptions;

public class NotEnoughQuantityException extends RuntimeException {

    public NotEnoughQuantityException(String message) {
        super(message);
    }
}
