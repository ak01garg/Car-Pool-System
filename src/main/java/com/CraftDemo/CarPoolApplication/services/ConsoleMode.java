package com.CraftDemo.CarPoolApplication.services;

import com.CraftDemo.CarPoolApplication.factories.CommandExecutorFactory;
import com.CraftDemo.CarPoolApplication.interfaces.ApplicationMode;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.CraftDemo.CarPoolApplication.utils.CommandConstants.INVALID_COMMAND_EXCEPTION_MESSAGE;

public class ConsoleMode implements ApplicationMode {

    private final CommandExecutorFactory commandExecutorFactory;

    public ConsoleMode() {
        commandExecutorFactory = CommandExecutorFactory.getInstance();
    }

    @Override
    public void init() {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                boolean commandInterpreted = false;
                final String input = reader.readLine();
                for (CommandExecutor commandExecutor : commandExecutorFactory.getCommandExecutorList()){
                    if(commandExecutor.canExecute(input)){
                        commandInterpreted  = true;
                        commandExecutor.execute(input);
                    }
                }
                if(!commandInterpreted) {
                    System.out.println(INVALID_COMMAND_EXCEPTION_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(e.getClass() + ": " +  e.getMessage());
            }
        }
    }
}
