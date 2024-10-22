package com.CraftDemo.CarPoolApplication.dto.request;


import com.CraftDemo.CarPoolApplication.enums.RideSelectionType;

public class RideSelectionRequest {

    private String requestByUserName;
    private String source;
    private String destination;
    private RideSelectionType rideSelectionType;
    private String preferredVehicleName;
    private Integer seats;

    public RideSelectionRequest(String requestByUserName , String source, String destination, RideSelectionType rideSelectionType, String preferredVehicleName, Integer seats) {
        this.requestByUserName = requestByUserName;
        this.source = source;
        this.destination = destination;
        this.rideSelectionType = rideSelectionType;
        this.preferredVehicleName = preferredVehicleName;
        this.seats = seats;
    }


    public RideSelectionType getRideSelectionType() {
        return rideSelectionType;
    }

    public String getPreferredVehicleName() {
        return preferredVehicleName;
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

    public String getRequestByUserName() {
        return requestByUserName;
    }

    public void setRequestByUserName(String requestByUserName) {
        this.requestByUserName = requestByUserName;
    }

    public Integer getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return "RideSelectionRequest{" +
                "requestByUserName='" + requestByUserName + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", rideSelectionType=" + rideSelectionType +
                ", preferredVehicleName='" + preferredVehicleName + '\'' +
                ", seats=" + seats +
                '}';
    }
}
