package com.CraftDemo.CarPoolApplication.dto.request;

public class EndRideRequest {

    private String userName;
    private String vehicleName;
    private String vehicleRegNo;
    private String source;
    private String destination;
    private Integer availableSeats;

    public EndRideRequest(String userName, String vehicleName, String vehicleRegNo, String source, String destination, Integer availableSeats) {
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
}
