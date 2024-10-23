package com.CraftDemo.CarPoolApplication.Repositories;

import com.CraftDemo.CarPoolApplication.dto.Graph.RideGraphNode;
import com.CraftDemo.CarPoolApplication.exceptions.RideAlreadyExistException;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import java.util.*;

import static com.CraftDemo.CarPoolApplication.enums.RideStatus.ACTIVE;
import static com.CraftDemo.CarPoolApplication.enums.RideStatus.EXPIRED;
import static com.CraftDemo.CarPoolApplication.utils.RideConstants.DUPLICATE_RIDE_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.RideConstants.NULL_RIDE_VALIDATION_MESSAGE;


public class RideRepository extends BaseRepository{


    private static class RideRepositoryInitializer{
        private static final Map<String, Ride> rideIdRideMap = new HashMap<>();
        private static final Map<String, List<Ride>> userIdRideMap = new HashMap<>();
        private static final Map<String, List<Ride>> locationRideMap = new HashMap<>();
    }

    public Map<String, Ride> getRideIdRideMapInstance(){
        return RideRepositoryInitializer.rideIdRideMap;
    }

    public Map<String, List<Ride>> getUserIdRideMapInstance(){
        return RideRepositoryInitializer.userIdRideMap;
    }

    public Map<String, List<Ride>> getLocationRideMapInstance(){
        return RideRepositoryInitializer.locationRideMap;
    }

    public void endRide(Ride ride){
        ValidationUtils.ensureNotNull(ride , NULL_RIDE_VALIDATION_MESSAGE);
        ride.setRideStatus(EXPIRED);
        Map<String, List<Ride>> userIdRideMapInstance = getUserIdRideMapInstance();
        Map<String, List<Ride>> locationRideMapInstance = getLocationRideMapInstance();
        Map<String, Ride> rideIdRideMapInstance = getRideIdRideMapInstance();
        List<Ride> dbRides = userIdRideMapInstance.get(ride.getDriverId());
        rideIdRideMapInstance.put(ride.getId(),ride);
        dbRides.stream().filter(dbRide -> dbRide.equals(ride)).forEach(dbRide -> dbRide.setRideStatus(EXPIRED));
        userIdRideMapInstance.put(ride.getDriverId(),dbRides);
        locationRideMapInstance.put(ride.getSource().getName(),dbRides);
        locationRideMapInstance.put(ride.getDestination().getName(), dbRides);
    }

    public void addRide(Ride ride){
        ValidationUtils.ensureNotNull(ride , NULL_RIDE_VALIDATION_MESSAGE);
        Map<String, Ride> rideIdRideMapInstance = getRideIdRideMapInstance();
        Map<String, List<Ride>> userIdRideMapInstance = getUserIdRideMapInstance();
        Map<String, List<Ride>> locationRideMapInstance = getLocationRideMapInstance();
        List<Ride> rides = userIdRideMapInstance.get(ride.getDriverId());
        if(Objects.isNull(rides) || rides.isEmpty() ||
                rides.stream().noneMatch(r -> r.equals(ride) && Objects.equals(r.getRideStatus(),ACTIVE))){
            rideIdRideMapInstance.put(ride.getId(),ride);
            userIdRideMapInstance.computeIfAbsent(ride.getDriverId(), (k-> new ArrayList<>())).add(ride);
            locationRideMapInstance.computeIfAbsent(ride.getSource().getName(), (k -> new ArrayList<>())).add(ride);
            locationRideMapInstance.computeIfAbsent(ride.getDestination().getName() , (k-> new ArrayList<>())).add(ride);
            RideGraphNode srcNode = new RideGraphNode(ride.getSource().getName());
            RideGraphNode destNode = new RideGraphNode(ride.getDestination().getName());
            populateRideGraphMap(srcNode, destNode);
            return;
        }
        throw new RideAlreadyExistException(DUPLICATE_RIDE_EXCEPTION_MESSAGE);
    }

    public void updateRide(Ride ride){
        ValidationUtils.ensureNotNull(ride , NULL_RIDE_VALIDATION_MESSAGE);
        Map<String, Ride> rideIdRideMapInstance = getRideIdRideMapInstance();
        rideIdRideMapInstance.put(ride.getId() , ride);
    }


}
