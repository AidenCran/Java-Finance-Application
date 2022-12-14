package com.FinanceApp.GUI_OLD;

import com.FinanceApp.Commands.CommandHandler;

import javax.swing.*;

import static com.FinanceApp.GUI_OLD.GUIManager.COMMANDHANDLER;

public class MainGUI extends GenericGUI {
    CommandHandler commandHandler = COMMANDHANDLER;

    public MainGUI() {
        super();

        setTitle("Core Panel");
        titleLabel.setText("Main GUI");

        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Press F1 To Open Command Search\n\n" +
                "Below Are Some Commands. Try Out Some\n");

        for (var command : commandHandler.GetCommands()) {
            contentBuilder.append(command.Key());
            contentBuilder.append(" ");
            contentBuilder.append(command.SubKey());
            contentBuilder.append("\t\t| Desc: ");
            contentBuilder.append(command.Description());
            contentBuilder.append("\n");
        }


        CreateTextPanel(contentBuilder.toString());

        // Override Close Operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}