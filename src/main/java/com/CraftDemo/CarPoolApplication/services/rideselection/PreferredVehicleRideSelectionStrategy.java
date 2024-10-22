package com.CraftDemo.CarPoolApplication.services.rideselection;


import com.CraftDemo.CarPoolApplication.Repositories.VehicleRepository;
import com.CraftDemo.CarPoolApplication.algorithms.RideFilterService;
import com.CraftDemo.CarPoolApplication.dto.pojo.RideFilter;
import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.interfaces.RideSelectionStrategy;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.models.Vehicle;

import java.util.*;

public class PreferredVehicleRideSelectionStrategy implements RideSelectionStrategy {

    private final VehicleRepository vehicleRepository;
    private final RideFilterService rideFilterService;


    public PreferredVehicleRideSelectionStrategy() {
        rideFilterService = new RideFilterService();
        vehicleRepository = new VehicleRepository();
    }

    @Override
    public String generateCompositeKey(String source, String destination) {
        return "";
    }

    @Override
    public List<Ride> selectRide(RideSelectionRequest rideSelectionRequest) {
        if(Objects.nonNull(rideSelectionRequest)){
            RideFilter rideFilter = new RideFilter(rideSelectionRequest.getSource() ,rideSelectionRequest.getDestination(),false);
            List<Ride> rideList = rideFilterService.doFilter(rideFilter);
            if(Objects.nonNull(rideList) && !rideList.isEmpty()){
                for (Ride ride : rideList) {
                    Map<String, Vehicle> vehicleIdMapInstance = vehicleRepository.getVehicleIdMapInstance();
                    Vehicle vehicle = vehicleIdMapInstance.get(ride.getVehicleUsed());
                    if (ride.getVacantSeats() >= rideSelectionRequest.getSeats() &&
                            Objects.equals(vehicle.getName(), rideSelectionRequest.getPreferredVehicleName())) {
                        return Collections.singletonList(ride);
                    }
                }
            }
        }
        return Collections.emptyList();
    }
}
