package com.CraftDemo.CarPoolApplication.services.command.executors;

import com.CraftDemo.CarPoolApplication.dto.pojo.UserStatistics;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.enums.Gender;
import com.CraftDemo.CarPoolApplication.models.Booking;
import com.CraftDemo.CarPoolApplication.models.Location;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.services.BookingService;
import com.CraftDemo.CarPoolApplication.services.RideService;
import com.CraftDemo.CarPoolApplication.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.CraftDemo.CarPoolApplication.enums.RideStatus.EXPIRED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisplayRideStateCommandExecutorTests {

    @Mock
    private BookingService bookingService;
    @Mock
    private UserService userService;
    @Mock
    private RideService rideService;
    @InjectMocks
    private DisplayRideStateCommandExecutor executor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(executor, "bookingService", bookingService);
        ReflectionTestUtils.setField(executor, "userService", userService);
        ReflectionTestUtils.setField(executor, "rideService", rideService);
    }

    @Test
    void canExecuteWithValidCommand() {
        assertTrue(executor.canExecute("print_ride_stats"));
    }

    @Test
    void canExecuteWithInvalidCommand() {
        assertFalse(executor.canExecute("invalid_command"));
    }

    @Test
    void executeWithNoUsers() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        executor.execute("display_ride_stats");

        verify(userService, times(1)).getAllUsers();
        verify(bookingService, never()).getBookings(any());
        verify(rideService, never()).getRidesOfferedByUser(any());
    }

    @Test
    void executeWithUsersAndNoBookingsOrRides() {
        User user = new User("user1" , 10 , Gender.MALE);
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));
        when(bookingService.getBookings(user.getId())).thenReturn(Collections.emptyList());
        when(rideService.getRidesOfferedByUser(user.getId())).thenReturn(Collections.emptyList());

        executor.execute("display_ride_stats");

        verify(userService, times(1)).getAllUsers();
        verify(bookingService, times(1)).getBookings(user.getId());
        verify(rideService, times(1)).getRidesOfferedByUser(user.getId());
    }

    @Test
    void executeWithUsersAndBookingsAndRides() {
        User user = new User("user1" , 10 , Gender.MALE);
        Ride ride = new Ride(user.getId() , List.of("User2"), 2, "vehicle1" , new Location("CityA",null),
                new Location("CityB",null) , new Date() , null);
        Booking booking = new Booking(user.getId(),ride.getId(),"vehicle1",new Date());
        ride.setRideStatus(EXPIRED);
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));
        when(bookingService.getBookings(user.getId())).thenReturn(Collections.singletonList(booking));
        when(rideService.getRideById(booking.getRideId())).thenReturn(ride);
        when(rideService.getRidesOfferedByUser(user.getId())).thenReturn(Collections.singletonList(ride));

        executor.execute("display_ride_stats");

        verify(userService, times(1)).getAllUsers();
        verify(bookingService, times(1)).getBookings(user.getId());
        verify(rideService, times(1)).getRideById(booking.getRideId());
        verify(rideService, times(1)).getRidesOfferedByUser(user.getId());
    }

    @Test
    void executeWithMultipleUsers() {
        User user1 = new User("user1" , 10 , Gender.MALE);
        User user2 = new User("user2" , 20 , Gender.MALE);
        Ride ride1 = new Ride(user1.getId() , List.of("User4"), 2, "vehicle1" , new Location("CityA",null),
                new Location("CityB",null) , new Date() , null);
        Ride ride2 = new Ride(user2.getId() , List.of("User5"), 2, "vehicle2" , new Location("CityA",null),
                new Location("CityB",null) , new Date() , null);
        Booking booking1 = new Booking(user1.getId(),ride1.getId(),"vehicle1",new Date());
        Booking booking2 = new Booking(user2.getId(),ride2.getId(),"vehicle2",new Date());
        ride2.setRideStatus(EXPIRED);
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
        when(bookingService.getBookings(user1.getId())).thenReturn(Collections.singletonList(booking1));
        when(rideService.getRideById(booking1.getRideId())).thenReturn(ride1);
        when(rideService.getRidesOfferedByUser(user1.getId())).thenReturn(Collections.singletonList(ride1));
        when(bookingService.getBookings(user2.getId())).thenReturn(Collections.singletonList(booking2));
        when(rideService.getRideById(booking2.getRideId())).thenReturn(ride2);
        when(rideService.getRidesOfferedByUser(user2.getId())).thenReturn(Collections.singletonList(ride2));

        executor.execute("display_ride_stats");

        verify(userService, times(1)).getAllUsers();
        verify(bookingService, times(1)).getBookings(user1.getId());
        verify(rideService, times(1)).getRideById(booking1.getRideId());
        verify(rideService, times(1)).getRidesOfferedByUser(user1.getId());
        verify(bookingService, times(1)).getBookings(user2.getId());
        verify(rideService, times(1)).getRideById(booking2.getRideId());
        verify(rideService, times(1)).getRidesOfferedByUser(user2.getId());
    }
}