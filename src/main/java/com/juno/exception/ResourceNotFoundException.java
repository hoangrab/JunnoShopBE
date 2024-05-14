package com.juno.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mess) {
        super(mess);
    }
}
