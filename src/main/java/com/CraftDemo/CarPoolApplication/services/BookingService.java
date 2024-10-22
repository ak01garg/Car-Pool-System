package com.CraftDemo.CarPoolApplication.services;


import com.CraftDemo.CarPoolApplication.Repositories.BookingRepository;
import com.CraftDemo.CarPoolApplication.Repositories.RideRepository;
import com.CraftDemo.CarPoolApplication.dto.request.RideBookingRequest;
import com.CraftDemo.CarPoolApplication.exceptions.RideFullException;
import com.CraftDemo.CarPoolApplication.exceptions.RideNotFoundException;
import com.CraftDemo.CarPoolApplication.models.Booking;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.utils.RideConstants;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.CraftDemo.CarPoolApplication.utils.RideConstants.RIDE_FULL_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.RideConstants.RIDE_NOT_FOUND_EXCEPTION_MESSAGE;


public class BookingService {

    private final BookingRepository bookingRepository;
    private final RideRepository rideRepository;

    public BookingService() {
        bookingRepository = new BookingRepository();
        rideRepository = new RideRepository();
    }

    public List<Booking> getBookings(String userId){
        Map<String, List<Booking>> userIdBookingIdMapInstance = bookingRepository.getUserIdBookingIdMapInstance();
        return userIdBookingIdMapInstance.get(userId);
    }

    public boolean isRideAlreadyBookedByUser(String userId,String rideId){
        Map<String, List<Booking>> userIdBookingIdMapInstance = bookingRepository.getUserIdBookingIdMapInstance();
        List<Booking> bookings = userIdBookingIdMapInstance.get(userId);
        if(Objects.nonNull(bookings) && !bookings.isEmpty()){
            return bookings.stream().anyMatch(b -> Objects.equals(b.getRideId(),rideId));
        }
        return false;
    }

    private void rollBackRideBooking(Ride ride , Booking booking){
        try{
            if(Objects.nonNull(booking)){
                bookingRepository.removeBooking(booking);
            }
            if(Objects.nonNull(ride)){
                rideRepository.updateRide(ride);
            }
        }catch (Exception e){
            System.out.println("ALERT : Error while rolling back ride state " +  e.getMessage());
        }finally {
            System.out.println("Rollback attempt completed");
        }

    }

    public void bookRide(RideBookingRequest rideBookingRequest){
        ValidationUtils.ensureNotNull(rideBookingRequest , RideConstants.NULL_RIDE_VALIDATION_MESSAGE);
        Booking booking = null;
        Ride ride = null;
        try{
            Map<String, Ride> rideIdRideMapInstance = rideRepository.getRideIdRideMapInstance();
            ride = rideIdRideMapInstance.get(rideBookingRequest.getRideId());
            if(Objects.nonNull(ride)){
                synchronized (ride){
                    if(ride.getVacantSeats() >= rideBookingRequest.getRequestedSeats()){
                        booking = new Booking(rideBookingRequest.getUserId(),
                                rideBookingRequest.getRideId(),rideBookingRequest.getVehicleId(),new Date());
                        bookingRepository.book(booking);
                        Ride updatedRide = new Ride(ride);
                        updatedRide.setVacantSeats(ride.getVacantSeats() - rideBookingRequest.getRequestedSeats());
                        List<String> currentPassengerList = Objects.nonNull(ride.getPassengers()) ?
                                ride.getPassengers() : new ArrayList<>();
                        currentPassengerList.add(booking.getUserId());
                        updatedRide.setPassengers(currentPassengerList);
                        rideRepository.updateRide(updatedRide);
                        return;
                    }
                    throw new RideFullException(RIDE_FULL_EXCEPTION_MESSAGE);
                }
            }
            throw new RideNotFoundException(RIDE_NOT_FOUND_EXCEPTION_MESSAGE);
        }catch (Exception e){
            System.out.println(e.getClass());
            System.out.println("Executing booking rollback and freeing vacant spaces");
            rollBackRideBooking(ride,booking);
            throw e;
        }
    }

    public Map<String, List<Booking>> getAllBookings(){
        return bookingRepository.getUserIdBookingIdMapInstance();
    }
}
