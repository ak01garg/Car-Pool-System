package com.CraftDemo.CarPoolApplication.services;



import com.CraftDemo.CarPoolApplication.Repositories.VehicleRepository;
import com.CraftDemo.CarPoolApplication.dto.request.AddVehicleRequest;
import com.CraftDemo.CarPoolApplication.enums.VehicleType;
import com.CraftDemo.CarPoolApplication.exceptions.UserNotFoundException;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.models.Vehicle;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import java.util.*;

import static com.CraftDemo.CarPoolApplication.utils.UserConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.VehicleConstants.NULL_VEHICLE_VALIDATION_MESSAGE;


public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    public VehicleService() {
        vehicleRepository = new VehicleRepository();
        userService = new UserService();
    }

    public Vehicle getById(String vehicleId){
        return vehicleRepository.getVehicleIdMapInstance().get(vehicleId);
    }

    public boolean doesVehicleHaveActiveRides(String regNo){
        Map<String, Vehicle> regNoVehicleMapInstance = vehicleRepository.getRegNoVehicleMapInstance();
        Vehicle vehicle = regNoVehicleMapInstance.get(regNo);
        Map<String, String> activeVehicleRideMapInstance = vehicleRepository.getActiveVehicleRideMapInstance();
        return Objects.nonNull(activeVehicleRideMapInstance.get(vehicle.getId()));
    }

    public void createActiveRideForVehicle(String vehicleId , String rideId){
        Map<String, String> activeVehicleRideMapInstance = vehicleRepository.getActiveVehicleRideMapInstance();
        activeVehicleRideMapInstance.put(vehicleId,rideId);
    }

    public void endRideForVehicle(String vehicleId , String rideId){
        Map<String, String> activeVehicleRideMapInstance = vehicleRepository.getActiveVehicleRideMapInstance();
        activeVehicleRideMapInstance.remove(vehicleId);
    }

    public Vehicle getVehicleByRegNo(String regNo){
        Map<String, Vehicle> regNoVehicleMapInstance = vehicleRepository.getRegNoVehicleMapInstance();
        return regNoVehicleMapInstance.get(regNo);
    }

    public void addVehicle(AddVehicleRequest addVehicleRequest){
        ValidationUtils.ensureNotNull(addVehicleRequest,NULL_VEHICLE_VALIDATION_MESSAGE);
        User user = userService.getUserByName(addVehicleRequest.getUserName());
        ValidationUtils.ensureNotNull(user,new UserNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
        List<String> owners = Collections.singletonList(user.getId());
        Vehicle vehicle = new Vehicle(addVehicleRequest.getVehicleName()
                , addVehicleRequest.getRegNo(),
                VehicleType.getDefaultVehicleType(),VehicleType.getDefaultVehicleType().getMaxSeats());

        List<Vehicle> vehicleList = Objects.nonNull(user.getVehicleList())
                ? user.getVehicleList() : new ArrayList<>();

        vehicleList.add(vehicle);
        user.setVehicleList(vehicleList);
        vehicleRepository.addVehicle(vehicle);
        userService.updateUser(user);
    }
}
