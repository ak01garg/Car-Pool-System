package com.CraftDemo.CarPoolApplication.exceptions;

public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException(String message) {
        super(message);
    }
}
