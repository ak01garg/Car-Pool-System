package com.CraftDemo.CarPoolApplication.exceptions;

public class RideAlreadyExistException extends RuntimeException{
    public RideAlreadyExistException(String message) {
        super(message);
    }
}
