package com.CraftDemo.CarPoolApplication.utils;

import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.models.Ride;

import java.util.*;
import java.util.stream.Collectors;

import static com.CraftDemo.CarPoolApplication.utils.GraphUtils.generateCompositeLocationKey;

public class RideUtils {

    public static Map<String, List<Ride>> groupRidesBySourceDestination(List<Ride> rideList) {
        return rideList.stream()
                .collect(Collectors.groupingBy(ride -> generateCompositeLocationKey(
                        ride.getSource().getName(),
                        ride.getDestination().getName())));
    }




}
