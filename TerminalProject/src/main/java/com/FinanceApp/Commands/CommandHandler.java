package com.FinanceApp.Commands;

import com.FinanceApp.GUI.GUIManager;
import com.FinanceApp.GUI.GraphGUI;
import com.FinanceApp.GUI.StatisticGUI;
import com.FinanceApp.GUI.TaxCalculatorGUI;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.util.ArrayList;

public class CommandHandler {

    private final ArrayList<ICommand> _commands = new ArrayList<>();
    private final GUIManager _appGUI;

    public CommandHandler(GUIManager appGUI) {
        this._appGUI = appGUI;

        SetupCommands();
    }

    /**
     * Test Case
     */
    public CommandHandler() {
        _appGUI = null;
    }

    public ArrayList<ICommand> GetCommands() {
        return _commands;
    }

    /**
     * Defines all commands
     */
    void SetupCommands() {
        _commands.add(new ToggleGUI<>(new TaxCalculatorGUI(), "CALC"));
        _commands.add(new ToggleGUI<>(new GraphGUI(), "GRAPH"));
        _commands.add(new ToggleGUI<>(new StatisticGUI(), "STATS"));

        _commands.add(new DisplayResults(ResultType.TotalGross));
        _commands.add(new DisplayResults(ResultType.TotalTax));
        _commands.add(new DisplayResults(ResultType.TotalNet));
        _commands.add(new DisplayResults(ResultType.TopEarningWeeks));
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
    public boolean CallCommand(String[] input) {
        // Guard
        if (input == null || input.length < 2) return false;

        // Define Keys
        String key = input[0].toUpperCase();
        String subKey = input[1].toUpperCase();

        if (_commands.size() == 0) return false;

        // Query & Guard
        var command = _commands.stream().filter(x -> x.Key().equals(key) && x.SubKey().equals(subKey)).findFirst().orElse(null);
        if (command == null) return false;

        // Reformat String
        String[] args = input;

        // Remove Key & Sub Key
        args = ArrayUtils.remove(args, 0);
        args = ArrayUtils.remove(args, 0);

//        System.out.println(Arrays.toString(output) + "\n" + Arrays.toString(args));
        command.Invoke(args);
        return true;
    }
}
