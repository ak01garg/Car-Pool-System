package com.CraftDemo.CarPoolApplication.factories;



import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;
import com.CraftDemo.CarPoolApplication.services.command.executors.*;

import java.util.Arrays;
import java.util.List;

public class CommandExecutorFactory {

    private final List<CommandExecutor> commandExecutorList;

    public CommandExecutorFactory() {
        commandExecutorList = Arrays.asList(
                new EndRideCommandExecutor(),
                new AddUserCommandExecutor(),
                new OfferRideCommandExecutor(),
                new DisplayRideStateCommandExecutor(),
                new BookRideCommandExecutor(),
                new AddVehicleCommandExecutor()
        );
    }

    public List<CommandExecutor> getCommandExecutorList() {
        return commandExecutorList;
    }
}
