package com.CraftDemo.CarPoolApplication.Repositories;

import com.CraftDemo.CarPoolApplication.exceptions.BookingAlreadyExistException;
import com.CraftDemo.CarPoolApplication.models.Booking;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import java.util.*;

import static com.CraftDemo.CarPoolApplication.utils.BookingConstants.DUPLICATE_BOOKING_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.BookingConstants.NULL_BOOKING_VALIDATION_MESSAGE;


public class BookingRepository extends BaseRepository{

    private static class BookingRepositoryInitializer{
        private static final Map<String, Booking> bookingIdMap = new HashMap<>();
        private static final Map<String, List<Booking>> userIdBookingIdMap = new HashMap<>();
    }

    public Map<String, Booking> getBookingIdMapInstance(){
        return BookingRepositoryInitializer.bookingIdMap;
    }

    public Map<String, List<Booking>> getUserIdBookingIdMapInstance(){
        return BookingRepositoryInitializer.userIdBookingIdMap;
    }

    public void book(Booking booking){
        ValidationUtils.ensureNotNull(booking , NULL_BOOKING_VALIDATION_MESSAGE);
        Map<String, Booking> bookingIdMapInstance = getBookingIdMapInstance();
        Map<String, List<Booking>> userIdBookingIdMapInstance = getUserIdBookingIdMapInstance();
        bookingIdMapInstance.put(booking.getId() , booking);
        userIdBookingIdMapInstance.computeIfAbsent(booking.getUserId(),
                (k-> new ArrayList<>())).add(booking);
    }

    public void removeBooking(Booking booking){
        if(Objects.nonNull(booking)){
            Map<String, Booking> bookingIdMapInstance = getBookingIdMapInstance();
            Map<String, List<Booking>> userIdBookingIdMapInstance = getUserIdBookingIdMapInstance();
            List<Booking> bookingList = userIdBookingIdMapInstance.get(booking.getUserId());
            if(Objects.nonNull(bookingList) && !bookingList.isEmpty()){
                List<Booking> bookingsToBeRemoved = bookingList.stream()
                        .filter(b -> Objects.equals(b.getId() , booking.getId()))
                        .toList();
                bookingList.removeAll(bookingsToBeRemoved);
                bookingIdMapInstance.remove(booking.getId());
            }
        }
    }

}
