package com.github.zlbovolini.mercadolivre.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    private ResourceNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
