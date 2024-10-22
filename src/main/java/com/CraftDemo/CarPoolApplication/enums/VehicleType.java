package com.CraftDemo.CarPoolApplication.enums;

public enum VehicleType {
    SEDAN(4) , SUV(6) , MUV(8) , TRUCK(10) , TWO_WHEELER(2) , THREE_WHEELER(3);
    private final Integer maxSeats;

    VehicleType(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public static VehicleType getDefaultVehicleType(){
        return SEDAN;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }
}
