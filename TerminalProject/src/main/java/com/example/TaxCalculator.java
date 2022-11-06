package com.example;
import java.io.*;
import java.util.Scanner;

public class TaxCalculator
{
    float[][] TaxTable;

    // Constructor
    public TaxCalculator() 
    {
        // Read & Initialize Tax Table
        ConvertTaxTable();
    }

    public float ReturnNet(float value)
    {
        var x = ReturnTax(value);
        return value - x;
    }

    public float ReturnTax(float value)
    {
        // Adjust
        int index;
        value += 0.99;

        float result = value;

        // If Beyond Max Value
        if (value >= TaxTable[TaxTable.length - 1][0]) return CalculateMethod(value, TaxTable.length - 1);        

        // Identify Correct Tax Section
        for (int i = 0; i < TaxTable.length; i++)
        {
            if (value <= TaxTable[i][0])
            {
                index = i;
                return CalculateMethod(value, index);
            }
        }

        return result;
    }

    // Calculate Tax From Gross Value
    float CalculateMethod(float value, int index)
    {
        // Calculate With Coefficients
        return Math.round((value * TaxTable[index][1]) - TaxTable[index][2]);
    }

    // Convert Text File Into 2D Array
    void ConvertTaxTable() 
    {
        int rows = 8;
        int columns = 3;

        float[][] array = new float[rows][columns];

        File file = new File("src\\TaxVariables.txt");

        try (var scan = new Scanner(file))
        {
            while (scan.hasNextLine())
            {
                for (int i = 0; i < array.length; i++)
                {
                    String[] line = scan.nextLine().split("\t");

                    for (int j = 0; j < line.length; j++)
                    {
                        array[i][j] = Float.parseFloat(line[j]);
                    }
                }
            }

            TaxTable = array;
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }
    }
}
