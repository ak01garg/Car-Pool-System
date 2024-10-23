package com.CraftDemo.CarPoolApplication.utils;

public class GraphUtils {

    public static String generateCompositeLocationKey(String source, String destination) {
        return source + "-" + destination;
    }
}
