package com.example;

import java.util.List;

import static com.example.App.TAXCALCULATOR;

public class WeeklyShift {
    private final List<ShiftData> _shifts;
    private final float _weekID;
    public TaxCalculator TaxCalculator;
    private float _weeklyGross;
    private float _weeklyTax;
    private float _weeklyNet;

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

    public float getWeekID() {
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
}
