package com.FinanceApp.Data;

import java.text.DecimalFormat;
import java.util.List;

import static com.FinanceApp.App.TAXCALCULATOR;

public class WeeklyShift {
    private final List<ShiftData> _shifts;
    private final int _weekID;
    public com.FinanceApp.Data.TaxCalculator TaxCalculator;
    private float _weeklyGross;
    private float _weeklyTax;
    private float _weeklyNet;

    private DecimalFormat _decimalFormat = new DecimalFormat("0.00");

    public WeeklyShift(List<ShiftData> shifts) {
        TaxCalculator = TAXCALCULATOR;
        _shifts = shifts;
        _weekID = _shifts.get(0).getShiftID();

        initWeeklyData();
    }

    public List<ShiftData> getShifts() {
        return _shifts;
    }

    public float getWeeklyGross() {
        return _weeklyGross;
    }

    public float getWeeklyTax() {
        return _weeklyTax;
    }

    public float getWeeklyNet() {
        return _weeklyNet;
    }

    public int getWeekID() {
        return _weekID;
    }

    void initWeeklyData() {
        setWeeklyGross();
        setWeeklyTax();
        setWeeklyNet();
    }

    void setWeeklyGross() {
        float total = 0;
        for (var shift : _shifts) {
            total += shift.gross;
        }

        _weeklyGross = total;
    }

    void setWeeklyTax() {
        _weeklyTax = TaxCalculator.ReturnTax(_weeklyGross);
    }

    void setWeeklyNet() {
        _weeklyNet = TaxCalculator.ReturnNet(_weeklyGross);
    }

    public String ToString(){
        return "ID: " + _weekID + "\t\tGross: " + _decimalFormat.format(_weeklyGross) + "\t\tTax: " + _decimalFormat.format(_weeklyTax) + "\t\tNet:" + _decimalFormat.format(_weeklyNet) + "\n";
    }
}
