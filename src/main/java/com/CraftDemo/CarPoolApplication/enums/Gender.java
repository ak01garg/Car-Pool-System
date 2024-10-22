package com.CraftDemo.CarPoolApplication.enums;

import java.util.Arrays;
import java.util.Objects;

public enum Gender {
    MALE("M") , FEMALE("F") , OTHER("O");
    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public static Gender getByLabel(String label){
        return Arrays.stream(Gender.values()).filter(gender -> Objects.equals(gender.label,label))
                .findFirst().orElse(null);
    }
}
