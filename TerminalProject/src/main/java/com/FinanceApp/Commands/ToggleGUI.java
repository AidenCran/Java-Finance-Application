package com.FinanceApp.Commands;

import com.FinanceApp.GUI.GenericGUI;

import java.util.ArrayList;

public class ToggleGUI<T extends GenericGUI> implements ICommand {

    T GUI;
    String subKey;


    /**
     * @param GUI GUI To Toggle
     * @param subKey GUI Key
     */
    public ToggleGUI(T GUI, String subKey) {
        this.GUI = GUI;
        this.subKey = subKey;
    }

    @Override
    public String Description() {
        return "Toggle Select Windows";
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
        String b;
        if (args.length == 0) b = "True";
        else b = args[0];
        boolean val = ParseBool(b);

        if (val) {
            GUI.setVisible(true);
        } else {
            GUI.dispose();
        }
    }
}
