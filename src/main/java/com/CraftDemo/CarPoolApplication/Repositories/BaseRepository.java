package com.CraftDemo.CarPoolApplication.Repositories;

import com.CraftDemo.CarPoolApplication.dto.Graph.Graph;
import com.CraftDemo.CarPoolApplication.dto.Graph.RideGraphNode;
import com.CraftDemo.CarPoolApplication.models.Booking;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.models.Vehicle;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRepository {

    private static class BaseRepositoryHelper {
        private static final Map<String, User> userIdUserMap = new HashMap<>();
        private static final Map<String, User> userNameUserMap = new HashMap<>();
        private static final Map<String, Vehicle> vehicleIdVehicleMap = new HashMap<>();
        private static final Map<String, Vehicle> regNoVehicleMap = new HashMap<>();
        private static final Map<String, Ride> rideIdRideMap = new HashMap<>();
        private static final Map<String,String> activeVehicleRideMap  =new HashMap<>();
        private static final Map<String, List<Ride>> userIdRideMap  = new HashMap<>();
        private static final Map<String,List<Ride>> locationRideMap = new HashMap<>();
        private static final Graph<RideGraphNode> locationRideGraph = new Graph<>();
        private static final Map<String, Booking> bookingIdBookingMap = new HashMap<>();
        private static final Map<String,List<Booking>> userIdBookingMap = new HashMap<>();

    }

    public Map<String,User> getUserIdMapInstance(){
        return BaseRepositoryHelper.userIdUserMap;
    }

    public Map<String,User> getUserNameMapInstance(){
        return BaseRepositoryHelper.userNameUserMap;
    }
    public Map<String,String> getActiveVehicleRideMapInstance(){
        return BaseRepositoryHelper.activeVehicleRideMap;
    }

    public Map<String,Vehicle> getVehicleIdMapInstance(){
        return BaseRepositoryHelper.vehicleIdVehicleMap;
    }

    public Map<String,Vehicle> getRegNoVehicleMapInstance(){
        return BaseRepositoryHelper.regNoVehicleMap;
    }

    public Map<String,Ride> getRideIdRideMapInstance(){
        return BaseRepositoryHelper.rideIdRideMap;
    }

    public Map<String,List<Ride>> getUserIdRideMapInstance(){
        return BaseRepositoryHelper.userIdRideMap;
    }

    public Graph<RideGraphNode> getRideGraphInstance(){
        return BaseRepositoryHelper.locationRideGraph;
    }

    public Map<String,List<Ride>> getLocationRideMapInstance(){
        return BaseRepositoryHelper.locationRideMap;
    }

    public void populateRideGraphMap(RideGraphNode source , RideGraphNode destination){
        Graph<RideGraphNode> rideGraphInstance = getRideGraphInstance();
        Map<String, RideGraphNode> nodeIdentifierMap = rideGraphInstance.getNodeIdentifierMap();
        if(!nodeIdentifierMap.containsKey(source.getLocation())
                && !nodeIdentifierMap.containsKey(destination.getLocation())){
            rideGraphInstance.addNodeIdentifier(source.getLocation(),source);
            rideGraphInstance.addNodeIdentifier(destination.getLocation(),destination);
            rideGraphInstance.addNodes(source , destination);
        }else if (!nodeIdentifierMap.containsKey(source.getLocation())){
            RideGraphNode existingDestinationNode = nodeIdentifierMap.get(destination.getLocation());
            nodeIdentifierMap.put(source.getLocation(),source);
            rideGraphInstance.addNodes(source , existingDestinationNode);
        }else if (!nodeIdentifierMap.containsKey(destination.getLocation())) {
            RideGraphNode existingSourceNode = nodeIdentifierMap.get(source.getLocation());
            nodeIdentifierMap.put(destination.getLocation(), destination);
            rideGraphInstance.addNodes(existingSourceNode, destination);
        }
    }

    public Map<String,Booking> getBookingIdMapInstance(){
        return BaseRepositoryHelper.bookingIdBookingMap;
    }

    public Map<String,List<Booking>> getUserIdBookingIdMapInstance(){
        return BaseRepositoryHelper.userIdBookingMap;
    }
}
