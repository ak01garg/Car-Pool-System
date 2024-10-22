package com.CraftDemo.CarPoolApplication.exceptions;

public class InvalidCommandException extends RuntimeException{
    public InvalidCommandException(String message) {
        super(message);
    }
}
