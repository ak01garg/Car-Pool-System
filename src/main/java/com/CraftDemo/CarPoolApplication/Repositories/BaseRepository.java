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

    private static class BaseRpositoryHelper{
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
        return BaseRpositoryHelper.userIdUserMap;
    }

    public Map<String,User> getUserNameMapInstance(){
        return BaseRpositoryHelper.userNameUserMap;
    }
    public Map<String,String> getActiveVehicleRideMapInstance(){
        return BaseRpositoryHelper.activeVehicleRideMap;
    }

    public Map<String,Vehicle> getVehicleIdMapInstance(){
        return BaseRpositoryHelper.vehicleIdVehicleMap;
    }

    public Map<String,Vehicle> getRegNoVehicleMapInstance(){
        return BaseRpositoryHelper.regNoVehicleMap;
    }

    public Map<String,Ride> getRideIdRideMapInstance(){
        return BaseRpositoryHelper.rideIdRideMap;
    }

    public Map<String,List<Ride>> getUserIdRideMapInstance(){
        return BaseRpositoryHelper.userIdRideMap;
    }

    public Graph<RideGraphNode> getRideGraphInstance(){
        return BaseRpositoryHelper.locationRideGraph;
    }

    public Map<String,List<Ride>> getLocationRideMapInstance(){
        return BaseRpositoryHelper.locationRideMap;
    }

    public void populateRideGraphMap(RideGraphNode source , RideGraphNode destination){
        Graph<RideGraphNode> rideGraphInstance = getRideGraphInstance();
        Map<Object, RideGraphNode> nodeIdentifierMap = rideGraphInstance.getNodeIdentifierMap();
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

    public void updateRideGraphMap(RideGraphNode source , RideGraphNode destination){
        Graph<RideGraphNode> rideGraphInstance = getRideGraphInstance();
        rideGraphInstance.removeNodes(source,destination);
    }

    public void addNodeIdentifier(Object identifier, RideGraphNode node) {
        Graph<RideGraphNode> rideGraphInstance = getRideGraphInstance();
        rideGraphInstance.addNodeIdentifier(identifier, node);
    }

    public void removeNodeIdentifier(Object identifier) {
        Graph<RideGraphNode> rideGraphInstance = getRideGraphInstance();
        rideGraphInstance.removeNodeIdentifier(identifier);
    }

    public Map<String,Booking> getBookingIdMapInstance(){
        return BaseRpositoryHelper.bookingIdBookingMap;
    }

    public Map<String,List<Booking>> getUserIdBookingIdMapInstance(){
        return BaseRpositoryHelper.userIdBookingMap;
    }
}
