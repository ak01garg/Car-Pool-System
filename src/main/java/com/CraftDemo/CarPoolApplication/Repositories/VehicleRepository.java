package com.CraftDemo.CarPoolApplication.Repositories;

import com.CraftDemo.CarPoolApplication.exceptions.VehicleAlreadyRegisteredException;
import com.CraftDemo.CarPoolApplication.models.Vehicle;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.CraftDemo.CarPoolApplication.utils.VehicleConstants.DUPLICATE_VEHICLE_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.VehicleConstants.NULL_VEHICLE_VALIDATION_MESSAGE;


public class VehicleRepository extends BaseRepository {


    private static class VehicleRepositoryInitializer{
        private static final Map<String,String> activeVehicleRideMap  = new HashMap<>();
        private static final Map<String, Vehicle> vehicleIdVehicleMap = new HashMap<>();
        private static final Map<String, Vehicle> regNoVehicleMap = new HashMap<>();
    }

    public Map<String,Vehicle> getVehicleIdMapInstance(){
        return VehicleRepositoryInitializer.vehicleIdVehicleMap;
    }

    public Map<String,Vehicle> getRegNoVehicleMapInstance(){
        return VehicleRepositoryInitializer.regNoVehicleMap;
    }


    public Map<String,String> getActiveVehicleRideMapInstance(){
        return VehicleRepositoryInitializer.activeVehicleRideMap;
    }



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
