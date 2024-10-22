package com.CraftDemo.CarPoolApplication.enums;


import static com.CraftDemo.CarPoolApplication.utils.CommandConstants.*;

public enum CommandConfig {

    ADD_USER(ADD_USER_PREFIX),
    ADD_VEHICLE(ADD_VEHICLE_PREFIX),
    SELECT_RIDE(SELECT_RIDE_PREFIX),
    OFFER_RIDE(OFFER_RIDE_PREFIX),
    END_RIDE(END_RIDE_PREFIX),
    DISPLAY_RIDE_STATS(DISPLAY_RIDE_STATS_PREFIX);

    private final String prefix;

    CommandConfig(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
