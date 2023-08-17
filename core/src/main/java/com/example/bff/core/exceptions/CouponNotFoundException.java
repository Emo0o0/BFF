package com.example.bff.core.exceptions;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(String message) {
        super(message);
    }
}
