package com.CraftDemo.CarPoolApplication.Repositories;

import com.CraftDemo.CarPoolApplication.exceptions.VehicleAlreadyRegisteredException;
import com.CraftDemo.CarPoolApplication.models.Vehicle;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import java.util.Map;
import java.util.Objects;

import static com.CraftDemo.CarPoolApplication.utils.VehicleConstants.DUPLICATE_VEHICLE_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.VehicleConstants.NULL_VEHICLE_VALIDATION_MESSAGE;


public class VehicleRepository extends BaseRepository {

    public void addVehicle(Vehicle vehicle){
        ValidationUtils.ensureNotNull(vehicle , NULL_VEHICLE_VALIDATION_MESSAGE);
        Map<String, Vehicle> regNoVehicleMapInstance = getRegNoVehicleMapInstance();
        Map<String, Vehicle> vehicleIdMapInstance = getVehicleIdMapInstance();

        if(Objects.isNull(regNoVehicleMapInstance.get(vehicle.getRegNo()))){
            regNoVehicleMapInstance.put(vehicle.getRegNo() , vehicle);
            vehicleIdMapInstance.put(vehicle.getId(),vehicle);
            return;
        }
        throw new VehicleAlreadyRegisteredException(DUPLICATE_VEHICLE_EXCEPTION_MESSAGE);
    }

}
