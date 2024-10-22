package com.CraftDemo.CarPoolApplication.models;


import com.CraftDemo.CarPoolApplication.enums.Gender;

import java.util.List;
import java.util.Objects;

public class User extends BaseModel {

    private String name;
    private Integer age;
    private Gender gender;
    private List<Vehicle> vehicleList;

    public User(String name, Integer age, Gender gender) {
        super();
        this.name = name;
        this.age = age;
        this.gender = gender;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(age, user.getAge())
                && Objects.equals(name, user.getName())
                && Objects.equals(gender,user.getGender());
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
