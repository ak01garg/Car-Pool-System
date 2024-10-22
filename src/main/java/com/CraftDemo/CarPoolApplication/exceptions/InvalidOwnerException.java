package com.CraftDemo.CarPoolApplication.exceptions;

public class InvalidOwnerException extends RuntimeException{
    public InvalidOwnerException(String message) {
        super(message);
    }
}
