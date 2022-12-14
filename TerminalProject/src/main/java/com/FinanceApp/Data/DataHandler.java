package com.FinanceApp.Data;

import java.util.List;

public class DataHandler {
    public List<WeeklyShift> weeklyShifts;
    public List<ShiftData> allShifts;
    public LoadFinanceData loadFinanceData;

    public DataHandler() {
        loadFinanceData = new LoadFinanceData();
        weeklyShifts = loadFinanceData.weeklyShifts;
        allShifts = loadFinanceData.allShifts;
    }

    public float GetGrossPay() {
        float totalGross = 0;
        for (var week : weeklyShifts) {
            totalGross += week.getWeeklyGross();
        }

        return totalGross;
    }

    public float GetNetPay() {
        float totalNet = 0;
        for (var week : weeklyShifts) {
            totalNet += week.getWeeklyNet();
        }

        return totalNet;
    }

    public float GetTotalTax() {
        float totalTax = 0;
        for (var week : weeklyShifts) {
            totalTax += week.getWeeklyTax();
        }

        return totalTax;
    }
}
