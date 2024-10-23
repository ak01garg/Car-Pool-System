package com.CraftDemo.CarPoolApplication.services.rideselection;


import com.CraftDemo.CarPoolApplication.algorithms.RideFilterService;
import com.CraftDemo.CarPoolApplication.dto.pojo.RideFilter;
import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.interfaces.RideSelectionStrategy;
import com.CraftDemo.CarPoolApplication.models.Ride;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.CraftDemo.CarPoolApplication.utils.GraphUtils.generateCompositeLocationKey;

public class MostVacantRideSelectionStrategy implements RideSelectionStrategy {

    private final RideFilterService rideFilterService;

    public MostVacantRideSelectionStrategy() {
        rideFilterService = new RideFilterService();
    }



    @Override
    public List<Ride> selectRide(RideSelectionRequest rideSelectionRequest) {
        if (Objects.isNull(rideSelectionRequest)) {
            return Collections.emptyList();
        }
        RideFilter rideFilter = new RideFilter(rideSelectionRequest.getSource(),
                rideSelectionRequest.getDestination(), true);
        List<Ride> rideList = rideFilterService.doFilter(rideFilter);
        if(!CollectionUtils.isEmpty(rideList)){
            Map<String, List<Ride>> sourceDestinationRideMap = groupRidesBySourceDestination(rideList);
            boolean canReachDestination = rideList.stream().anyMatch(ride ->
                    ride.getDestination().getName().equals(rideSelectionRequest.getDestination()))
                    && rideList.stream().anyMatch(ride ->
                    ride.getSource().getName().equals(rideSelectionRequest.getSource()));
            if (canReachDestination) {
                List<Ride> selectedRides = getDirectRides(rideSelectionRequest, sourceDestinationRideMap);
                return CollectionUtils.isEmpty(selectedRides) ? getIntermediateRides(sourceDestinationRideMap) : selectedRides;
            }
        }
        return Collections.emptyList();
    }

    private Map<String, List<Ride>> groupRidesBySourceDestination(List<Ride> rideList) {
        return rideList.stream()
                .collect(Collectors.groupingBy(ride -> generateCompositeLocationKey(
                        ride.getSource().getName(),
                        ride.getDestination().getName())));
    }

    private List<Ride> getDirectRides(RideSelectionRequest rideSelectionRequest, Map<String, List<Ride>> sourceDestinationRideMap) {
        String compositeKey = generateCompositeLocationKey(rideSelectionRequest.getSource(), rideSelectionRequest.getDestination());
        if (sourceDestinationRideMap.containsKey(compositeKey)) {
            return sourceDestinationRideMap.get(compositeKey).stream()
                    .filter(ride -> ride.getVacantSeats() >= rideSelectionRequest.getSeats())
                    .sorted(Comparator.comparingInt(Ride::getVacantSeats).reversed()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private List<Ride> getIntermediateRides(Map<String, List<Ride>> sourceDestinationRideMap) {
        List<Ride> availableRides = new ArrayList<>();
        for (String key : sourceDestinationRideMap.keySet()) {
            List<Ride> intermediateRides = sourceDestinationRideMap.get(key);
            availableRides.add(intermediateRides.stream().max(Comparator.comparingInt(Ride::getVacantSeats)).get());
        }
        return availableRides;
    }
}
