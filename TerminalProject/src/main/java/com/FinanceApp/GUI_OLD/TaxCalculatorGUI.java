package com.FinanceApp.GUI_OLD;

import com.FinanceApp.Data.TaxCalculator;
import com.FinanceApp.GUIExtensions.GUIScreenPosition;
import com.FinanceApp.GUIExtensions.ScreenPosition;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import static com.FinanceApp.App.TAXCALCULATOR;

public class TaxCalculatorGUI extends GenericGUI {

    TaxCalculator TaxCalculator;

    public TaxCalculatorGUI() {
        // Cache Calculator
        TaxCalculator = TAXCALCULATOR;

        setName("Tax Calculator");

        setSize(baseFrameWidth, baseFrameHeight);

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

                // TODO: FIX CONNECTION

//                dataHandler.loadFinanceData.OpenFinanceDataTSVFile(output);
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
            // TODO: Refactor | TaxCalculator Variable (Not Necessary)
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
        getContentPane().add(BorderLayout.SOUTH, panel);
        getContentPane().add(BorderLayout.NORTH, mb);
        getContentPane().add(BorderLayout.CENTER, ta);
        add(BorderLayout.CENTER, new JScrollPane(ta));
        setResizable(true);

        // Post Init
        tf.requestFocus();

        // Center The Frame
        GUIScreenPosition.SetScreenLocation(ScreenPosition.Center, this);

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
}
