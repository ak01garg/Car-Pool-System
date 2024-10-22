package com.CraftDemo.CarPoolApplication.services.command.parsers;


import com.CraftDemo.CarPoolApplication.dto.request.AddVehicleRequest;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.interfaces.CommandParser;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import static com.CraftDemo.CarPoolApplication.utils.CommandConstants.*;
import static com.CraftDemo.CarPoolApplication.utils.ValidationUtils.*;
import static com.CraftDemo.CarPoolApplication.utils.ValidationUtils.ensureRegexMatch;

public class AddVehicleCommandParser implements CommandParser<AddVehicleRequest> {

    private static final Integer expectedArguments = 3;

    @Override
    public AddVehicleRequest parseCommand(String input) throws CommandParsingException {
        String[] vehicleDetails = extractArguments(input);
        ensureTrue(vehicleDetails.length >= expectedArguments ,
                new CommandParsingException(COMMAND_PARSING_EXCEPTION_MESSAGE));

        String userName = vehicleDetails[0].trim();
        String vehicleName = vehicleDetails[1].trim();
        String regNo = vehicleDetails[2].trim();

        ensureNotNullOrEmpty(userName , new CommandParsingException(USER_NAME_VALIDATION_FAILURE_MESSAGE));
        ensureNotNullOrEmpty(vehicleName , new CommandParsingException(VEHICLE_NAME_VALIDATION_FAILURE_MESSAGE));
        ensureNotNullOrEmpty(regNo , new CommandParsingException(VEHICLE_REG_VALIDATION_FAILURE_MESSAGE));
        ensureRegexMatch(userName , ALPHABET_REGEX , new CommandParsingException(INVALID_NAME_ARGUMENT));

        return new AddVehicleRequest(userName, regNo , vehicleName);
    }
}
