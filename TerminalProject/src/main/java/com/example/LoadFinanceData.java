package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoadFinanceData {
    ArrayList<ShiftData> shiftData = new ArrayList<>();

    // Key      Integer           Week of year + Year (E.g. 12022 (Week 1 of 2022)
    // Value    Array<ShiftData>  Shifts Completed in key Week
    Map<Integer, ArrayList<ShiftData>> weeklyShifts = new HashMap<>();

    int dateIndex = 1;
    int startTimeIndex = 2;
    int endTimeIndex = 3;
    int rateIndex = 6;
    int holidayIndex = 7;

    public LoadFinanceData() {
        OpenFinanceDataTSVFile(new File("src/ShiftData.tsv"));
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
                shiftData.add(newData);
                System.out.println(newData.hoursWorked);
                System.out.println(newData.gross);

                GroupDataIntoWeeks();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    void GroupDataIntoWeeks(){

    }
}