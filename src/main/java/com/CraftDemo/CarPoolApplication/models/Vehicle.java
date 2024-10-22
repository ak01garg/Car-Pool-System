package com.CraftDemo.CarPoolApplication.models;



import com.CraftDemo.CarPoolApplication.enums.VehicleType;

import java.util.Objects;

public class Vehicle extends BaseModel {

    private String name;
    private String regNo;
    private VehicleType vehicleType;
    private Integer maxSeats;

    public Vehicle(String name, String regNo, VehicleType vehicleType, Integer maxSeats) {
        super();
        this.name = name;
        this.regNo = regNo;
        this.vehicleType = vehicleType;
        this.maxSeats = maxSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(regNo, vehicle.getRegNo())
                && Objects.equals(name, vehicle.getName());
    }
}
