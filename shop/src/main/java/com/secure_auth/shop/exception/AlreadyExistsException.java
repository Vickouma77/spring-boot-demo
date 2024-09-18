package com.secure_auth.shop.exception;

/**
 * Exception thrown when an entity already exists.
 */
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
