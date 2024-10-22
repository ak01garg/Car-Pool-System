package com.CraftDemo.CarPoolApplication.Repositories;

import com.CraftDemo.CarPoolApplication.exceptions.BookingAlreadyExistException;
import com.CraftDemo.CarPoolApplication.models.Booking;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.CraftDemo.CarPoolApplication.utils.BookingConstants.DUPLICATE_BOOKING_EXCEPTION_MESSAGE;
import static com.CraftDemo.CarPoolApplication.utils.BookingConstants.NULL_BOOKING_VALIDATION_MESSAGE;


public class BookingRepository extends BaseRepository{

    public void book(Booking booking){
        ValidationUtils.ensureNotNull(booking , NULL_BOOKING_VALIDATION_MESSAGE);
        Map<String, Booking> bookingIdMapInstance = getBookingIdMapInstance();
        Map<String, List<Booking>> userIdBookingIdMapInstance = getUserIdBookingIdMapInstance();
        List<Booking> bookingList = userIdBookingIdMapInstance.get(booking.getUserId());
        if(Objects.isNull(bookingList) || bookingList.isEmpty() ||
                bookingList.stream().noneMatch(b -> b.equals(booking))){

            bookingIdMapInstance.put(booking.getId() , booking);
            userIdBookingIdMapInstance.computeIfAbsent(booking.getUserId(),  (k-> new ArrayList<>())).add(booking);
            return;
        }

        throw new BookingAlreadyExistException(DUPLICATE_BOOKING_EXCEPTION_MESSAGE);
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
