package com.FinanceApp.GUI_OLD;

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

import static com.FinanceApp.App.DATAHANDLER;

public class GraphGUI extends GenericGUI {
    DataHandler dataHandler = DATAHANDLER;

    public GraphGUI() {
        super();


        var dataset = CompileNetTaxDataset();
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

        setTitle("Weekly Income");
        setSize(baseFrameWidth, baseFrameHeight);
        add(chartPanel);
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
