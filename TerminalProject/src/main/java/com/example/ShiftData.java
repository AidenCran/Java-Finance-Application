package com.example;

import java.time.*;


public class ShiftData {
    public LocalDate date;
    public LocalTime startTime;
    public LocalTime endTime;
    public float rate;
    public boolean isHoliday;

    public String weekday;
    public float hoursWorked;
    public float gross;

    public ShiftData(LocalDate date, LocalTime startTime, LocalTime endTime, float rate, boolean isHoliday)
    {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rate = rate;
        this.isHoliday = isHoliday;

        weekday = date.getDayOfWeek().toString();
        hoursWorked = endTime.toSecondOfDay() - startTime.toSecondOfDay();
        hoursWorked =  hoursWorked / 60 / 60;
        gross = hoursWorked * rate;
    }
}
