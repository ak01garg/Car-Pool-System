package com.CraftDemo.CarPoolApplication.services.command.executors;

import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.enums.RideSelectionType;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.models.Location;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.services.RideService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.SelectRideCommandParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.CraftDemo.CarPoolApplication.enums.RideStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookRideCommandExecutorTests {

    @Mock
    private RideService rideService;
    @Mock
    private SelectRideCommandParser commandParser;
    @InjectMocks
    private BookRideCommandExecutor executor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(executor, "rideService", rideService);
        ReflectionTestUtils.setField(executor, "commandParser", commandParser);
    }

    @Test
    void canExecuteWithValidCommand() {
        assertTrue(executor.canExecute("select_ride(John, origin=CityA, destination=CityB, seats=2, PREFERRED_VEHICLE=Car)"));
    }

    @Test
    void canExecuteWithInvalidCommand() {
        assertFalse(executor.canExecute("invalid_command(John, origin=CityA, destination=CityB, seats=2, PREFERRED_VEHICLE=Car)"));
    }

    @Test
    void executeWithValidCommand() throws CommandParsingException {
        String command = "select_ride(John, origin=CityA, destination=CityB, seats=2, PREFERRED_VEHICLE=Car)";
        RideSelectionRequest request = new RideSelectionRequest("John", "CityA", "CityB", RideSelectionType.MOST_VACANT, null,1);
        Ride ride = new Ride("user1" , List.of("User2"), 2, "vehicle1" , new Location("CityA",null),
                new Location("CityB",null) , new Date() , null);
        when(commandParser.parseCommand(command)).thenReturn(request);
        when(rideService.selectRide(request)).thenReturn(Collections.singletonList(ride));

        executor.execute(command);

        verify(rideService, times(1)).selectRide(request);
    }

    @Test
    void executeWithNoMatchingRide() throws CommandParsingException {
        String command = "select_ride(John, origin=CityA, destination=CityB, seats=2, PREFERRED_VEHICLE=Car)";
        RideSelectionRequest request = new RideSelectionRequest("John", "CityA", "CityB", RideSelectionType.MOST_VACANT, null,1);
        when(commandParser.parseCommand(command)).thenReturn(request);
        when(rideService.selectRide(request)).thenReturn(Collections.emptyList());

        executor.execute(command);

        verify(rideService, times(1)).selectRide(request);
    }

    @Test
    void executeWithInvalidCommandThrowsException() throws CommandParsingException {
        String command = "select_ride(John, origin=CityA, destination=CityB, seats=2)";
        when(commandParser.parseCommand(command)).thenThrow(new CommandParsingException("Invalid command"));

        assertThrows(CommandParsingException.class, () -> executor.execute(command));
    }

}