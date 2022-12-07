package com.FinanceApp.Tabs;

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

public class GraphTab extends BaseTab {
    public GraphTab(){
        var button = new JButton("Graph Overview");
        button.addActionListener((x)->{
            var frame = new JFrame();
            frame.add(CreateChartPanel());
            frame.setVisible(true);
            frame.pack();
        });

        add(button);
    }

    ChartPanel CreateChartPanel() {
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

        var newDim = new Dimension(800, 600);
        chartPanel.setPreferredSize(newDim);

        return chartPanel;
    }

    /**
     * To Be Refactored
     */
    DefaultCategoryDataset CompileNetTaxDataset() {
        var dataset = new DefaultCategoryDataset();

        DataHandler dataHandler = DATAHANDLER;
        for (var week : dataHandler.weeklyShifts) {
            var weekID = String.format("%s", week.getWeekID());
            dataset.setValue(week.getWeeklyNet(), "Net", weekID);
            dataset.setValue(week.getWeeklyTax(), "Tax", weekID);
        }

        return dataset;
    }

    @Override
    public void OnTabSelection() {

    }

    @Override
    public String Title() {
        return "Stat Graphs";
    }

    @Override
    public Icon Icon() {
        return null;
    }

    @Override
    public String ToolTip() {
        return "Contains Statistic Graphs";
    }

    @Override
    public Color TabColour() {
        return Color.decode("#FABAD2");
    }
}
