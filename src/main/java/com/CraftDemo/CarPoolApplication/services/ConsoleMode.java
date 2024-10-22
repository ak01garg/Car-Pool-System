package com.CraftDemo.CarPoolApplication.services;

import com.CraftDemo.CarPoolApplication.factories.CommandExecutorFactory;
import com.CraftDemo.CarPoolApplication.interfaces.ApplicationMode;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleMode implements ApplicationMode {

    private final CommandExecutorFactory commandExecutorFactory;

    public ConsoleMode() {
        commandExecutorFactory = new CommandExecutorFactory();
    }

    @Override
    public void init() {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                final String input = reader.readLine();
                for (CommandExecutor commandExecutor : commandExecutorFactory.getCommandExecutorList()){
                    if(commandExecutor.canExecute(input)){
                        commandExecutor.execute(input);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getClass().toString() + ": " +  e.getMessage());
            }
        }
    }
}
