package com.example.Commands;

import javax.swing.*;
import java.util.ArrayList;

public class ToggleGUI implements ICommand {

    JFrame GUI;
    String subKey;

    public ToggleGUI(JFrame frame, String subKey){
        GUI = frame;
        this.subKey = subKey;
    }

    @Override
    public String Key() {
        return "GUI";
    }

    @Override
    public String SubKey() {
        return subKey;
    }

    @Override
    public ArrayList<String> args() {
        var x = new ArrayList<String>();
        x.add("Boolean");
        return x;
    }

    @Override
    public void Invoke(String[] args) {
        // Convert Args
        boolean val = Boolean.parseBoolean(args[0]);
        GUI.setVisible(val);
    }
}
