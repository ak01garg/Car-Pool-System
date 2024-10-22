package com.CraftDemo.CarPoolApplication.models;



import com.CraftDemo.CarPoolApplication.enums.RideStatus;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Ride extends BaseModel {

    private String driverId;
    private List<String> passengers;
    private Integer vacantSeats;
    private String vehicleUsed;
    private Location source;
    private Location destination;
    private Date startTime;
    private Date endTime;
    private RideStatus rideStatus;

    public Ride(String driverId, List<String> passengers, Integer vacantSeats, String vehicleUsed,
                Location source, Location destination, Date startTime, Date endTime) {
        super();
        this.driverId = driverId;
        this.passengers = passengers;
        this.vacantSeats = vacantSeats;
        this.vehicleUsed = vehicleUsed;
        this.source = source;
        this.destination = destination;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rideStatus = RideStatus.ACTIVE;
    }

    public Ride(Ride newRide){
        this.id = newRide.getId();
        this.driverId = newRide.getDriverId();
        this.passengers = newRide.getPassengers();
        this.vacantSeats = newRide.getVacantSeats();
        this.vehicleUsed = newRide.getVehicleUsed();
        this.source = newRide.getSource();
        this.destination = newRide.getDestination();
        this.startTime = newRide.getStartTime();
        this.endTime = newRide.getEndTime();
        this.rideStatus = newRide.getRideStatus();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Integer getVacantSeats() {
        return vacantSeats;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<String> passengers) {
        this.passengers = passengers;
    }

    public String getVehicleUsed() {
        return vehicleUsed;
    }

    public void setVehicleUsed(String vehicleUsed) {
        this.vehicleUsed = vehicleUsed;
    }

    public Location getSource() {
        return source;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ride ride = (Ride) o;
        return Objects.equals(driverId, ride.getDriverId())
                && Objects.equals(vehicleUsed, ride.getVehicleUsed())
                && Objects.equals(source,ride.getSource())
                && Objects.equals(destination , ride.getDestination());
    }


    public void setVacantSeats(Integer vacantSeats) {
        this.vacantSeats = vacantSeats;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "driverId='" + driverId + '\'' +
                ", passengers=" + passengers +
                ", vacantSeats=" + vacantSeats +
                ", vehicleUsed='" + vehicleUsed + '\'' +
                ", source=" + source.getName() +
                ", destination=" + destination.getName() +
                ", startTime=" + startTime +
                ", rideStatus=" + rideStatus +
                '}';
    }
}
