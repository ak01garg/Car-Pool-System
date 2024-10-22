package com.CraftDemo.CarPoolApplication.utils;

import com.CraftDemo.CarPoolApplication.exceptions.ValidationException;

import java.util.Objects;

public class ValidationUtils {

    public static void ensureNotNull(Object object , String message){
        if(Objects.isNull(object)) throw new ValidationException(message);
    }

    public static void ensureNotNull(Object object , RuntimeException e){
        if(Objects.isNull(object)) throw e;
    }

    public static void ensureNotNullOrEmpty(String string , RuntimeException e){
        if(Objects.isNull(string) || string.isEmpty()) throw e;
    }

    public static void ensureTrue(boolean condition, RuntimeException e){
        if(!condition) throw e;
    }
    public static void ensureRegexMatch(String data , String regex , RuntimeException e){
        if(!data.matches(regex)) throw e;
    }
}
