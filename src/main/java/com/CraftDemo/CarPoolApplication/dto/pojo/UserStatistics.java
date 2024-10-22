package com.CraftDemo.CarPoolApplication.dto.pojo;

public class UserStatistics {
    private String userName;
    private Integer ridesOffered;
    private Integer ridesCompleted;
    private Integer ridesBooked;

    public UserStatistics(String userName , Integer ridesOffered, Integer ridesCompleted) {
        this.userName = userName;
        this.ridesOffered = ridesOffered;
        this.ridesCompleted = ridesCompleted;
    }

    public Integer getRidesOffered() {
        return ridesOffered;
    }

    public void setRidesOffered(Integer ridesOffered) {
        this.ridesOffered = ridesOffered;
    }

    public Integer getRidesCompleted() {
        return ridesCompleted;
    }

    public void setRidesCompleted(Integer ridesCompleted) {
        this.ridesCompleted = ridesCompleted;
    }

    public Integer getRidesBooked() {
        return ridesBooked;
    }

    public void setRidesBooked(Integer ridesBooked) {
        this.ridesBooked = ridesBooked;
    }

    @Override
    public String toString() {
        return  userName +
                ": " + ridesCompleted + " Taken"  +
                ", " + ridesOffered + " Offered";
    }
}
