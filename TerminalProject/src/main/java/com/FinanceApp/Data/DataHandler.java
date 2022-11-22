package com.FinanceApp.Data;

import java.util.List;

public class DataHandler {
    public List<WeeklyShift> weeklyShifts;

    public LoadFinanceData loadFinanceData;

    public DataHandler() {
        loadFinanceData = new LoadFinanceData();
        weeklyShifts = loadFinanceData.weeklyShifts;
    }

    public float getGrossPay() {
        float totalGross = 0;
        for (var week : weeklyShifts) {
            totalGross += week.getWeeklyGross();
        }

        return totalGross;
    }

    public float getNetPay() {
        float totalNet = 0;
        for (var week : weeklyShifts) {
            totalNet += week.getWeeklyNet();
        }

        return totalNet;
    }

    public float getTotalTax() {
        float totalTax = 0;
        for (var week : weeklyShifts) {
            totalTax += week.getWeeklyTax();
        }

        return totalTax;
    }
}
