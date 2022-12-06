package com.FinanceApp.GUI_OLD;

import com.FinanceApp.Commands.CommandHandler;

public class GUIManager {
    public static CommandHandler COMMANDHANDLER;

    public GUIManager() {
        // Create Command Handler
        COMMANDHANDLER = new CommandHandler(this);

        // Generate GUIs
        new MainGUI();

//        COMMANDHANDLER.PromptSearchInput();
    }
}