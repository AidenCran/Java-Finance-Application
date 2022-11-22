package com.FinanceApp;

import com.FinanceApp.Data.DataHandler;
import com.FinanceApp.Data.TaxCalculator;
import com.FinanceApp.GUI.GUIManager;

public class App {
    public static TaxCalculator TAXCALCULATOR;
    public static DataHandler DATAHANDLER;
    public static GUIManager GUIMANAGER;

    public static void main(String[] args) {
        TAXCALCULATOR = new TaxCalculator();
        DATAHANDLER = new DataHandler();
        GUIMANAGER = new GUIManager();
    }
}