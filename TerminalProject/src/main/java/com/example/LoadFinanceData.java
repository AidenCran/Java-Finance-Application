package com.example;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LoadFinanceData {
    ArrayList<ShiftData> shiftData = new ArrayList<>();

    public LoadFinanceData() {
        OpenTSVFile(new File("src/ShiftData.tsv"));
    }

    // Extracts Information From TSV File
    public void OpenTSVFile(File file) {
        int rows = 180;
        int columns = 8;
        String[][] array = new String[rows][columns];

        try (var scan = new BufferedReader(new FileReader(file))) {
            // Skip First Line
            scan.readLine();

            for (int i = 0; i < rows; i++) {
                String[] line = scan.readLine().split("\t");

                // Skip Empty Lines
                if (line.length == 0) continue;

                // Specific To Provided Dataset
                if (line[1].isEmpty()) continue;

                System.arraycopy(line, 0, array[i], 0, columns);


                // Format Data
                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ENGLISH);

                String weekday = array[i][0];

                Date newDate = new SimpleDateFormat("dd/MM").parse(array[i][1]);

                LocalTime startTime = LocalTime.parse(array[i][2].toUpperCase(), formatter);
                LocalTime endTime = LocalTime.parse(array[i][3].toUpperCase(), formatter);

                float hours = Float.parseFloat(array[i][5].replace("$",""));
                float rate = Float.parseFloat(array[i][6].replace("$",""));
                float gross = Float.parseFloat(array[i][7].replace("$",""));

                ShiftData newData = new ShiftData(weekday, newDate, startTime, endTime, hours, rate, gross);
                shiftData.add(newData);
            }
            float total = 0;
            for (var item: shiftData) {
                System.out.println(item.gross);
                total += item.gross;
            }

            System.out.println("Total: " + total);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}