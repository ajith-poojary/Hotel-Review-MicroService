package com.ajith.userService.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException()
    {
        super("Resource not found om Server!!");
    }

    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
