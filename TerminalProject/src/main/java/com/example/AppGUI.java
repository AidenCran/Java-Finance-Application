package com.example;

import com.example.Commands.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

import static com.example.App.TAXCALCULATOR;

public class AppGUI {
    public LoadFinanceData loadFinanceData = new LoadFinanceData();
    TaxCalculator TaxCalculator;

    HashMap<String, ICommand> CommandMap = new HashMap<>();

    JFrame MainGUI;
    JFrame GraphGUI;

    int baseFrameWidth = 600;
    int baseFrameHeight = 400;

    public AppGUI() {
        // Cache Calculator
        TaxCalculator = TAXCALCULATOR;

        DisplayManualCalculatorGUI();
        DisplayStackedBarChart(CompileNetTaxDataset());

        SetupCommands();


        OpenCodeGUI();

        JFrame frame = new JFrame("T");
//        frame.setVisible(true);
        JPanel basePanel = new JPanel();
        frame.add(basePanel);
        frame.setVisible(true);
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = basePanel.getInputMap(condition);
        ActionMap actionMap = basePanel.getActionMap();

        // Refactor
        String click = "click";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), click);
        actionMap.put(click, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                OpenCodeGUI();
            }
        });
    }

    void SetupCommands(){
        var x = new ToggleGUI(MainGUI, "Main");
        var y = new ToggleGUI(GraphGUI, "Graph");

        CommandMap.put(x.Key(), x);
//        CommandMap.put(y.Key(), y);
    }

    private void OpenCodeGUI(){

        JFrame frame = new JFrame("Title");
        String[] output = JOptionPane.showInputDialog(frame, "Prompt").split(" ");
        String key = output[0];

        if (CommandMap.containsKey(key)){
            ICommand obj = CommandMap.get(key);

            String[] args = output;

            // TODO: Refactor To Remove With 'N' Length (Where N is amount of Keys)

            // Remove Key
            args = ArrayUtils.remove(args, 0);

            // Remove Sub Key
            args = ArrayUtils.remove(args, 0);

            System.out.println(Arrays.toString(output) + "\n" + Arrays.toString(args));
            obj.Invoke(args);
        }
    }

    // TODO: Rework Below GUI to not CORE / MAIN but a side GUI. A GUI Accessible Through The Console Above
    // TODO: Adjust Console Above | Usable, Refactor
    private void DisplayManualCalculatorGUI() {
        MainGUI = new JFrame("Tax Calculator");
        MainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainGUI.setSize(baseFrameWidth, baseFrameHeight);

        // Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem openMenuItem = new JMenuItem("Open");

        openMenuItem.addActionListener((x) -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Select a TSV File");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TSV Files", "tsv");
            jfc.addChoosableFileFilter(filter);

            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File output = jfc.getSelectedFile();
                System.out.println(output.getPath());

                // TODO: Add Additional Menu To Set Column Indexes Correlating To Data Columns

                loadFinanceData.OpenFinanceDataTSVFile(output);
            }
        });

        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(openMenuItem);
        m1.add(m22);

        // Text Area at the Center
        JTextArea ta = new JTextArea();
        ta.setEditable(false);

        // General Panel
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(4);

        //#region Buttons

        JButton send = new JButton("Send");

        send.addActionListener((x) ->
        {
            if (tf.getText() == null) return;

            float gross;

            try {
                gross = Float.parseFloat(tf.getText());
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                tf.selectAll();
                tf.setBackground(Color.red);
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            tf.setBackground(Color.white);

            // Calculate Data
            var tax = TaxCalculator.ReturnTax(gross);
            var net = gross - tax;

            // Append Values
            String nl = "Gross: " + gross + "\tTax: " + tax + "\tNet: " + net;
            ta.append("\n" + nl);

            // Reset Text
            tf.setText("");
        });


        JButton reset = new JButton("Reset");

        reset.addActionListener((x) ->
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
        MainGUI.getContentPane().add(BorderLayout.SOUTH, panel);
        MainGUI.getContentPane().add(BorderLayout.NORTH, mb);
        MainGUI.getContentPane().add(BorderLayout.CENTER, ta);
        MainGUI.add(BorderLayout.CENTER, new JScrollPane(ta));
        MainGUI.setResizable(true);
//        MainGUI.setVisible(true);

        // Post Init
        tf.requestFocus();

        // Center The Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xLocation = screenSize.width / 2 - baseFrameWidth / 2;
        int yLocation = screenSize.height / 2 - baseFrameHeight / 2;
        MainGUI.setLocation(xLocation, yLocation);

        //#region Key Binds

        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = panel.getInputMap(condition);
        ActionMap actionMap = panel.getActionMap();

        String enter = "enter";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
        actionMap.put(enter, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (tf.isFocusOwner()) {
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
    void DisplayStackedBarChart(DefaultCategoryDataset dataset) {
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
//        GraphGUI.setVisible(true);
    }

    void DisplayStatistics() {

    }


    /**
     * To Be Refactored
     */
    DefaultCategoryDataset CompileNetTaxDataset() {
        var dataset = new DefaultCategoryDataset();

        for (var week : loadFinanceData.weeklyShifts) {
            var weekID = String.format("%s", week.getWeekID());
            dataset.setValue(week.getWeeklyNet(), "Net", weekID);
            dataset.setValue(week.getWeeklyTax(), "Tax", weekID);
        }

        return dataset;
    }
}