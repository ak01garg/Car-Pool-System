package com.CraftDemo.CarPoolApplication.services;

import com.CraftDemo.CarPoolApplication.Repositories.BookingRepository;
import com.CraftDemo.CarPoolApplication.Repositories.RideRepository;
import com.CraftDemo.CarPoolApplication.dto.request.RideBookingRequest;
import com.CraftDemo.CarPoolApplication.exceptions.RideFullException;
import com.CraftDemo.CarPoolApplication.models.Booking;
import com.CraftDemo.CarPoolApplication.models.Location;
import com.CraftDemo.CarPoolApplication.models.Ride;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(bookingService, "bookingRepository", bookingRepository);
        ReflectionTestUtils.setField(bookingService, "rideRepository", rideRepository);
    }

    @Test
    void bookRideSuccessfully() {
        RideBookingRequest request = new RideBookingRequest("user1", "ride1", "vehicle1", 2);
        Ride ride = new Ride("ride1",new ArrayList<>(), 3, "vehicle1" , new Location("source" , null) , new Location("destination" , null) , new Date() , null);
        when(rideRepository.getRideIdRideMapInstance()).thenReturn(Map.of("ride1", ride));
        doNothing().when(bookingRepository).book(any(Booking.class));
        doNothing().when(rideRepository).updateRide(any(Ride.class));

        bookingService.bookRide(request);

        verify(bookingRepository, times(1)).book(any(Booking.class));
        verify(rideRepository, times(1)).updateRide(any(Ride.class));
    }

    @Test
    void bookRideWhenRideIsFull() {
        RideBookingRequest request = new RideBookingRequest("user1", "ride1", "vehicle1", 4);
        Ride ride = new Ride("ride1",new ArrayList<>(), 3,
                "vehicle1" , new Location("source" , null) ,
                new Location("destination" , null) , new Date() , null);
        when(rideRepository.getRideIdRideMapInstance()).thenReturn(Map.of("ride1", ride));

        assertThrows(RideFullException.class, () -> bookingService.bookRide(request));

        verify(bookingRepository, never()).book(any(Booking.class));
        verify(rideRepository, times(0)).updateRide(any(Ride.class));
    }

    @Test
    void getBookingsForUser() {
        List<Booking> bookings = List.of(new Booking("user1", "ride1", "vehicle1", new Date()));
        when(bookingRepository.getUserIdBookingIdMapInstance()).thenReturn(Map.of("user1", bookings));

        List<Booking> result = bookingService.getBookings("user1");

        assertEquals(1, result.size());
        assertEquals("ride1", result.get(0).getRideId());
    }

    @Test
    void isRideAlreadyBookedByUser() {
        List<Booking> bookings = List.of(new Booking("user1", "ride1", "vehicle1", new Date()));
        when(bookingRepository.getUserIdBookingIdMapInstance()).thenReturn(Map.of("user1", bookings));
        boolean result = bookingService.isRideAlreadyBookedByUser("user1", "ride1");
        assertTrue(result);
    }

    @Test
    void isRideNotBookedByUser() {
        List<Booking> bookings = List.of(new Booking("user1", "ride2", "vehicle1", new Date()));
        when(bookingRepository.getUserIdBookingIdMapInstance()).thenReturn(Map.of("user1", bookings));
        boolean result = bookingService.isRideAlreadyBookedByUser("user1", "ride1");
        assertFalse(result);
    }

    @Test
    void getAllBookings() {
        Map<String, List<Booking>> allBookings = Map.of("user1", List.of(new Booking("user1", "ride1", "vehicle1", new Date())));
        when(bookingRepository.getUserIdBookingIdMapInstance()).thenReturn(allBookings);
        Map<String, List<Booking>> result = bookingService.getAllBookings();
        assertEquals(1, result.size());
        assertEquals(1, result.get("user1").size());
    }
}