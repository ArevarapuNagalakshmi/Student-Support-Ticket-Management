package com.edusupport_backend.edusupport_backend.Exception;

public class BadRequestException
        extends RuntimeException {

    public BadRequestException(
            String message) {

        super(message);
    }
}