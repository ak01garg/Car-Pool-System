package com.CraftDemo.CarPoolApplication.services.rideselection;

import com.CraftDemo.CarPoolApplication.algorithms.RideFilterService;
import com.CraftDemo.CarPoolApplication.dto.pojo.RideFilter;
import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.enums.RideSelectionType;
import com.CraftDemo.CarPoolApplication.models.Location;
import com.CraftDemo.CarPoolApplication.models.Ride;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MostVacantRideSelectionStrategyTests {

    @Mock
    private RideFilterService rideFilterService;

    @InjectMocks
    private MostVacantRideSelectionStrategy mostVacantRideSelectionStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(mostVacantRideSelectionStrategy, "rideFilterService", rideFilterService);
    }

    @Test
    void selectRideSuccessfully() {
        RideSelectionRequest request = new RideSelectionRequest("user1" , "source", "destination", RideSelectionType.MOST_VACANT,null , 2);
        Ride ride = new Ride("ride1", Arrays.asList("user2"), 3, "vehicle1",
                new Location("source", null), new Location("destination", null), new Date(), null);
        when(rideFilterService.doFilter(any(RideFilter.class))).thenReturn(List.of(ride));

        List<Ride> result = mostVacantRideSelectionStrategy.selectRide(request);

        assertTrue(!CollectionUtils.isEmpty(result));
        assertEquals(ride.getId(), result.get(0).getId());
    }

    @Test
    void selectRideWhenNoRidesAvailable() {
        RideSelectionRequest request = new RideSelectionRequest("user1" , "source", "destination", RideSelectionType.MOST_VACANT,null , 2);
        when(rideFilterService.doFilter(any(RideFilter.class))).thenReturn(Collections.emptyList());

        List<Ride> result = mostVacantRideSelectionStrategy.selectRide(request);

        assertTrue(CollectionUtils.isEmpty(result));
    }

    @Test
    void selectRideWithNullRequest() {
        List<Ride> result = mostVacantRideSelectionStrategy.selectRide(null);
        assertTrue(CollectionUtils.isEmpty(result));
    }

    @Test
    void selectRideWithInsufficientSeats() {
        RideSelectionRequest request = new RideSelectionRequest("user1" , "source", "destination", RideSelectionType.MOST_VACANT,null , 10);
        Ride ride = new Ride("ride1", Arrays.asList("user2"), 3, "vehicle1", new Location("source", null), new Location("destination", null), new Date(), null);
        when(rideFilterService.doFilter(any(RideFilter.class))).thenReturn(List.of(ride));
        List<Ride> result = mostVacantRideSelectionStrategy.selectRide(request);
        assertTrue(CollectionUtils.isEmpty(result));
    }
}