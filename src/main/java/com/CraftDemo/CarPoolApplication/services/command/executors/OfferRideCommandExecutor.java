package com.CraftDemo.CarPoolApplication.services.command.executors;


import com.CraftDemo.CarPoolApplication.dto.request.RideOfferRequest;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;
import com.CraftDemo.CarPoolApplication.interfaces.CommandParser;
import com.CraftDemo.CarPoolApplication.services.RideService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.OfferRideCommandParser;


import java.util.Objects;


public class OfferRideCommandExecutor implements CommandExecutor {

    private final RideService rideService;
    private final CommandParser<RideOfferRequest> commandParser;

    public OfferRideCommandExecutor() {
        this.rideService = new RideService();
        this.commandParser = new OfferRideCommandParser();
    }

    @Override
    public boolean canExecute(String command) {
        return Objects.nonNull(command) && command.toLowerCase().startsWith(CommandConfig.OFFER_RIDE.getPrefix());
    }


    @Override
    public void execute(String command) {
        RideOfferRequest rideOfferRequest = commandParser.parseCommand(command);
        rideService.offerRide(rideOfferRequest);
        System.out.println("Ride successfully created for request" + rideOfferRequest.toString());
    }
}
