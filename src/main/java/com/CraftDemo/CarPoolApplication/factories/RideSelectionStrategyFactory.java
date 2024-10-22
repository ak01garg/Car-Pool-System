package com.CraftDemo.CarPoolApplication.factories;

import com.CraftDemo.CarPoolApplication.enums.RideSelectionType;
import com.CraftDemo.CarPoolApplication.interfaces.RideSelectionStrategy;
import com.CraftDemo.CarPoolApplication.services.rideselection.MostVacantRideSelectionStrategy;
import com.CraftDemo.CarPoolApplication.services.rideselection.PreferredVehicleRideSelectionStrategy;

import java.util.*;

public class RideSelectionStrategyFactory {

    public RideSelectionStrategyFactory(){
    }

    private static class RideSelectionFactoryHelper{
        private static final Map<RideSelectionType, RideSelectionStrategy> rideSelectionStrategyMap = new HashMap<>();
        static {
            rideSelectionStrategyMap.put(RideSelectionType.MOST_VACANT, new MostVacantRideSelectionStrategy());
            rideSelectionStrategyMap.put(RideSelectionType.PREFERRED_VEHICLE, new PreferredVehicleRideSelectionStrategy());
        }
    }

    public static RideSelectionStrategy getRideSelectionStratgeyForCommand(RideSelectionType rideSelectionType){
        return RideSelectionFactoryHelper.rideSelectionStrategyMap.get(rideSelectionType);
    }
}
