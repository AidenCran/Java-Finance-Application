package com.example;

import java.time.LocalTime;
import java.util.Date;

public class ShiftData {
    public String weekday;
    public Date date;
    public LocalTime startTime;
    public LocalTime endTime;
    public float hoursWorked;
    public float rate;
    public float gross;

    public ShiftData(String day, Date date, LocalTime startTime, LocalTime endTime, float hrs, float rate, float gross)
    {
        this.weekday = day;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hoursWorked = hrs;
        this.rate = rate;
        this.gross = gross;
    }
}
