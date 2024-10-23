package com.CraftDemo.CarPoolApplication.services.command.executors;

import com.CraftDemo.CarPoolApplication.dto.request.RideOfferRequest;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.services.RideService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.OfferRideCommandParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfferRideCommandExecutorTest {

    @Mock
    private RideService rideService;
    @Mock
    private OfferRideCommandParser commandParser;
    @InjectMocks
    private OfferRideCommandExecutor executor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(executor, "rideService", rideService);
        ReflectionTestUtils.setField(executor, "commandParser", commandParser);
    }

    @Test
    void canExecuteWithValidCommand() {
        assertTrue(executor.canExecute("offer_ride Aakash CityA CityB 2"));
    }

    @Test
    void canExecuteWithInvalidCommand() {
        assertFalse(executor.canExecute("invalid_command Aakash CityA CityB 2"));
    }

    @Test
    void executeWithValidCommand() throws CommandParsingException {
        String command = "offer_ride Aakash CityA CityB 2";
        RideOfferRequest request = new RideOfferRequest("Aakash", "CityA", "REGNO", "CITYA" , "CITYB" , 2);
        when(commandParser.parseCommand(command)).thenReturn(request);

        executor.execute(command);

        verify(rideService, times(1)).offerRide(request);
    }

    @Test
    void executeWithInvalidCommandThrowsException() throws CommandParsingException {
        String command = "offer_ride Aakash CityA";
        when(commandParser.parseCommand(command)).thenThrow(new CommandParsingException("Invalid command"));

        assertThrows(CommandParsingException.class, () -> executor.execute(command));
    }


}