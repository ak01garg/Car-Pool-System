package com.CraftDemo.CarPoolApplication.services.command.executors;

import com.CraftDemo.CarPoolApplication.dto.request.EndRideRequest;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.services.RideService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.EndRideCommandParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EndRideCommandExecutorTests {

    @Mock
    private RideService rideService;
    @Mock
    private EndRideCommandParser commandParser;
    @InjectMocks
    private EndRideCommandExecutor executor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(executor, "rideService", rideService);
        ReflectionTestUtils.setField(executor, "commandParser", commandParser);
    }

    @Test
    void canExecuteWithValidCommand() {
        assertTrue(executor.canExecute("end_ride 123"));
    }

    @Test
    void canExecuteWithInvalidCommand() {
        assertFalse(executor.canExecute("invalid_command 123"));
    }

    @Test
    void executeWithValidCommand() throws CommandParsingException {
        String command = "end_ride(“Rohan, Origin=Hyderabad, Available Seats=1, Vehicle=Swift, KA-01-12345, Destination= Bangalore”)";
        EndRideRequest request = new EndRideRequest("Rohan", "Swift" ,
                "KA-01-12345" , "Hyderabad", "Bangalore",1);
        when(commandParser.parseCommand(command)).thenReturn(request);

        executor.execute(command);

        verify(rideService, times(1)).endRide(request);
    }

    @Test
    void executeWithInvalidCommandThrowsException() throws CommandParsingException {
        String command = "end_ride";
        when(commandParser.parseCommand(command)).thenThrow(new CommandParsingException("Invalid command"));

        assertThrows(CommandParsingException.class, () -> executor.execute(command));
    }


}