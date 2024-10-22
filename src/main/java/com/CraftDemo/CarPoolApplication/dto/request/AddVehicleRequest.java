package com.CraftDemo.CarPoolApplication.dto.request;

public class AddVehicleRequest {

    private String userName;
    private String regNo;
    private String vehicleName;

    public AddVehicleRequest(String userName, String regNo, String vehicleName) {
        this.userName = userName;
        this.regNo = regNo;
        this.vehicleName = vehicleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Override
    public String toString() {
        return "VehicleRegistration{" +
                "userName='" + userName + '\'' +
                ", regNo='" + regNo + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                '}';
    }
}
