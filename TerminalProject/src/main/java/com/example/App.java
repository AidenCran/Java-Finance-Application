package com.example;

/**
 * Initilize GUI
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AppGUI app = new AppGUI();
        app.taxCalc = new TaxCalculator();
        app.DisplayGUI();
    }
}