package com.CraftDemo.CarPoolApplication.algorithms;


import com.CraftDemo.CarPoolApplication.Repositories.RideRepository;
import com.CraftDemo.CarPoolApplication.algorithms.interfaces.FilterService;
import com.CraftDemo.CarPoolApplication.dto.Graph.RideGraphNode;
import com.CraftDemo.CarPoolApplication.dto.pojo.RideFilter;
import com.CraftDemo.CarPoolApplication.models.Ride;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;



public class RideFilterService implements FilterService<RideFilter, Ride> {


    private final RideRepository rideRepository;
    private final RideGraphSearch rideGraphSearch;

    public RideFilterService() {
        this.rideGraphSearch = new RideGraphSearch();
        this.rideRepository = new RideRepository();
    }


    @Override
    public List<Ride> doFilter(RideFilter rideFilter) {
        if(Objects.nonNull(rideFilter)){
            if(!rideFilter.isFilterTransitively()){
                Map<String, Ride> rideIdRideMapInstance = rideRepository.getRideIdRideMapInstance();
                return rideIdRideMapInstance.values().stream()
                        .filter(ride -> Objects.equals(rideFilter.getSource(), ride.getSource().getName())
                        && Objects.equals(rideFilter.getDestination(), ride.getDestination().getName()))
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>(rideGraphSearch.execute(new RideGraphNode(rideFilter.getSource()),
                new RideGraphNode(rideFilter.getDestination()),rideRepository.getRideGraphInstance()));

    }

}
