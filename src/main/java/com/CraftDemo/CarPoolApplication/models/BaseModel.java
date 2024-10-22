package com.CraftDemo.CarPoolApplication.models;

import java.util.UUID;

public class BaseModel {
    public String id;

    public BaseModel(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId(){
        return this.id;
    }
}
