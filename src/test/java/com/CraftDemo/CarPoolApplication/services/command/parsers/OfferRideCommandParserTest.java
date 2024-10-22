package com.CraftDemo.CarPoolApplication.services.command.parsers;

import com.CraftDemo.CarPoolApplication.dto.request.RideOfferRequest;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfferRideCommandParserTests {

    private final OfferRideCommandParser parser = new OfferRideCommandParser();

    @Test
    void parseCommandSuccessfully() throws CommandParsingException {
        String input = "offer_ride(John, origin=CityA, seats=2, vehicle=Car, ABC123, destination=CityB)";
        RideOfferRequest result = parser.parseCommand(input);
        assertNotNull(result);
        assertEquals("John", result.getUserName());
        assertEquals("Car", result.getVehicleName());
        assertEquals("ABC123", result.getVehicleRegNo());
        assertEquals("CityA", result.getSource());
        assertEquals("CityB", result.getDestination());
        assertEquals(2, result.getAvailableSeats());
    }

    @Test
    void parseCommandWithInsufficientArguments() {
        String input = "offer_ride(John, origin=CityA, seats=2, vehicle=Car, ABC123)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyUserName() {
        String input = "offer_ride( , origin=CityA, seats=2, vehicle=Car, ABC123, destination=CityB)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyVehicleName() {
        String input = "offer_ride(John, origin=CityA, seats=2, vehicle= , ABC123, destination=CityB)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyOrigin() {
        String input = "offer_ride(John, origin= , seats=2, vehicle=Car, ABC123, destination=CityB)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyDestination() {
        String input = "offer_ride(John, origin=CityA, seats=2, vehicle=Car, ABC123, destination= )";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyRegNo() {
        String input = "offer_ride(John, origin=CityA, seats=2, vehicle=Car, , destination=CityB)";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithNullInput() {
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(null));
    }

    @Test
    void parseCommandWithNonNumericSeats() {
        String input = "offer_ride(John, origin=CityA, seats=two, vehicle=Car, ABC123, destination=CityB)";
        assertThrows(NumberFormatException.class, () -> parser.parseCommand(input));
    }
}