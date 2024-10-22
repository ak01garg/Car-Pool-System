package com.CraftDemo.CarPoolApplication.exceptions;

public class VehicleAlreadyRegisteredException extends RuntimeException{
    public VehicleAlreadyRegisteredException(String message) {
        super(message);
    }
}
