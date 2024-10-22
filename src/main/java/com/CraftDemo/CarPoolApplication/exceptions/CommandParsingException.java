package com.CraftDemo.CarPoolApplication.exceptions;

public class CommandParsingException extends RuntimeException{
    public CommandParsingException(String message) {
        super(message);
    }
}
