package com.CraftDemo.CarPoolApplication;

import com.CraftDemo.CarPoolApplication.factories.ApplicationModeFactory;
import com.CraftDemo.CarPoolApplication.factories.CommandExecutorFactory;
import com.CraftDemo.CarPoolApplication.interfaces.ApplicationMode;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class CarPoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarPoolApplication.class, args);
		ApplicationMode applicationMode = ApplicationModeFactory.getApplicationMode(args[0]);
        applicationMode.init();
    }

}
