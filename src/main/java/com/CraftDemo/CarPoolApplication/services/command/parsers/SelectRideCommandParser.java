package com.CraftDemo.CarPoolApplication.services.command.parsers;


import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.enums.RideSelectionType;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.interfaces.CommandParser;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import java.util.Objects;

import static com.CraftDemo.CarPoolApplication.enums.RideSelectionType.PREFERRED_VEHICLE;
import static com.CraftDemo.CarPoolApplication.utils.CommandConstants.*;


public class SelectRideCommandParser implements CommandParser<RideSelectionRequest> {

    private static final Integer expectedArguments = 5;
    @Override
    public RideSelectionRequest parseCommand(String input) throws CommandParsingException {
        String[] rideDetails = extractArguments(input);
        ValidationUtils.ensureTrue(rideDetails.length >= expectedArguments ,
                new CommandParsingException(COMMAND_PARSING_EXCEPTION_MESSAGE));
        String userName = rideDetails[0].trim();
        String origin = rideDetails[1].split("=")[1].trim();
        String destination = rideDetails[2].split("=")[1].trim();
        String seats = rideDetails[3].split("=")[1].trim();
        RideSelectionType rideSelectionType = RideSelectionType.getByLabel(rideDetails[4].split("=")[0].trim());
        String preferredVehicleName = rideDetails[4].split("=").length>=2 ? rideDetails[4].split("=")[1].trim() : null;

        ValidationUtils.ensureNotNullOrEmpty(userName , new CommandParsingException(USER_NAME_VALIDATION_FAILURE_MESSAGE));
        ValidationUtils.ensureNotNullOrEmpty(origin , new CommandParsingException(LOCATION_VALIDATION_FAILURE_MESSAGE));
        ValidationUtils.ensureNotNullOrEmpty(destination,new CommandParsingException(LOCATION_VALIDATION_FAILURE_MESSAGE));
        ValidationUtils.ensureNotNull(seats, new CommandParsingException(NULL_SEATS_VALIDATION_MESSAGE));
        ValidationUtils.ensureRegexMatch(seats,NUMERIC_REGEX,new CommandParsingException(INVALID_SEAT_ARGUMENT));
        ValidationUtils.ensureNotNull(rideSelectionType, new CommandParsingException(RIDE_SELECTION_STRATEGY_VALIDATION_MESSAGE));

        if(Objects.equals(rideSelectionType,PREFERRED_VEHICLE)){
            ValidationUtils.ensureTrue(Objects.nonNull(preferredVehicleName) && !preferredVehicleName.isEmpty(),
                    new CommandParsingException(VEHICLE_NAME_VALIDATION_FAILURE_MESSAGE));
        }

        return new RideSelectionRequest(userName,origin,destination,rideSelectionType,preferredVehicleName,Integer.parseInt(seats));
    }
}
