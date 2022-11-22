package com.FinanceApp;

import static org.junit.jupiter.api.Assertions.*;

import com.FinanceApp.Data.TaxCalculator;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TaxCalculatorTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void netResultShouldEqual969()
    {
        TaxCalculator taxCalc = new TaxCalculator();
        assertEquals(969f, taxCalc.ReturnNet(1200));
    }

    @Test
    public void thisShouldReturn1693()
    {
        TaxCalculator taxCalc = new TaxCalculator();
        assertEquals(1693f, taxCalc.ReturnNet(2306));
    }

    @Test
    public void netResultShouldEqual2513()
    {
        TaxCalculator taxCalc = new TaxCalculator();
        assertEquals(2513f, taxCalc.ReturnNet(3680));
    }
}
