package com.FinanceApp.GUI;

import com.FinanceApp.Commands.CommandHandler;
import com.FinanceApp.Data.DataHandler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class AppGUI {
    public DataHandler dataHandler;
    CommandHandler commandHandler;

    public JFrame MainGUI;
    public JFrame GraphGUI;
    public JFrame StatisticGUI;

    int baseFrameWidth = 600;
    int baseFrameHeight = 400;

    public AppGUI() {
        dataHandler = new DataHandler();

        // Generate GUIs
        CreateMainGUI();

        // Create Command Handler
        commandHandler = new CommandHandler(this);
        commandHandler.PromptSearchInput();
    }

    public void CreateMainGUI() {
        String FrameTitle = "Core Panel";
        String LabelTitle = "Main GUI";
        String Content =
                "Simple Command System (Pet Project)\n" +
                        "Try: Display Graph True\n" +
                        "Or: Display Main Open";

        var x = new GenericGUI(FrameTitle, LabelTitle, Content);
        MainGUI = x.GetFrame();

        // Override Close Operation
        MainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainGUI.setVisible(true);
    }


    // TODO: Rework Below GUI to not CORE / MAIN but a side GUI. A GUI Accessible Through The Console Above
    // TODO: Adjust Console Above | Usable, Refactor
    public void CreateManualCalculatorGUI() {
        new TaxCalculatorGUI();
    }

    /**
     * Display Basic Graph
     */
    void CreateStackedBarChartGUI(DefaultCategoryDataset dataset) {
        JFreeChart jFreeChart = ChartFactory.createStackedBarChart("Title", "", "", dataset, PlotOrientation.VERTICAL, true, true, false);

        // Set Bar Colours
        CategoryPlot plot = jFreeChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Index 0: Net Colour
        renderer.setSeriesPaint(0, Color.blue);

        // Index 1: Tax Colour
        renderer.setSeriesPaint(1, Color.red);

        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        GraphGUI = new JFrame("Weekly Income");

        GraphGUI.setSize(baseFrameWidth, baseFrameHeight);

        GraphGUI.add(chartPanel);
        GraphGUI.setVisible(true);
    }

    public void CreateDefaultGraphChart() {
        CreateStackedBarChartGUI(CompileNetTaxDataset());
    }

    public void CreateStatisticsGUI() {
        // TODO: Gross Total | Tax Total | Net Total
        // TODO: Average Per Week | Month | Year

        String contentBuilder = "Total Gross<\t\t" + dataHandler.getGrossPay() + "\n" +
                "Total Taxes\t\t" + dataHandler.getTotalTax() + "\n" +
                "Total Net\t\t" + dataHandler.getNetPay() + "\n";

        var x = new GenericGUI("Statistic GUI", "Statistics", contentBuilder);
        StatisticGUI = x.GetFrame();
    }


    /**
     * To Be Refactored
     */
    DefaultCategoryDataset CompileNetTaxDataset() {
        var dataset = new DefaultCategoryDataset();

        for (var week : dataHandler.weeklyShifts) {
            var weekID = String.format("%s", week.getWeekID());
            dataset.setValue(week.getWeeklyNet(), "Net", weekID);
            dataset.setValue(week.getWeeklyTax(), "Tax", weekID);
        }

        return dataset;
    }
}