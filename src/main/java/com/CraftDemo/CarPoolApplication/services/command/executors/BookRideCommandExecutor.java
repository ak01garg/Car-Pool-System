package com.CraftDemo.CarPoolApplication.services.command.executors;


import com.CraftDemo.CarPoolApplication.dto.BookRideResponse;
import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;
import com.CraftDemo.CarPoolApplication.interfaces.CommandParser;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.models.Vehicle;
import com.CraftDemo.CarPoolApplication.services.RideService;
import com.CraftDemo.CarPoolApplication.services.UserService;
import com.CraftDemo.CarPoolApplication.services.VehicleService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.SelectRideCommandParser;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BookRideCommandExecutor implements CommandExecutor {

    private final RideService rideService;
    private final CommandParser<RideSelectionRequest> commandParser;
    private final UserService userService;
    private final VehicleService vehicleService;

    public BookRideCommandExecutor() {
        this.commandParser = new SelectRideCommandParser();
        this.rideService = new RideService();
        this.userService = new UserService();
        this.vehicleService = new VehicleService();
    }

    @Override
    public boolean canExecute(String command) {
        return Objects.nonNull(command) && command.toLowerCase().startsWith(CommandConfig.SELECT_RIDE.getPrefix());
    }
    @Override
    public void execute(String command) {
        RideSelectionRequest rideSelectionRequest = commandParser.parseCommand(command);
        List<Ride> rides = rideService.selectRide(rideSelectionRequest);
        if(CollectionUtils.isEmpty(rides)){
            System.out.println("Could not find the suitable ride for request " + rideSelectionRequest.toString());
            return;
        }
        System.out.println("Rides successfully selected");
        rides.forEach(ride -> {
            Ride rideById = rideService.getRideById(ride.getId());
            User userById = userService.getUserById(rideById.getDriverId());
            Vehicle vehicle = vehicleService.getById(rideById.getVehicleUsed());
            BookRideResponse rideResponse = new BookRideResponse(userById.getName(),rideById.getSource().getName(),
                    rideById.getDestination().getName(), vehicle.getName(), rideById.getVacantSeats());
            System.out.println(rideResponse.toString());
        });
    }
}
