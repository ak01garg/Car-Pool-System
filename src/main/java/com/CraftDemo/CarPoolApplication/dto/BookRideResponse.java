package com.CraftDemo.CarPoolApplication.dto;

public class BookRideResponse {
    private String driverName;
    private String source;
    private String destination;
    private String vehicleName;
    private Integer availableSeats;


    public BookRideResponse(String driverName, String source, String destination, String vehicleName, Integer availableSeats) {
        this.driverName = driverName;
        this.source = source;
        this.destination = destination;
        this.vehicleName = vehicleName;
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "RideResponse{" +
                "driverName='" + driverName + '\'' +
                ", vacantSeats=" + availableSeats +
                ", vehicleUsed='" + vehicleName + '\'' +
                ", source=" + source +
                ", destination=" + destination +
                '}';
    }
}
