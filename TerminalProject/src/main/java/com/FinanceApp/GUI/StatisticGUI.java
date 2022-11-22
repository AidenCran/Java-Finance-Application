package com.FinanceApp.GUI;

import com.FinanceApp.Data.DataHandler;

import static com.FinanceApp.App.DATAHANDLER;

public class StatisticGUI extends GenericGUI {
    public StatisticGUI() {
        DataHandler dataHandler = DATAHANDLER;

        // TODO: Gross Total | Tax Total | Net Total
        // TODO: Average Per Week | Month | Year

        String contentBuilder = "Total Gross\t\t$" + dataHandler.getGrossPay() + "\n" +
                "Total Taxes\t\t$" + dataHandler.getTotalTax() + "\n" +
                "Total Net\t\t$" + dataHandler.getNetPay() + "\n";

        // TODO: Fix Quick Hack
        var x = new GenericGUI("Statistic GUI", "Statistics", contentBuilder);
        frame = x.frame;
    }
}
