package com.devcareeros.exception;

/** Thrown whenever we try to fetch/update/delete something that doesn't exist. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
