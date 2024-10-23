package com.CraftDemo.CarPoolApplication.services.command.parsers;

import com.CraftDemo.CarPoolApplication.dto.request.EndRideRequest;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndRideCommandParserTest {

    private final EndRideCommandParser parser = new EndRideCommandParser();

    @Test
    void parseCommandSuccessfully() throws CommandParsingException {
        String input = "end_ride(Aakash, origin=CityA, seats=2, vehicle=Car, ABC123, destination=CityB)";
        EndRideRequest result = parser.parseCommand(input);
        assertNotNull(result);
        assertEquals("Aakash", result.getUserName());
        assertEquals("Car", result.getVehicleName());
        assertEquals("ABC123", result.getVehicleRegNo());
        assertEquals("CityA", result.getSource());
        assertEquals("CityB", result.getDestination());
        assertEquals(2, result.getAvailableSeats());
    }

    @Test
    void parseCommandWithInsufficientArguments() {
        String input = "end_ride(Aakash, origin=CityA, seats=2, vehicle=Car, ABC123)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyUserName() {
        String input = "end_ride( , origin=CityA, seats=2, vehicle=Car, ABC123, destination=CityB)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyVehicleName() {
        String input = "end_ride(Aakash, origin=CityA, seats=2, vehicle= , ABC123, destination=CityB)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyOrigin() {
        String input = "end_ride(Aakash, origin= , seats=2, vehicle=Car, ABC123, destination=CityB)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyDestination() {
        String input = "end_ride(Aakash, origin=CityA, seats=2, vehicle=Car, ABC123, destination= )";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyRegNo() {
        String input = "end_ride(Aakash, origin=CityA, seats=2, vehicle=Car, , destination=CityB)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithNullInput() {
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(null));
    }

    @Test
    void parseCommandWithNonNumericSeats() {
        String input = "end_ride(Aakash, origin=CityA, seats=two, vehicle=Car, ABC123, destination=CityB)";
        assertThrows(NumberFormatException.class, () -> parser.parseCommand(input));
    }
}