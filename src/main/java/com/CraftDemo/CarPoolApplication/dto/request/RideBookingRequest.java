package com.CraftDemo.CarPoolApplication.dto.request;

public class RideBookingRequest {
    private String userId;
    private String rideId;
    private String vehicleId;
    private Integer requestedSeats;


    public RideBookingRequest(String userId, String rideId, String vehicleId, Integer requestedSeats) {
        this.userId = userId;
        this.rideId = rideId;
        this.vehicleId = vehicleId;
        this.requestedSeats = requestedSeats;
    }

    public String getUserId() {
        return userId;
    }

    public String getRideId() {
        return rideId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public Integer getRequestedSeats() {
        return requestedSeats;
    }
}
