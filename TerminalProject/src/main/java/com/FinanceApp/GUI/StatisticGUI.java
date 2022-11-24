package com.FinanceApp.GUI;

import com.FinanceApp.Data.DataHandler;

import static com.FinanceApp.App.DATAHANDLER;

public class StatisticGUI extends GenericGUI {
    public StatisticGUI() {
        super();
        DataHandler dataHandler = DATAHANDLER;

        // TODO: Gross Total | Tax Total | Net Total
        // TODO: Average Per Week | Month | Year

        String contentBuilder = "Total Gross\t\t$" + dataHandler.getGrossPay() + "\n" +
                "Total Taxes\t\t$" + dataHandler.getTotalTax() + "\n" +
                "Total Net\t\t$" + dataHandler.getNetPay() + "\n";

        setTitle("Statistic GUI");
        titleLabel.setText("Statistics");
        CreateTextPanel(contentBuilder);
    }
}
