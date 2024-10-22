package com.CraftDemo.CarPoolApplication.exceptions;

public class RideFullException extends RuntimeException{
    public RideFullException(String message) {
        super(message);
    }
}
