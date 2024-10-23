package com.CraftDemo.CarPoolApplication.algorithms;

import com.CraftDemo.CarPoolApplication.Repositories.RideRepository;
import com.CraftDemo.CarPoolApplication.algorithms.interfaces.SingleSourceDFS;
import com.CraftDemo.CarPoolApplication.dto.Graph.Graph;
import com.CraftDemo.CarPoolApplication.dto.Graph.RideGraphNode;
import com.CraftDemo.CarPoolApplication.models.Ride;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RideGraphSearch implements SingleSourceDFS<RideGraphNode, Ride> {

    private static Set<RideGraphNode> traversalStateMap;
    private final RideRepository rideRepository;

    public RideGraphSearch(){
        rideRepository = new RideRepository();
        traversalStateMap = new HashSet<>();
    }

    @Override
    public Set<Ride> execute(RideGraphNode src, RideGraphNode destination, Graph<RideGraphNode> graph) {
        Set<Ride> filteredRides = new HashSet<>();
        if(Objects.nonNull(src) && Objects.nonNull(destination) && Objects.nonNull(graph)){
            Map<RideGraphNode, List<RideGraphNode>> rideGraphMap = graph.getRideGraphMap();
            Map<String, RideGraphNode> nodeIdentifierMap = graph.getNodeIdentifierMap();
            Map<String, List<Ride>> locationRideMapInstance = rideRepository.getLocationRideMapInstance();
            if(!CollectionUtils.isEmpty(locationRideMapInstance.get(src.getLocation()))){
                filteredRides.addAll(locationRideMapInstance.get(src.getLocation()));
            }
            if(!CollectionUtils.isEmpty(nodeIdentifierMap)
                    && Objects.nonNull(nodeIdentifierMap.get(src.getLocation()))
                    && !Objects.equals(src.getLocation(),destination.getLocation())){
                RideGraphNode sourceNode = nodeIdentifierMap.get(src.getLocation());
                traversalStateMap.add(sourceNode);
                for (RideGraphNode intermediateNode : rideGraphMap.get(sourceNode)){
                    if(!traversalStateMap.contains(intermediateNode)){
                        filteredRides.addAll(execute(intermediateNode, destination, graph));
                    }
                }
            }
        }
        return filteredRides;
    }
}
