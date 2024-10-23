package com.CraftDemo.CarPoolApplication.factories;

import ch.qos.logback.core.util.StringUtil;
import com.CraftDemo.CarPoolApplication.interfaces.ApplicationMode;
import com.CraftDemo.CarPoolApplication.services.ConsoleMode;
import com.CraftDemo.CarPoolApplication.services.FileReaderMode;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static com.CraftDemo.CarPoolApplication.utils.Constants.CONSOLE_APPLICATION_MODE;
import static com.CraftDemo.CarPoolApplication.utils.Constants.FILE_APPLICATION_MODE;

public class ApplicationModeFactory {

    public static ApplicationMode getApplicationMode(String mode){
        switch (mode){
            case FILE_APPLICATION_MODE:
                return new FileReaderMode();
            case CONSOLE_APPLICATION_MODE:
                return new ConsoleMode();
            default:
                System.out.println("Mode not provided. Defaulting to console mode");
                return new ConsoleMode();
        }
    }
}
