package com.FinanceApp.Data;

import java.io.File;
import java.util.Scanner;

public class TaxCalculator {
    float[][] TaxTable;

    // Constructor
    public TaxCalculator() {
        // Read & Initialize Tax Table
        ConvertTaxTable();
    }

    public float ReturnNet(float value) {
        var x = ReturnTax(value);
        return value - x;
    }

    /**
     * @param weeklyEarnings y = ax - b
     *                       y = Weekly tax
     *                       x = Weekly earnings (Whole Dollars - Floored) + 99 Cents
     *                       a & b = coefficients
     * @return Tax
     */
    public float ReturnTax(float weeklyEarnings) {
        weeklyEarnings = (float) Math.floor(weeklyEarnings);
        weeklyEarnings += 0.99;

        float result = weeklyEarnings;

        // Guard
        if (weeklyEarnings <= 0) return 0;

        // If Beyond Max Value
        // Calculator Unreliable Post Max Value ($3461)
        if (weeklyEarnings >= TaxTable[TaxTable.length - 1][0]) return 0;
//            return CalculateMethod(weeklyEarnings, TaxTable.length - 1);


        // Identify Correct Tax Section
        for (int i = 0; i < TaxTable.length; i++) {
            if (weeklyEarnings <= TaxTable[i][0]) {
                return CalculateMethod(weeklyEarnings, i);
            }
        }

        return result;
    }

    // Calculate Tax From Gross Value
    float CalculateMethod(float value, int index) {
        // Calculate With Coefficients
        return Math.round((value * TaxTable[index][1]) - TaxTable[index][2]);
    }

    // Convert Text File Into 2D Array
    void ConvertTaxTable() {
        int rows = 8;
        int columns = 3;

        float[][] array = new float[rows][columns];

        File file = new File("src\\TaxVariables.txt");

        try (var scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                for (int i = 0; i < array.length; i++) {
                    String[] line = scan.nextLine().split("\t");

                    for (int j = 0; j < line.length; j++) {
                        array[i][j] = Float.parseFloat(line[j]);
                    }
                }
            }

            TaxTable = array;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
