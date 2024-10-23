package com.CraftDemo.CarPoolApplication.factories;



import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;
import com.CraftDemo.CarPoolApplication.services.command.executors.*;

import java.util.Arrays;
import java.util.List;

public class CommandExecutorFactory {

    private final List<CommandExecutor> commandExecutorList;

    private static class CommandExecutorFactoryInitializer{
        private static final CommandExecutorFactory INSTANCE = new CommandExecutorFactory();
    }

    private CommandExecutorFactory() {
        commandExecutorList = Arrays.asList(
                new EndRideCommandExecutor(),
                new AddUserCommandExecutor(),
                new OfferRideCommandExecutor(),
                new DisplayRideStateCommandExecutor(),
                new BookRideCommandExecutor(),
                new AddVehicleCommandExecutor()
        );
    }

    public static CommandExecutorFactory getInstance(){
        return CommandExecutorFactoryInitializer.INSTANCE;
    }

    public List<CommandExecutor> getCommandExecutorList() {
        return commandExecutorList;
    }
}
