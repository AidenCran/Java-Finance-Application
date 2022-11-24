package com.FinanceApp.GUI;

import com.FinanceApp.Commands.CommandHandler;

public class GUIManager {
    public static CommandHandler COMMANDHANDLER;

    public GUIManager() {
        // Create Command Handler
        COMMANDHANDLER = new CommandHandler(this);

        // Generate GUIs
        new MainGUI();

        COMMANDHANDLER.PromptSearchInput();
    }
}