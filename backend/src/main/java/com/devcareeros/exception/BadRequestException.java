package com.devcareeros.exception;

/** Thrown for things like "email already registered" or "wrong password". */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
