package com.CraftDemo.CarPoolApplication.enums;

import java.util.Arrays;
import java.util.Objects;

public enum RideSelectionType {
    MOST_VACANT ("Most Vacant"),
    PREFERRED_VEHICLE("Preferred Vehicle");

    private final String label;

    RideSelectionType(String label) {
        this.label = label;
    }

    public static RideSelectionType getByLabel(String label){
        return Arrays.stream(RideSelectionType.values())
                .filter(rideSelectionType -> Objects.equals(rideSelectionType.label.toLowerCase(),label.toLowerCase()))
                .findFirst().orElse(null);
    }

}
