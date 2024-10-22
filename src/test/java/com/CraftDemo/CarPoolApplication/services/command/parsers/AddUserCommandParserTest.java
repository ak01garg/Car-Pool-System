package com.CraftDemo.CarPoolApplication.services.command.parsers;

import com.CraftDemo.CarPoolApplication.dto.request.AddUserRequest;
import com.CraftDemo.CarPoolApplication.enums.Gender;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddUserCommandParserTests {

    private final AddUserCommandParser parser = new AddUserCommandParser();

    @Test
    void parseCommandSuccessfully() throws CommandParsingException {
        String input = "add_user(Rohan, M, 36)";
        AddUserRequest result = parser.parseCommand(input);
        assertNotNull(result);
        assertEquals("Rohan", result.getName());
        assertEquals(Gender.MALE, result.getGender());
        assertEquals(36, result.getAge());
    }

    @Test
    void parseCommandWithInsufficientArguments() {
        String input = "John M";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithInvalidGender() {
        String input = "John X 30";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithEmptyName() {
        String input = "  M 30";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }

    @Test
    void parseCommandWithNullInput() {
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(null));
    }

    @Test
    void parseCommandWithNonNumericAge() {
        String input = "John M thirty";
        assertThrows(CommandParsingException.class, () -> parser.parseCommand(input));
    }
}