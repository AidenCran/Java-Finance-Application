package com.FinanceApp.Commands;

import com.FinanceApp.GUI.AppGUI;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.util.ArrayList;

public class CommandHandler {

    private final ArrayList<ICommand> _commands = new ArrayList<>();

    private final AppGUI _appGUI;

    public CommandHandler(AppGUI appGUI) {
        this._appGUI = appGUI;

        SetupCommands();
    }

    /**
     * Defines all commands
     */
    void SetupCommands() {
        _commands.add(new ToggleGUI(_appGUI::CreateManualCalculatorGUI, "CALC"));
        _commands.add(new ToggleGUI(_appGUI::CreateDefaultGraphChart, "GRAPH"));
        _commands.add(new ToggleGUI(_appGUI::CreateStatisticsGUI, "STATS"));
    }

    /**
     * Prompts user to enter a command
     */
    public void PromptSearchInput() {
        JFrame frame = new JFrame("Command Query");

        // Display Input | Set Uppercase | Regex Split
        var rawInput = JOptionPane.showInputDialog(frame, "Command");
        if (rawInput == null || rawInput.isEmpty()) return;

        String[] input = rawInput.toUpperCase().split(" ");
        CallCommand(input);
    }

    /**
     * @param input User Prompt Input
     */
    void CallCommand(String[] input) {
        // Define Keys
        String key = input[0].toUpperCase();
        String subKey = input[1].toUpperCase();

        // Query & Guard
        var command = _commands.stream().filter(x -> x.Key().equals(key) && x.SubKey().equals(subKey)).findFirst().orElse(null);
        if (command == null) return;

        // Reformat String
        String[] args = input;

        // Remove Key & Sub Key
        args = ArrayUtils.remove(args, 0);
        args = ArrayUtils.remove(args, 0);

//        System.out.println(Arrays.toString(output) + "\n" + Arrays.toString(args));
        command.Invoke(args);
    }
}
