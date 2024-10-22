package com.CraftDemo.CarPoolApplication.exceptions;

public class BookingAlreadyExistException extends RuntimeException{

    public BookingAlreadyExistException(String message) {
        super(message);
    }
}
