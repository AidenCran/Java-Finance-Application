package com.FinanceApp;

import com.FinanceApp.Data.TaxCalculator;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing The TaxCalculator<br>
 * Results May Vary Pre- / Post-2022 (As Outlined In README.md)<br>
 * Results Compared Against: <a href="https://www.ato.gov.au/Calculators-and-tools/Host/?anchor=TWC&anchor=TWC/questions#TWC/questions">ATO Withheld Tax Calculator</a>
 */
public class TaxCalculatorTest {
    @Test
    @DisplayName("Net Value of 0 should be 0")
    public void netValueOf0_ShouldReturn0(){
        // Arrange
        float grossValue = 0;
        TaxCalculator taxCalc = new TaxCalculator();

        // Act
        float expected = 0;
        float actual = taxCalc.ReturnNet(grossValue);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Net Value of Integer Limit, Should be Integer Limit (No Difference)")
    public void netValueOfIntLimit_ShouldReturnIntLimit_NO_DIFFERENCE(){
        // Arrange
        float grossValue = Integer.MAX_VALUE;
        TaxCalculator taxCalc = new TaxCalculator();

        // Act
        float expected = Integer.MAX_VALUE;
        float actual = taxCalc.ReturnNet(grossValue);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Net Value of Integer Minimum, Should be Integer Minimum (No Difference)")
    public void netValueOfIntMin_ShouldReturnIntMin_NO_DIFFERENCE(){
        // Arrange
        float grossValue = Integer.MIN_VALUE;
        TaxCalculator taxCalc = new TaxCalculator();

        // Act
        float expected = Integer.MIN_VALUE;
        float actual = taxCalc.ReturnNet(grossValue);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void netValueOf1200_ShouldReturn969_DIFFERENCE_OF_231() {
        // Arrange
        float grossValue = 1200;
        TaxCalculator taxCalc = new TaxCalculator();

        // Act
        float expected = 969;
        float actual = taxCalc.ReturnNet(grossValue);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void netValueOf2306_ShouldReturn1693_DIFFERENCE_OF_613() {
        // Arrange
        float grossValue = 2306;
        TaxCalculator taxCalc = new TaxCalculator();

        // Act
        float expected = 1693;
        float actual = taxCalc.ReturnNet(grossValue);

        // Assert
        assertEquals(expected, actual);
    }

//    /**
//     * Above $3461 | Tax Table Doesn't Fully Reflect Tax Withheld
//     */
}
