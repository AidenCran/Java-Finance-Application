package com.example;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static com.example.App.TAXCALCULATOR;

public class LoadFinanceData {
    ArrayList<ShiftData> allShifts = new ArrayList<>();

    // Key      String            Week of year + Year (E.g. 12022 (Week 1 of 2022)
    // Value    Array<ShiftData>  Shifts Completed in key Week
    Map<Integer, List<ShiftData>> weeklyShifts = new HashMap<>();

    int dateIndex = 1;
    int startTimeIndex = 2;
    int endTimeIndex = 3;
    int rateIndex = 6;
    int holidayIndex = 7;

    public LoadFinanceData(File file) {
        OpenFinanceDataTSVFile(file);
    }

    public LoadFinanceData(String fileName) {
        OpenFinanceDataTSVFile(new File(fileName));
    }

    public LoadFinanceData()
    {
        OpenFinanceDataTSVFile(new File("src/ShiftData.tsv"));

        System.out.println(getGrossPay());
        System.out.println(getNetPay());
        System.out.println(getTotalTax());
    }

    /**
     * @param file
     * Extracts Finance Data From Parsed TSV File
     * Columns Dynamically Adjusted By User Through GUI
     */
    public void OpenFinanceDataTSVFile(File file) {
        int rows = 180;
        int columns = 8;
        String[][] array = new String[rows][columns];

        try (var scan = new BufferedReader(new FileReader(file))) {

            // Skip First Line
            scan.readLine();

            // Formatters
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ENGLISH);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.ENGLISH);

            for (int i = 0; i < rows; i++) {
                String[] line = scan.readLine().split("\t");

                // Skip Empty Lines
                if (line.length == 0) continue;

                // Specific To Provided Dataset
                if (line[1].isEmpty()) continue;

                System.arraycopy(line, 0, array[i], 0, columns);

                // Required Data
                LocalDate newDate = LocalDate.parse(array[i][dateIndex], dateFormatter);
                LocalTime startTime = LocalTime.parse(array[i][startTimeIndex].toUpperCase(), timeFormatter);
                LocalTime endTime = LocalTime.parse(array[i][endTimeIndex].toUpperCase(), timeFormatter);
                float rate = Float.parseFloat(array[i][rateIndex].replace("$",""));
                boolean isHoliday = Boolean.parseBoolean(array[i][holidayIndex]);

                // Create New Data Object
                ShiftData newData = new ShiftData(newDate, startTime, endTime, rate, isHoliday);
                allShifts.add(newData);
            }



        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        GroupDataIntoWeeks();
    }

    void GroupDataIntoWeeks(){
        Map<Integer, List<ShiftData>> shiftsByWeek = new HashMap<>();
        for (var shift : allShifts){
            if (!shiftsByWeek.containsKey(shift.getShiftID())){
                shiftsByWeek.put(shift.getShiftID(), new ArrayList<>());
            }
            shiftsByWeek.get(shift.getShiftID()).add(shift);
        }

        weeklyShifts = shiftsByWeek;
    }

    public float getGrossPay()
    {
        float totalGross = 0;
        for (var shift : allShifts)
        {
            totalGross += shift.gross;
        }

        return totalGross;
    }

    public float getNetPay()
    {
        float totalNet = 0;
        for (var weeks : weeklyShifts.values())
        {
            float weeklyGross = 0;
            for (var shift : weeks)
            {
                weeklyGross += shift.gross;
            }

            totalNet += TAXCALCULATOR.ReturnNet(weeklyGross);
        }

        return totalNet;
    }

    public float getTotalTax()
    {
        float totalNet = 0;
        for (var weeks : weeklyShifts.values())
        {
            float weeklyGross = 0;
            for (var shift : weeks)
            {
                weeklyGross += shift.gross;
            }

            var tax = TAXCALCULATOR.ReturnTax(weeklyGross);
            totalNet += tax;
        }

        return totalNet;
    }

    // TODO Generate JSON File Containing Compiled Data
}