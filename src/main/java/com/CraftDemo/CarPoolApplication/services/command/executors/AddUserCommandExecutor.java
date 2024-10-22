package com.CraftDemo.CarPoolApplication.services.command.executors;

import com.CraftDemo.CarPoolApplication.dto.request.AddUserRequest;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;
import com.CraftDemo.CarPoolApplication.interfaces.CommandParser;
import com.CraftDemo.CarPoolApplication.services.UserService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.AddUserCommandParser;


import java.util.Objects;

public class AddUserCommandExecutor implements CommandExecutor {

    private final UserService userService;
    private final CommandParser<AddUserRequest> commandParser;

    public AddUserCommandExecutor() {
        commandParser = new AddUserCommandParser();
        userService = new UserService();
    }

    @Override
    public boolean canExecute(String command) {
        return Objects.nonNull(command) && command.toLowerCase().startsWith(CommandConfig.ADD_USER.getPrefix());
    }

    @Override
    public void execute(String command) {
        AddUserRequest addUserRequest =  commandParser.parseCommand(command);
        userService.addUser(addUserRequest);
        System.out.println("User Successfully Created with details " + addUserRequest.toString());
    }
}
