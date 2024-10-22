package com.CraftDemo.CarPoolApplication.dto.request;

public class RideOfferRequest {

    private String userName;
    private String vehicleName;
    private String vehicleRegNo;
    private String source;
    private String destination;
    private Integer availableSeats;

    public RideOfferRequest(String userName, String vehicleName, String vehicleRegNo, String source, String destination, Integer availableSeats) {
        this.userName = userName;
        this.vehicleName = vehicleName;
        this.vehicleRegNo = vehicleRegNo;
        this.source = source;
        this.destination = destination;
        this.availableSeats = availableSeats;
    }

    public String getUserName() {
        return userName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public String toString() {
        return "RideOfferRequest{" +
                "userName='" + userName + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", vehicleRegNo='" + vehicleRegNo + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", availableSeats=" + availableSeats +
                '}';
    }
}
