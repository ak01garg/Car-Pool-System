package com.CraftDemo.CarPoolApplication.services.command.executors;

import com.CraftDemo.CarPoolApplication.dto.request.AddUserRequest;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.enums.Gender;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.services.UserService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.AddUserCommandParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddUserCommandExecutorTests {

    @Mock
    private UserService userService;
    @Mock
    private AddUserCommandParser commandParser = new AddUserCommandParser();
    @InjectMocks
    private AddUserCommandExecutor executor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(executor, "userService", userService);
        ReflectionTestUtils.setField(executor, "commandParser", commandParser);

    }

    @Test
    void canExecuteWithValidCommand() {
        assertTrue(executor.canExecute("add_user John M 30"));
    }

    @Test
    void canExecuteWithInvalidCommand() {
        assertFalse(executor.canExecute("invalid_command John M 30"));
    }

    @Test
    void executeWithValidCommand() throws CommandParsingException {
        String command = "add_user John M 30";
        AddUserRequest request = new AddUserRequest("John", 30, Gender.MALE);
        when(commandParser.parseCommand(command)).thenReturn(request);

        executor.execute(command);

        verify(userService, times(1)).addUser(request);
    }

    @Test
    void executeWithInvalidCommandThrowsException() throws CommandParsingException {
        String command = "add_user John M";
        when(commandParser.parseCommand(command)).thenThrow(new CommandParsingException("Invalid command"));

        assertThrows(CommandParsingException.class, () -> executor.execute(command));
    }

}