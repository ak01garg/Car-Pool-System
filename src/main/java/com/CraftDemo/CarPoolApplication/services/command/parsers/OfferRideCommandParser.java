package com.CraftDemo.CarPoolApplication.services.command.parsers;


import com.CraftDemo.CarPoolApplication.dto.request.RideOfferRequest;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.interfaces.CommandParser;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import static com.CraftDemo.CarPoolApplication.utils.CommandConstants.*;

public class OfferRideCommandParser implements CommandParser<RideOfferRequest> {

    private static final Integer expectedArguments = 6;
    @Override
    public RideOfferRequest parseCommand(String input) throws CommandParsingException {
        String[] rideDetails = extractArguments(input);
        ValidationUtils.ensureTrue(rideDetails.length >= expectedArguments ,
                new CommandParsingException(COMMAND_PARSING_EXCEPTION_MESSAGE));
        String userName = rideDetails[0].trim();
        String origin = rideDetails[1].split("=")[1].trim();
        Integer seats = Integer.parseInt(rideDetails[2].split("=")[1].trim());
        String vehicleName = rideDetails[3].split("=")[1].trim();
        String regNo = rideDetails[4].trim();
        String destination = rideDetails[5].split("=")[1].trim();

        ValidationUtils.ensureNotNullOrEmpty(userName , new CommandParsingException(USER_NAME_VALIDATION_FAILURE_MESSAGE));
        ValidationUtils.ensureNotNullOrEmpty(vehicleName , new CommandParsingException(VEHICLE_NAME_VALIDATION_FAILURE_MESSAGE));
        ValidationUtils.ensureNotNullOrEmpty(origin , new CommandParsingException(LOCATION_VALIDATION_FAILURE_MESSAGE));
        ValidationUtils.ensureNotNullOrEmpty(destination,new CommandParsingException(LOCATION_VALIDATION_FAILURE_MESSAGE));
        ValidationUtils.ensureNotNullOrEmpty(regNo , new CommandParsingException(VEHICLE_REG_VALIDATION_FAILURE_MESSAGE));

        return new RideOfferRequest(userName,vehicleName,regNo,origin,destination,seats);
    }
}
