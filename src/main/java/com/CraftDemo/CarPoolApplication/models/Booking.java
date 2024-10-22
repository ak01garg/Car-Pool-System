package com.CraftDemo.CarPoolApplication.models;

import java.util.Date;
import java.util.Objects;

public class Booking extends BaseModel {

    private String userId;
    private String rideId;
    private String vehicleId;
    private Date bookingTime;

    public Booking(String userId, String rideId, String vehicleId, Date bookingTime) {
        super();
        this.userId = userId;
        this.rideId = rideId;
        this.vehicleId = vehicleId;
        this.bookingTime = bookingTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;
        return Objects.equals(userId, booking.getUserId())
                && Objects.equals(rideId, booking.getRideId());
    }

}
