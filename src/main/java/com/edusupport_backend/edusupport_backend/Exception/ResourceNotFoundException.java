package com.edusupport_backend.edusupport_backend.Exception;


public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(
            String message) {

        super(message);
    }
}
