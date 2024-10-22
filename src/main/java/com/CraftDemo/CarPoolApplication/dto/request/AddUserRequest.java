package com.CraftDemo.CarPoolApplication.dto.request;


import com.CraftDemo.CarPoolApplication.enums.Gender;

public class AddUserRequest {

    private String name;
    private Integer age;
    private Gender gender;

    public AddUserRequest(String name, Integer age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "AddUserRequest{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
