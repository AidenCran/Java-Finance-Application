package com.example;

/**
 * Initilize GUI
 *
 */
public class App
{
    public static TaxCalculator TAXCALCULATOR = new TaxCalculator();
    public static void main( String[] args )
    {
        TAXCALCULATOR = new TaxCalculator();
        AppGUI app = new AppGUI();
        app.DisplayGUI();
    }
}