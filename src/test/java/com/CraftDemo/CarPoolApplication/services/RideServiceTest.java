package com.CraftDemo.CarPoolApplication.services;

import com.CraftDemo.CarPoolApplication.Repositories.RideRepository;
import com.CraftDemo.CarPoolApplication.dto.request.EndRideRequest;
import com.CraftDemo.CarPoolApplication.dto.request.RideBookingRequest;
import com.CraftDemo.CarPoolApplication.dto.request.RideOfferRequest;
import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.enums.Gender;
import com.CraftDemo.CarPoolApplication.enums.RideSelectionType;
import com.CraftDemo.CarPoolApplication.enums.VehicleType;
import com.CraftDemo.CarPoolApplication.exceptions.*;
import com.CraftDemo.CarPoolApplication.factories.RideSelectionStrategyFactory;
import com.CraftDemo.CarPoolApplication.interfaces.RideSelectionStrategy;
import com.CraftDemo.CarPoolApplication.models.Location;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.models.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RideServiceTest {

    @Mock
    private RideRepository rideRepository;

    @Mock
    private BookingService bookingService;

    @Mock
    private UserService userService;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private RideService rideService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(rideService, "bookingService", bookingService);
        ReflectionTestUtils.setField(rideService, "rideRepository", rideRepository);
        ReflectionTestUtils.setField(rideService, "userService", userService);
        ReflectionTestUtils.setField(rideService, "vehicleService", vehicleService);

    }

    @Test
    void offerRideSuccessfully() {
        RideOfferRequest request = new RideOfferRequest("user1", "vehicle1", "reg1", "source", "dest" , 3);
        User user = new User("user1", 30, Gender.MALE);
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        user.setVehicleList(Arrays.asList(vehicle));
        when(userService.getUserByName("user1")).thenReturn(user);
        when(vehicleService.getVehicleByRegNo("reg1")).thenReturn(vehicle);
        when(vehicleService.doesVehicleHaveActiveRides("reg1")).thenReturn(false);

        rideService.offerRide(request);

        verify(rideRepository, times(1)).addRide(any(Ride.class));
        verify(vehicleService, times(1)).createActiveRideForVehicle(anyString(), anyString());
    }

    @Test
    void offerRideWithInvalidOwner() {
        RideOfferRequest request = new RideOfferRequest("user1", "vehicle1", "reg1", "source", "dest" , 3);
        User user = new User("user1", 30, Gender.MALE);
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        when(userService.getUserByName("user1")).thenReturn(user);
        when(vehicleService.getVehicleByRegNo("reg1")).thenReturn(vehicle);
        when(vehicleService.doesVehicleHaveActiveRides("vehicle1")).thenReturn(false);

        assertThrows(InvalidOwnerException.class, () -> rideService.offerRide(request));

        verify(rideRepository, never()).addRide(any(Ride.class));
        verify(vehicleService, never()).createActiveRideForVehicle(anyString(), anyString());
    }

    @Test
    void selectRideSuccessfully() {
        RideSelectionRequest request = new RideSelectionRequest("user1", "source", "destination", RideSelectionType.MOST_VACANT, null , 3);
        RideSelectionStrategy strategy = mock(RideSelectionStrategy.class);
        Ride ride = new Ride("ride1", List.of("user2"), 3, "vehicle1", new Location("source", null), new Location("destination", null), new Date(), null);
        User user = new User("user1", 30, Gender.MALE);
        try(MockedStatic<RideSelectionStrategyFactory> mockedStatic = mockStatic(RideSelectionStrategyFactory.class)){
            mockedStatic.when(() -> RideSelectionStrategyFactory.getRideSelectionStratgeyForCommand(RideSelectionType.MOST_VACANT)).thenReturn(strategy);
            when(strategy.selectRide(request)).thenReturn(Collections.singletonList(ride));
            when(userService.getUserByName("user1")).thenReturn(user);
            when(bookingService.isRideAlreadyBookedByUser("user1", "ride1")).thenReturn(false);
            doNothing().when(bookingService).bookRide(any(RideBookingRequest.class));
            List<Ride> result = rideService.selectRide(request);
            assertTrue(!CollectionUtils.isEmpty(result));
            verify(bookingService, times(1)).bookRide(any(RideBookingRequest.class));
        }
    }

    @Test
    void selectRideWhenNoRideAvailable() {
        RideSelectionRequest request = new RideSelectionRequest("user1", "source", "destination", RideSelectionType.MOST_VACANT, null , 3);
        RideSelectionStrategy strategy = mock(RideSelectionStrategy.class);
        try(MockedStatic<RideSelectionStrategyFactory> mockedStatic = mockStatic(RideSelectionStrategyFactory.class)){
            mockedStatic.when(() -> RideSelectionStrategyFactory.getRideSelectionStratgeyForCommand(RideSelectionType.MOST_VACANT)).thenReturn(strategy);
            when(RideSelectionStrategyFactory.getRideSelectionStratgeyForCommand(RideSelectionType.MOST_VACANT)).thenReturn(strategy);
            when(strategy.selectRide(request)).thenReturn(Collections.emptyList());
            List<Ride> result = rideService.selectRide(request);
            assertTrue(CollectionUtils.isEmpty(result));
            verify(bookingService, never()).bookRide(any(RideBookingRequest.class));
        }
    }

    @Test
    void endRideSuccessfully() {
        EndRideRequest request = new EndRideRequest("user1", "vehicle1" ,
                "reg1" , "source", "dest",2);
        User user = new User("user1", 30, Gender.MALE);
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        Ride ride = new Ride("ride1", List.of("user1"), 3, vehicle.getId(), new Location("source", null), new Location("destination", null), new Date(), null);
        List<Ride> rides = List.of(ride);
        when(userService.getUserByName("user1")).thenReturn(user);
        when(vehicleService.getVehicleByRegNo("reg1")).thenReturn(vehicle);
        when(rideRepository.getUserIdRideMapInstance()).thenReturn(Map.of(user.getId(), rides));
        doNothing().when(rideRepository).endRide(ride);
        doNothing().when(vehicleService).endRideForVehicle("vehicle1", "ride1");
        rideService.endRide(request);
        verify(rideRepository, times(1)).endRide(ride);
        verify(vehicleService, times(1)).endRideForVehicle(vehicle.getId(), ride.getId());
    }

    @Test
    void endRideWhenRideNotFound() {
        EndRideRequest request = new EndRideRequest("user1", "vehicle1" ,
                "vehicleRegNo" , "source", "dest",2);
        User user = new User("user1", 30, Gender.MALE);
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        when(userService.getUserByName("user1")).thenReturn(user);
        when(vehicleService.getVehicleByRegNo("vehicle1")).thenReturn(vehicle);
        when(rideRepository.getUserIdRideMapInstance()).thenReturn(Map.of("user1", Collections.emptyList()));

        assertThrows(RideNotFoundException.class, () -> rideService.endRide(request));

        verify(rideRepository, never()).endRide(any(Ride.class));
        verify(vehicleService, never()).endRideForVehicle(anyString(), anyString());
    }
}