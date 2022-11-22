package com.FinanceApp.Commands;

import javax.swing.*;
import java.util.ArrayList;

public class ToggleGUI implements ICommand {

    String subKey;

    final Runnable action;

    public ToggleGUI(Runnable action, String subKey) {
//        GUI = frame;
        this.action = action;
        this.subKey = subKey;
    }

    @Override
    public Runnable action() {
        return action;
    }

    @Override
    public String Description() {
        return "Open / Close Selected Windows";
    }

    @Override
    public String Key() {
        return "DISPLAY";
    }

    @Override
    public String SubKey() {
        return subKey;
    }

    @Override
    public ArrayList<String> args() {
        var x = new ArrayList<String>();
        x.add("Boolean: (Enable/Disable)");
        return x;
    }

    @Override
    public void Invoke(String[] args) {
        // Convert Args
        boolean val = ParseBool(args[0]);
        if (val) action.run();
//        GUI.setVisible(val);
    }
}
