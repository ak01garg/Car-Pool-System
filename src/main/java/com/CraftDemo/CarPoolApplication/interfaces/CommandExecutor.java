package com.CraftDemo.CarPoolApplication.interfaces;

public interface CommandExecutor {

    boolean canExecute(String command);
    void execute(String command);

}
