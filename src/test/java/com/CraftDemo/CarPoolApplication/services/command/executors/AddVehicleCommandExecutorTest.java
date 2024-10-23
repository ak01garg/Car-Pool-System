package com.CraftDemo.CarPoolApplication.services.command.executors;

import com.CraftDemo.CarPoolApplication.dto.request.AddVehicleRequest;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.services.VehicleService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.AddVehicleCommandParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddVehicleCommandExecutorTest {

    @Mock
    private VehicleService vehicleService;
    @Mock
    private AddVehicleCommandParser commandParser;
    @InjectMocks
    private AddVehicleCommandExecutor executor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(executor, "vehicleService", vehicleService);
        ReflectionTestUtils.setField(executor, "commandParser", commandParser);
    }

    @Test
    void canExecuteWithValidCommand() {
        assertTrue(executor.canExecute("add_vehicle(Aakash, Car, ABC123)"));
    }

    @Test
    void canExecuteWithInvalidCommand() {
        assertFalse(executor.canExecute("invalid_command(Aakash, Car, ABC123)"));
    }

    @Test
    void executeWithValidCommand() throws CommandParsingException {
        String command = "add_vehicle(Aakash, Car, ABC123)";
        AddVehicleRequest request = new AddVehicleRequest("Aakash", "Car", "ABC123");
        when(commandParser.parseCommand(command)).thenReturn(request);

        executor.execute(command);

        verify(vehicleService, times(1)).addVehicle(request);
    }

    @Test
    void executeWithInvalidCommandThrowsException() throws CommandParsingException {
        String command = "add_vehicle(Aakash, Car)";
        when(commandParser.parseCommand(command)).thenThrow(new CommandParsingException("Invalid command"));

        assertThrows(CommandParsingException.class, () -> executor.execute(command));
    }

}