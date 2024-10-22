package com.CraftDemo.CarPoolApplication.exceptions;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(String message) {
        super(message);
    }
}
