package com.CraftDemo.CarPoolApplication.services.command.parsers;

import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.enums.RideSelectionType;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectRideCommandParserTest {

    private final SelectRideCommandParser parser = new SelectRideCommandParser();

    @Test
    void parseCommandSuccessfully() throws CommandParsingException {
        String input = "select_ride(Aakash, origin=CityA, destination=CityB, seats=2, Preferred Vehicle=Car)";
        RideSelectionRequest result = parser.parseCommand(input);
        assertNotNull(result);
        assertEquals("Aakash", result.getRequestByUserName());
        assertEquals("CityA", result.getSource());
        assertEquals("CityB", result.getDestination());
        assertEquals(2, result.getSeats());
        assertEquals(RideSelectionType.PREFERRED_VEHICLE, result.getRideSelectionType());
        assertEquals("Car", result.getPreferredVehicleName());
    }

    @Test
    void parseCommandWithInsufficientArguments() {
        String input = "select_ride(Aakash, origin=CityA, destination=CityB, seats=2)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyUserName() {
        String input = "select_ride( , origin=CityA, destination=CityB, seats=2, PREFERRED_VEHICLE=Car)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyOrigin() {
        String input = "select_ride(Aakash, origin= , destination=CityB, seats=2, PREFERRED_VEHICLE=Car)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyDestination() {
        String input = "select_ride(Aakash, origin=CityA, destination= , seats=2, PREFERRED_VEHICLE=Car)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyPreferredVehicleName() {
        String input = "select_ride(Aakash, origin=CityA, destination=CityB, seats=2, PREFERRED_VEHICLE= )";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithNullInput() {
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(null));
    }

    @Test
    void parseCommandWithNonNumericSeats() {
        String input = "select_ride(Aakash, origin=CityA, destination=CityB, seats=two, PREFERRED_VEHICLE=Car)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }
}