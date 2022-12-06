package com.FinanceApp;

import com.FinanceApp.Data.DataHandler;
import com.FinanceApp.Data.TaxCalculator;
import com.FinanceApp.GUI_OLD.GUIManager;
import com.FinanceApp.Tabs.MainWindow;

public class App {
    public static TaxCalculator TAXCALCULATOR = new TaxCalculator();
    public static DataHandler DATAHANDLER = new DataHandler();
//    public static GUIManager GUIMANAGER = new GUIManager();

    public static void main(String[] args) {
        new MainWindow();
    }
}