package com.FinanceApp.Commands;

import com.FinanceApp.Data.DataHandler;
import com.FinanceApp.Data.WeeklyShift;
import com.FinanceApp.GUI_OLD.GenericGUI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.FinanceApp.App.DATAHANDLER;

public class DisplayResults implements ICommand {
    DataHandler dataHandler = DATAHANDLER;

    ResultType type;

    public DisplayResults(ResultType type) {
        this.type = type;
    }

    @Override
    public String Description() {
        return null;
    }

    @Override
    public String Key() {
        return "STATS";
    }

    @Override
    public String SubKey() {
        return type.name().toUpperCase();
    }

    @Override
    public ArrayList<String> args() {
        var x = new ArrayList<String>();
        x.add("ResultType: Enum, Display Type");
        x.add("Integer: (Result # To Display)");
        return x;
    }

    @Override
    public void Invoke(String[] args) {
        StringBuilder result = new StringBuilder();

        switch (type) {

            case TotalGross:
                result = new StringBuilder(String.valueOf(dataHandler.GetGrossPay()));
                break;
            case TotalTax:
                result = new StringBuilder(String.valueOf(dataHandler.GetTotalTax()));
                break;
            case TotalNet:
                result = new StringBuilder(String.valueOf(dataHandler.GetNetPay()));
                break;
            case TopEarningWeeks:
                // Get Weeks Count to Display
                var count = 0;

                // Guard & Default Count = 1
                if (args == null || args.length == 0 || args[0].isEmpty() || args[0].isBlank()) count = 1;
                else count = Integer.parseInt(args[0]);

                // Copy Week Data
                var listCopy = new ArrayList<>(dataHandler.weeklyShifts);

                // Sort By Net | Reverse List
                listCopy.sort(
                        Comparator.comparing(
                                WeeklyShift::getWeeklyNet)
                                .reversed());

                // Cache New Holder List
                List<WeeklyShift> topShifts = new ArrayList<>();

                // Grab Each Result
                for (int i = 0; i < count; i++) {
                    topShifts.add(listCopy.get(i));
                    result.append(topShifts.get(i).ToString());
                }
                break;
        }

        System.out.println(result);

        var x = new GenericGUI("Results GUI", "Results", result.toString());
        x.setSize(800, 400);
        x.setVisible(true);
    }
}
