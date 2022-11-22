package com.FinanceApp.Commands;

import java.util.ArrayList;

public interface ICommand {

    String Description();

    // Command Key
    // E.g. "GUI"
    String Key();

    // Command Key
    // E.g. "Main"
    String SubKey();

    // Args E.g. "true"
    ArrayList<String> args();

    // Command Functionality
    void Invoke(String[] args);

    // Custom Boolean Parser
    default boolean ParseBool(String s) {
        s = s.toUpperCase();

        if (s.equals("YES") || s.equals("OPEN")) s = "TRUE";
        if (s.equals("NO") || s.equals("CLOSE")) s = "FALSE";

        return Boolean.parseBoolean(s);
    }
}
