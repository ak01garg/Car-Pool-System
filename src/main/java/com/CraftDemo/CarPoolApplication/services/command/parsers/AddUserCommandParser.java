package com.CraftDemo.CarPoolApplication.services.command.parsers;


import com.CraftDemo.CarPoolApplication.dto.request.AddUserRequest;
import com.CraftDemo.CarPoolApplication.enums.Gender;
import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.interfaces.CommandParser;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import static com.CraftDemo.CarPoolApplication.utils.CommandConstants.*;
import static com.CraftDemo.CarPoolApplication.utils.ValidationUtils.*;

public class AddUserCommandParser implements CommandParser<AddUserRequest> {

    private static final Integer expectedArguments = 3;

    @Override
    public AddUserRequest parseCommand(String input) throws CommandParsingException {
        String[] userDetails = extractArguments(input);
        ensureTrue(userDetails.length >= expectedArguments ,
                new CommandParsingException(COMMAND_PARSING_EXCEPTION_MESSAGE));

        String name = userDetails[0].trim();
        Gender gender = Gender.getByLabel(userDetails[1].trim());
        Integer age = Integer.parseInt(userDetails[2].trim());

        ensureNotNullOrEmpty(name , new CommandParsingException(USER_NAME_VALIDATION_FAILURE_MESSAGE));
        ensureNotNull(gender , new CommandParsingException(GENDER_VALIDATION_FAILURE_MESSAGE));
        ensureNotNull(age, new CommandParsingException(INVALID_AGE_ARGUMENT));
        ensureRegexMatch(age.toString(),NUMERIC_REGEX,new CommandParsingException(INVALID_AGE_ARGUMENT));
        ensureRegexMatch(name , ALPHABET_REGEX , new CommandParsingException(INVALID_NAME_ARGUMENT));

        return new AddUserRequest(name, age, gender);

    }
}
