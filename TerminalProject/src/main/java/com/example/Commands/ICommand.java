package com.example.Commands;

import java.util.ArrayList;

public interface ICommand {

    // Command Key
    // E.g. "GUI"
    public String Key();

    // Command Key
    // E.g. "Main"
    public String SubKey();

    // Args E.g. "true"
    public ArrayList<String> args();

    // Command Functionality
    public void Invoke(String[] args);
}
