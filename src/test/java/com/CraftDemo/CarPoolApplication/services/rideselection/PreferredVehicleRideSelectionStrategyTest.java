package com.CraftDemo.CarPoolApplication.services.rideselection;

import com.CraftDemo.CarPoolApplication.Repositories.VehicleRepository;
import com.CraftDemo.CarPoolApplication.algorithms.RideFilterService;
import com.CraftDemo.CarPoolApplication.dto.pojo.RideFilter;
import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.enums.RideSelectionType;
import com.CraftDemo.CarPoolApplication.enums.VehicleType;
import com.CraftDemo.CarPoolApplication.models.Location;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.models.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreferredVehicleRideSelectionStrategyTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private RideFilterService rideFilterService;

    @InjectMocks
    private PreferredVehicleRideSelectionStrategy preferredVehicleRideSelectionStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(preferredVehicleRideSelectionStrategy, "rideFilterService", rideFilterService);
        ReflectionTestUtils.setField(preferredVehicleRideSelectionStrategy, "vehicleRepository", vehicleRepository);
    }

    @Test
    void selectRideSuccessfully() {
        RideSelectionRequest request = new RideSelectionRequest("user1", "source", "destination", RideSelectionType.PREFERRED_VEHICLE, "vehicle1", 2);
        Ride ride = new Ride("ride1", Arrays.asList("user2"), 3, "vehicle1", new Location("source", null), new Location("destination", null), new Date(), null);
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        when(rideFilterService.doFilter(any(RideFilter.class))).thenReturn(List.of(ride));
        when(vehicleRepository.getVehicleIdMapInstance()).thenReturn(Map.of("vehicle1", vehicle));

        Optional<Ride> result = preferredVehicleRideSelectionStrategy.selectRide(request);

        assertTrue(result.isPresent());
        assertEquals(ride.getId(), result.get().getId());
    }

    @Test
    void selectRideWhenNoRidesAvailable() {
        RideSelectionRequest request = new RideSelectionRequest("user1", "source", "destination", RideSelectionType.PREFERRED_VEHICLE, "vehicle1", 2);
        when(rideFilterService.doFilter(any(RideFilter.class))).thenReturn(Collections.emptyList());

        Optional<Ride> result = preferredVehicleRideSelectionStrategy.selectRide(request);

        assertFalse(result.isPresent());
    }

    @Test
    void selectRideWithNullRequest() {
        Optional<Ride> result = preferredVehicleRideSelectionStrategy.selectRide(null);
        assertFalse(result.isPresent());
    }

    @Test
    void selectRideWithInsufficientSeats() {
        RideSelectionRequest request = new RideSelectionRequest("user1", "source", "destination", RideSelectionType.PREFERRED_VEHICLE, "vehicle1", 5);
        Ride ride = new Ride("ride1", Arrays.asList("user2"), 3, "vehicle1", new Location("source", null), new Location("destination", null), new Date(), null);
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        when(rideFilterService.doFilter(any(RideFilter.class))).thenReturn(List.of(ride));
        when(vehicleRepository.getVehicleIdMapInstance()).thenReturn(Map.of("vehicle1", vehicle));

        Optional<Ride> result = preferredVehicleRideSelectionStrategy.selectRide(request);

        assertFalse(result.isPresent());
    }

    @Test
    void selectRideWithNonMatchingVehicle() {
        RideSelectionRequest request = new RideSelectionRequest("user1", "source", "destination", RideSelectionType.PREFERRED_VEHICLE, "vehicle2", 2);
        Ride ride = new Ride("ride1", Arrays.asList("user2"), 3, "vehicle1", new Location("source", null), new Location("destination", null), new Date(), null);
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        when(rideFilterService.doFilter(any(RideFilter.class))).thenReturn(List.of(ride));
        when(vehicleRepository.getVehicleIdMapInstance()).thenReturn(Map.of("vehicle1", vehicle));

        Optional<Ride> result = preferredVehicleRideSelectionStrategy.selectRide(request);

        assertFalse(result.isPresent());
    }
}