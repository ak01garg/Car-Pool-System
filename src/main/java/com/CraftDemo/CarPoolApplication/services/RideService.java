package com.CraftDemo.CarPoolApplication.services;



import com.CraftDemo.CarPoolApplication.Repositories.RideRepository;
import com.CraftDemo.CarPoolApplication.dto.request.EndRideRequest;
import com.CraftDemo.CarPoolApplication.dto.request.RideBookingRequest;
import com.CraftDemo.CarPoolApplication.dto.request.RideOfferRequest;
import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.exceptions.*;
import com.CraftDemo.CarPoolApplication.factories.RideSelectionStrategyFactory;
import com.CraftDemo.CarPoolApplication.interfaces.RideSelectionStrategy;
import com.CraftDemo.CarPoolApplication.models.Location;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.models.Vehicle;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.CraftDemo.CarPoolApplication.utils.BookingConstants.DUPLICATE_BOOKING_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.BookingConstants.SELF_RIDE_BOOKING_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.RideConstants.*;
import static com.CraftDemo.CarPoolApplication.utils.UserConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.VehicleConstants.VEHICLE_NOT_FOUND_EXCEPTION;


public class RideService {

    private final RideRepository rideRepository;;
    private final BookingService bookingService;
    private final UserService userService;
    private final VehicleService vehicleService;

    public RideService() {
        this.rideRepository = new RideRepository();
        this.bookingService = new BookingService();
        this.userService = new UserService();
        this.vehicleService = new VehicleService();
    }

    public void offerRide(RideOfferRequest rideOfferRequest){
        ValidationUtils.ensureNotNull(rideOfferRequest , NULL_RIDE_VALIDATION_MESSAGE);

        User user = userService.getUserByName(rideOfferRequest.getUserName());
        Vehicle vehicle = vehicleService.getVehicleByRegNo(rideOfferRequest.getVehicleRegNo());

        ValidationUtils.ensureNotNull(user , new UserNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
        ValidationUtils.ensureNotNull(vehicle , new VehicleNotFoundException(VEHICLE_NOT_FOUND_EXCEPTION));

        boolean doesUserOwnThisVehicle = !CollectionUtils.isEmpty(user.getVehicleList())
                && user.getVehicleList().stream().anyMatch(v -> Objects.equals(v.getRegNo(), vehicle.getRegNo()));

        ValidationUtils.ensureTrue(doesUserOwnThisVehicle,new InvalidOwnerException(INVALID_OWNER_EXCEPTION_MESSAGE));
        ValidationUtils.ensureTrue(!vehicleService.doesVehicleHaveActiveRides(rideOfferRequest.getVehicleRegNo()),
                new ActiveVehicleRideException(DUPLICATE_RIDE_EXCEPTION_MESSAGE));

        Ride ride = new Ride(user.getId() ,
                null ,
                rideOfferRequest.getAvailableSeats(),
                vehicle.getId(),
                new Location(rideOfferRequest.getSource(),null) ,
                new Location(rideOfferRequest.getDestination(),null), new Date() , null);

        rideRepository.addRide(ride);
        vehicleService.createActiveRideForVehicle(vehicle.getId(),ride.getId());
    }


    public List<Ride> selectRide(RideSelectionRequest rideSelectionRequest){
        if(Objects.nonNull(rideSelectionRequest)){
            RideSelectionStrategy rideSelectionStrategyForCommand =
                    RideSelectionStrategyFactory.getRideSelectionStratgeyForCommand(
                            rideSelectionRequest.getRideSelectionType());
            if(Objects.nonNull(rideSelectionStrategyForCommand)){
                List<Ride> rides = rideSelectionStrategyForCommand.selectRide(rideSelectionRequest);
                if(!CollectionUtils.isEmpty(rides)){
                    for (Ride selectedRide : rides) {
                        User userByName = userService.getUserByName(rideSelectionRequest.getRequestByUserName());
                        ValidationUtils.ensureNotNull(userByName ,
                                new UserNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
                        ValidationUtils.ensureTrue(!Objects.equals(selectedRide.getDriverId(),userByName.getId()) ,
                                new ValidationException(SELF_RIDE_BOOKING_EXCEPTION_MESSAGE));
                        ValidationUtils.ensureTrue(!bookingService.isRideAlreadyBookedByUser(
                                        userByName.getId(), selectedRide.getId()),
                                new BookingAlreadyExistException(DUPLICATE_BOOKING_EXCEPTION_MESSAGE));
                        RideBookingRequest rideBookingRequest = new RideBookingRequest(userByName.getId(),
                                selectedRide.getId(), selectedRide.getVehicleUsed(), rideSelectionRequest.getSeats());
                        bookingService.bookRide(rideBookingRequest);
                    }
                    return rides;
                }

            }
        }
        return Collections.emptyList();
    }


    public void endRide(EndRideRequest endRideRequest){
        ValidationUtils.ensureNotNull(endRideRequest , NULL_RIDE_VALIDATION_MESSAGE);
        User user = userService.getUserByName(endRideRequest.getUserName());
        ValidationUtils.ensureNotNull(user,new UserNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
        Vehicle vehicle = vehicleService.getVehicleByRegNo(endRideRequest.getVehicleRegNo());
        ValidationUtils.ensureNotNull(user,new VehicleNotFoundException(VEHICLE_NOT_FOUND_EXCEPTION));
        Map<String, List<Ride>> userIdRideMapInstance = rideRepository.getUserIdRideMapInstance();
        List<Ride> rides = userIdRideMapInstance.get(user.getId());
        if(!CollectionUtils.isEmpty(rides)){
            for (Ride ride : rides) {
                if (Objects.equals(ride.getVehicleUsed(),vehicle.getId())) {
                    rideRepository.endRide(ride);
                    vehicleService.endRideForVehicle(vehicle.getId(),ride.getId());
                }
            }
            return;
        }
        throw new RideNotFoundException(RIDE_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    public Map<String, List<Ride>> getAllRides(){
        return rideRepository.getUserIdRideMapInstance();
    }

    public Ride getRideById(String rideId){
        return rideRepository.getRideIdRideMapInstance().get(rideId);
    }

    public List<Ride> getRidesOfferedByUser(String userId){
        return rideRepository.getUserIdRideMapInstance().get(userId);
    }
}
