package com.FinanceApp.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;


public class ShiftData implements Comparable<ShiftData> {
    public LocalDate date;
    public LocalTime startTime;
    public LocalTime endTime;
    public float rate;
    public boolean isHoliday;

    public String weekday;
    public float hoursWorked;
    public float gross;

    int weekOfYear;
    int year;
    int shiftID;

    public ShiftData(LocalDate date, LocalTime startTime, LocalTime endTime, float rate, boolean isHoliday) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rate = rate;
        this.isHoliday = isHoliday;

        weekday = date.getDayOfWeek().toString();
        hoursWorked = endTime.toSecondOfDay() - startTime.toSecondOfDay();
        hoursWorked = hoursWorked / 60 / 60;
        gross = hoursWorked * rate;

        CreateShiftID();
    }

    public int getShiftID() {
        return shiftID;
    }

    void CreateShiftID() {
        // Define WOY Field
        TemporalField woy = WeekFields.ISO.weekOfWeekBasedYear();

        // Get Week + 1 (Index Starts at 0)
        weekOfYear = date.get(woy) + 1;
        year = date.getYear();

        // Compile Shift ID
        String ID = year + "" + weekOfYear;
        shiftID = Integer.parseInt(ID);
    }

    // Custom Comparison Operator
    @Override
    public int compareTo(ShiftData shiftData) {
        return shiftData.getShiftID() - this.shiftID;
    }
}