package com.edusupport_backend.edusupport_backend.Exception;


public class UnauthorizedException
        extends RuntimeException {

    public UnauthorizedException(
            String message) {

        super(message);
    }
}
