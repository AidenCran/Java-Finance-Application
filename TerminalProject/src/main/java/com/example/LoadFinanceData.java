package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoadFinanceData {
    ArrayList<ShiftData> allShifts = new ArrayList<>();
    List<WeeklyShift> weeklyShifts;
    int dateColIndex = 1;
    int startTimeColIndex = 2;
    int endTimeColIndex = 3;
    int rateColIndex = 6;
    int holidayColIndex = 7;

    public LoadFinanceData(File file) {
        OpenFinanceDataTSVFile(file);
    }

    public LoadFinanceData(String fileName) {
        OpenFinanceDataTSVFile(new File(fileName));
    }

    public LoadFinanceData() {
        OpenFinanceDataTSVFile(new File("src/ShiftData.tsv"));
    }

    /**
     * @param file Extracts Finance Data From Parsed TSV File
     *             Columns Dynamically Adjusted By User Through GUI
     */
    public void OpenFinanceDataTSVFile(File file) {
        int rows = 200;
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
                LocalDate newDate = LocalDate.parse(array[i][dateColIndex], dateFormatter);
                LocalTime startTime = LocalTime.parse(array[i][startTimeColIndex].toUpperCase(), timeFormatter);
                LocalTime endTime = LocalTime.parse(array[i][endTimeColIndex].toUpperCase(), timeFormatter);
                float rate = Float.parseFloat(array[i][rateColIndex].replace("$", ""));
                boolean isHoliday = Boolean.parseBoolean(array[i][holidayColIndex]);

                // Create New Data Object
                ShiftData newData = new ShiftData(newDate, startTime, endTime, rate, isHoliday);
                allShifts.add(newData);
            }


        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        GroupDataIntoWeeks();
    }

    void GroupDataIntoWeeks() {
        // Cache Local List
        List<WeeklyShift> weeklyShiftList = new ArrayList<>();

        // Cache Shift Holder
        List<ShiftData> shifts = new ArrayList<>();
        ShiftData lastShift = allShifts.get(0);

        // Iterate Through All Shifts
        for (var shift : allShifts) {

            // If Shift Has The Same ID
            if (shift.getShiftID() == lastShift.getShiftID()) {
                shifts.add(shift);
            } else {
                weeklyShiftList.add(new WeeklyShift(shifts));
                shifts = new ArrayList<>();
                shifts.add(shift);
            }

            lastShift = shift;
        }

        // Add Last Week
        weeklyShiftList.add(new WeeklyShift(shifts));
        weeklyShifts = weeklyShiftList;
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

    // TODO Generate JSON File Containing Compiled Data
}