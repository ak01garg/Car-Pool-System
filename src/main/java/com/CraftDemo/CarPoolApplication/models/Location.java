package com.CraftDemo.CarPoolApplication.models;


import com.CraftDemo.CarPoolApplication.dto.pojo.Coordinates;

public class Location extends BaseModel{

    private String name;
    private Coordinates coordinates;

    public Location(String name, Coordinates coordinates) {
        super();
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
