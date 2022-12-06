package com.FinanceApp.Tabs;

import com.FinanceApp.Data.DataHandler;

import javax.swing.*;
import java.awt.*;

import static com.FinanceApp.App.DATAHANDLER;

// Overview Page
// Displays High Level Compiled Data E.g. Totals
public class OverviewTab extends BaseTab {

    DataHandler dataHandler = DATAHANDLER;
    public OverviewTab(){
        setBackground(Color.darkGray);

        var textPane = new JTextPane();
        textPane.setEditable(false);
        UpdateData(textPane);

        var updateButton = new Button();
        updateButton.setLabel("Update Stats");
        updateButton.addActionListener((x)-> UpdateData(textPane));

        add(updateButton);
        add(textPane);
    }

    void UpdateData(JTextPane textPane){
        // TODO: Gross Total | Tax Total | Net Total
        // TODO: Average Per Week | Month | Year
        String contentBuilder = "Total Gross\t\t$" + dataHandler.getGrossPay() + "\n" +
                "Total Taxes\t\t$" + dataHandler.getTotalTax() + "\n" +
                "Total Net\t\t$" + dataHandler.getNetPay() + "\n";

        textPane.setText(contentBuilder);
    }

    @Override
    public String Title() {
        return "Overview";
    }

    @Override
    public Icon Icon() {
        return null;
    }

    @Override
    public String ToolTip() {
        return "Contains Basic Statistics";
    }
}
