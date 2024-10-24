package com.CraftDemo.CarPoolApplication.services.command.parsers;

import com.CraftDemo.CarPoolApplication.dto.request.AddVehicleRequest;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddVehicleCommandParserTest {

    private final AddVehicleCommandParser parser = new AddVehicleCommandParser();

    @Test
    void parseCommandSuccessfully() throws CommandParsingException {
        String input = "add_vehicle(Aakash, Car, ABC123)";
        AddVehicleRequest result = parser.parseCommand(input);
        assertNotNull(result);
        assertEquals("Aakash", result.getUserName());
        assertEquals("Car", result.getVehicleName());
        assertEquals("ABC123", result.getRegNo());
    }

    @Test
    void parseCommandWithInsufficientArguments() {
        String input = "add_vehicle(Aakash, Car)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyUserName() {
        String input = "add_vehicle( , Car, ABC123)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyVehicleName() {
        String input = "add_vehicle(Aakash, , ABC123)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyRegNo() {
        String input = "add_vehicle(Aakash, Car, )";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithNullInput() {
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(null));
    }
}