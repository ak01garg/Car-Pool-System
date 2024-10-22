package com.CraftDemo.CarPoolApplication.factories;

import com.CraftDemo.CarPoolApplication.interfaces.ApplicationMode;
import com.CraftDemo.CarPoolApplication.services.ConsoleMode;
import com.CraftDemo.CarPoolApplication.services.FileReaderMode;

public class ApplicationModeFactory {

    public static ApplicationMode getApplicationMode(String mode){
        if(mode == null){
            System.out.println("Mode not provided. Defaulting to console mode");
            return new ConsoleMode();
        }
        if(mode.equalsIgnoreCase("file")){
            return new FileReaderMode();
        }
        if(mode.equalsIgnoreCase("console")){
            return new ConsoleMode();
        }
        return null;
    }
}
