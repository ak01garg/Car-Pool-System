package com.CraftDemo.CarPoolApplication.services;

import com.CraftDemo.CarPoolApplication.factories.CommandExecutorFactory;
import com.CraftDemo.CarPoolApplication.interfaces.ApplicationMode;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class FileReaderMode implements ApplicationMode {

    private static final String FILE_PATH = "Commands.txt";
    private final CommandExecutorFactory commandExecutorFactory;

    public FileReaderMode() {
        this.commandExecutorFactory = new CommandExecutorFactory();
    }

    @Override
    public void init() {
        final File file = new File(FILE_PATH);
        final BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            while (true) {
                try{
                    final String input = reader.readLine();
                    List<CommandExecutor> commandExecutorList = commandExecutorFactory.getCommandExecutorList();
                    for (CommandExecutor commandExecutor : commandExecutorList){
                        if(commandExecutor.canExecute(input)){
                            commandExecutor.execute(input);
                        }
                    }
                }catch (Exception e){
                    System.out.println(e.getClass().toString() + ": " +  e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(-1);
        }
    }
}
