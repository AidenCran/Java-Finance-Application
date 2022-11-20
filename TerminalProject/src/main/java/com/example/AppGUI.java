package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import static com.example.App.TAXCALCULATOR;

public class AppGUI
{
    public LoadFinanceData loadFinanceData = new LoadFinanceData();
    TaxCalculator taxCalc;

    int baseFrameWidth = 600;
    int baseFrameHeight = 400;

    public AppGUI()
    {
        // Cache Calculator
        taxCalc = TAXCALCULATOR;

        DisplayGUI();
        DisplayGraph(CompileNetTaxDataset());
    }

    void DisplayGUI()
    {
        JFrame frame = new JFrame("Tax Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(baseFrameWidth, baseFrameHeight);

        // Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem openMenuItem = new JMenuItem("Open");

        openMenuItem.addActionListener((x)->{
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Select a TSV File");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TSV Files", "tsv");
            jfc.addChoosableFileFilter(filter);
    
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File output = jfc.getSelectedFile();
                System.out.println(output.getPath());
                loadFinanceData.OpenFinanceDataTSVFile(output);
            }
        });

        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(openMenuItem);
        m1.add(m22);

        // Text Area at the Center
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        
        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(4); // Accepts Upto 4 Characters (9999)
        
        //#region Buttons

        JButton send = new JButton("Send");

        send.addActionListener((x)->
        {
            if (tf.getText() == null) return;
            
            float gross;

            try
            {
                gross = Float.parseFloat(tf.getText());
            }
            catch (Exception e)
            {
                System.out.println("Exception: " + e);
                tf.selectAll();
                tf.setBackground(Color.red);
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            
            tf.setBackground(Color.white);
            
            // Calculate Data
            var tax = taxCalc.ReturnTax(gross);
            var net = gross - tax;

            // Append Values
            String nl = "Gross: " + gross + "\tTax: " + tax + "\tNet: " + net;
            ta.append("\n"+nl);

            // Reset Text
            tf.setText("");
        });
        
        
        JButton reset = new JButton("Reset");

        reset.addActionListener((x)->
        {
            ta.setText(null);
            tf.setText(null);
        });

        //#endregion

        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        // Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.add(BorderLayout.CENTER, new JScrollPane(ta));
        frame.setResizable(true);
        frame.setVisible(true);
        
        // Post Init
        tf.requestFocus();

        // Center The Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xLocation = screenSize.width/2 - baseFrameWidth /2;
        int yLocation = screenSize.height/2 - baseFrameHeight /2;
        frame.setLocation(xLocation, yLocation);
        
        //#region Key Binds

        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = panel.getInputMap(condition);
        ActionMap actionMap = panel.getActionMap();
        
        String enter = "enter";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
        actionMap.put(enter, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                if (tf.isFocusOwner()) 
                {
                    send.doClick();
                    return;
                }
                
                // Must be a default way of operating this
                if (send.isFocusOwner()) send.doClick();
                if (reset.isFocusOwner()) reset.doClick();
            }
         });

        //#endregion
    }

    /**
     * Display Basic Graph
     */
    void DisplayGraph(DefaultCategoryDataset dataset)
    {
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

        JFrame frame = new JFrame("Weekly Income");

        frame.setSize(baseFrameWidth, baseFrameHeight);

        frame.add(chartPanel);
        frame.setVisible(true);
    }


    /**
     * To Be Refactored
     */
    DefaultCategoryDataset CompileNetTaxDataset()
    {
        var dataset = new DefaultCategoryDataset();

        for (var week : loadFinanceData.weeklyShifts){
            var weekID = String.format("%s", week.getWeekID());
            dataset.setValue(week.getWeeklyNet(), "Net", weekID);
            dataset.setValue(week.getWeeklyTax(), "Tax", weekID);
        }

        return dataset;
    }

}