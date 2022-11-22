package com.FinanceApp;

import com.FinanceApp.Data.TaxCalculator;
import com.FinanceApp.GUI.AppGUI;

public class App {
    public static TaxCalculator TAXCALCULATOR = new TaxCalculator();

    public static void main(String[] args) {
        TAXCALCULATOR = new TaxCalculator();
        AppGUI app = new AppGUI();

        System.out.println("GOT HERE");
    }
}