package com.CraftDemo.CarPoolApplication.services.command.executors;


import com.CraftDemo.CarPoolApplication.dto.request.EndRideRequest;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;
import com.CraftDemo.CarPoolApplication.interfaces.CommandParser;
import com.CraftDemo.CarPoolApplication.services.RideService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.EndRideCommandParser;


import java.util.Objects;


public class EndRideCommandExecutor implements CommandExecutor {

    private final RideService rideService;
    private final CommandParser<EndRideRequest> commandParser;

    public EndRideCommandExecutor() {
        this.rideService = new RideService();
        this.commandParser = new EndRideCommandParser();
    }

    @Override
    public boolean canExecute(String command) {
        return Objects.nonNull(command) && command.toLowerCase().startsWith(CommandConfig.END_RIDE.getPrefix());
    }


    @Override
    public void execute(String command) {
        EndRideRequest endRideRequest = commandParser.parseCommand(command);
        rideService.endRide(endRideRequest);
        System.out.println("Ride successfully ended");
    }
}
