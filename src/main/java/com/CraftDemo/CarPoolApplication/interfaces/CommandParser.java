package com.CraftDemo.CarPoolApplication.interfaces;


import com.CraftDemo.CarPoolApplication.exceptions.CommandParsingException;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;

import static com.CraftDemo.CarPoolApplication.utils.CommandConstants.*;

public interface CommandParser<T> {

     <T> T parseCommand(String input) throws CommandParsingException;

     default String[] extractArguments(String input) throws CommandParsingException{
          ValidationUtils.ensureNotNullOrEmpty(input ,
                  new CommandParsingException(COMMAND_EMPTY_VALIDATION_MESSAGE));
          try{
               return input.substring(input.indexOf(ARGUMENTS_START_DELIMITER) + 1,
                               input.indexOf(ARGUMENTS_END_DELIMITER)).replaceAll(CLEAN_ARGUMENTS_REGEX,"")
                       .split(SPLIT_ARGUMENTS_REGEX);
          }catch (Exception e) {
               throw new CommandParsingException(COMMAND_PARSING_EXCEPTION_MESSAGE);
          }
     }
}
