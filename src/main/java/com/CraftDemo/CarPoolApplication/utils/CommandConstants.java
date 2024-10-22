package com.CraftDemo.CarPoolApplication.utils;

public class CommandConstants {

    public static final String ADD_USER_PREFIX = "add_user";
    public static final String ADD_VEHICLE_PREFIX = "add_vehicle";
    public static final String OFFER_RIDE_PREFIX = "offer_ride";
    public static final String END_RIDE_PREFIX = "end_ride";
    public static final String SELECT_RIDE_PREFIX = "select_ride";
    public static final String DISPLAY_RIDE_STATS_PREFIX = "print_ride_stats";
    public static final String ARGUMENTS_START_DELIMITER = "(";
    public static final String ARGUMENTS_END_DELIMITER = ")";
    public static final String CLEAN_ARGUMENTS_REGEX   = "[\"“”]";
    public static final String SPLIT_ARGUMENTS_REGEX = ",";
    public static final String NUMERIC_REGEX = "^\\d+$";
    public static final String ALPHABET_REGEX = "^[a-zA-Z]+$";
    public static final String COMMAND_PARSING_EXCEPTION_MESSAGE = "Invalid arguments, Unable to parse command";
    public static final String USER_NAME_VALIDATION_FAILURE_MESSAGE = "User name cannot be null";
    public static final String GENDER_VALIDATION_FAILURE_MESSAGE = "Gender cannot be null";
    public static final String VEHICLE_NAME_VALIDATION_FAILURE_MESSAGE = "Vehicle name cannot be null";
    public static final String LOCATION_VALIDATION_FAILURE_MESSAGE  = "Location name cannot be null";
    public static final String VEHICLE_REG_VALIDATION_FAILURE_MESSAGE = "Reg No cannot be null";
    public static final String COMMAND_EMPTY_VALIDATION_MESSAGE = "Command cannot be null";
    public static final String NULL_SEATS_VALIDATION_MESSAGE  = "Requested seats can't be null";
    public static final String RIDE_SELECTION_STRATEGY_VALIDATION_MESSAGE = "Ride Selection Strategy cannot be null";
    public static final String INVALID_AGE_ARGUMENT = "Invalid Age Argument";
    public static final String INVALID_SEAT_ARGUMENT = "Invalid Seat Argument";
    public static final String INVALID_NAME_ARGUMENT = "Invalid Name Argument";



}
