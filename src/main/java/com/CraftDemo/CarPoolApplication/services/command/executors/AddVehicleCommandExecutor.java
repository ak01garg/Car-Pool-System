package com.CraftDemo.CarPoolApplication.services.command.executors;



import com.CraftDemo.CarPoolApplication.dto.request.AddVehicleRequest;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;
import com.CraftDemo.CarPoolApplication.interfaces.CommandParser;
import com.CraftDemo.CarPoolApplication.services.VehicleService;
import com.CraftDemo.CarPoolApplication.services.command.parsers.AddVehicleCommandParser;

import java.util.Objects;

public class AddVehicleCommandExecutor implements CommandExecutor {

    private final CommandParser<AddVehicleRequest> commandParser;
    private final VehicleService vehicleService;

    public AddVehicleCommandExecutor() {
        vehicleService = new VehicleService();
        commandParser = new AddVehicleCommandParser();
    }

    @Override
    public boolean canExecute(String command) {
        return Objects.nonNull(command) && command.toLowerCase().startsWith(CommandConfig.ADD_VEHICLE.getPrefix());
    }


    @Override
    public void execute(String command) {
        AddVehicleRequest addVehicleRequest = commandParser.parseCommand(command);
        vehicleService.addVehicle(addVehicleRequest);
        System.out.println("Vehicle added successfully with details " + addVehicleRequest.toString());
    }
}
