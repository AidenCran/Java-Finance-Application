package com.FinanceApp.GUI;

import com.FinanceApp.Commands.CommandHandler;

import javax.swing.*;

public class GUIManager {
    public static CommandHandler COMMANDHANDLER;
    public JFrame MainGUI;

    public GUIManager() {
        // Create Command Handler
        COMMANDHANDLER = new CommandHandler(this);

        // Generate GUIs
        CreateMainGUI();

        COMMANDHANDLER.PromptSearchInput();
    }

    public void PromptSearch() {
        COMMANDHANDLER.PromptSearchInput();
    }

    public void CreateMainGUI() {
        String FrameTitle = "Core Panel";
        String LabelTitle = "Main GUI";

        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Press F1 To Open Command Search\n\n" +
                "Below Are Some Commands. Try Out Some\n");
        for (var command : COMMANDHANDLER.GetCommands()) {
            contentBuilder.append(command.Key() + " " + command.SubKey() + " | Desc: " + command.Description() + "\n");
        }

        var x = new GenericGUI(FrameTitle, LabelTitle, contentBuilder.toString());
        MainGUI = x.GetFrame();

        // Override Close Operation
        MainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainGUI.setVisible(true);
    }


    // TODO: Rework Below GUI to not CORE / MAIN but a side GUI. A GUI Accessible Through The Console Above
    // TODO: Adjust Console Above | Usable, Refactor
    public void CreateManualCalculatorGUI(Boolean b) {
        new TaxCalculatorGUI();
    }

    public void CreateStackedBarChartGUI() {
        new GraphGUI();
    }

    public void CreateStatisticsGUI() {
        new StatisticGUI();
    }
}