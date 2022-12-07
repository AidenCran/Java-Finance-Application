package com.FinanceApp.Tabs;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import static com.FinanceApp.App.TAXCALCULATOR;

public class TaxCalcTab extends BaseTab {

    JTextArea OutputArea;
    JTextField PromptField;

    public TaxCalcTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 400));

        add(TopPanel());
        add(CorePanel());
    }

    JPanel TopPanel(){
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

        // General Panel
        JLabel promptLabel = new JLabel("Enter Text");
        PromptField = new JTextField(4);
        PromptField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Limit Character Input
                if (PromptField.getText().length() >= 4 ) {
                    e.consume();
                }
            }
        });

        //#region Buttons

        JButton send = new JButton("Send");

        send.addActionListener((x) ->
        {
            if (PromptField.getText() == null) return;

            float gross;

            try {
                gross = Float.parseFloat(PromptField.getText());
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                PromptField.selectAll();
                PromptField.setBackground(Color.red);
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            PromptField.setBackground(Color.white);

            // Calculate Data
            var tax = TAXCALCULATOR.ReturnTax(gross);
            var net = gross - tax;

            // Append Values
            String nl = "Gross: " + gross + "\tTax: " + tax + "\tNet: " + net;
            OutputArea.append("\n" + nl);

            // Reset Text
            PromptField.setText("");
        });


        JButton reset = new JButton("Reset");

        reset.addActionListener((x) ->
        {
            OutputArea.setText(null);
            PromptField.setText(null);
        });

        //#endregion

        var panel = new JPanel();

        //#region Key Binds

        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        String enter = "enter";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
        actionMap.put(enter, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (PromptField.isFocusOwner()) {
                    send.doClick();
                    return;
                }

                // Must be a default way of operating this
                if (send.isFocusOwner()) send.doClick();
                if (reset.isFocusOwner()) reset.doClick();
            }
        });

        //#endregion

        panel.add(promptLabel);
        panel.add(PromptField);
        panel.add(send);
        panel.add(reset);
        panel.add(mb);
        return panel;
    }

    JPanel CorePanel() {
        // Text Area at the Center
        OutputArea = new JTextArea();
        OutputArea.setEditable(false);
        OutputArea.setPreferredSize(new Dimension(300, 400));

        var panel = new JPanel();
        panel.add(OutputArea);
        panel.setOpaque(false);
        return panel;
    }

    @Override
    public void OnTabSelection() {
        PromptField.requestFocus();
    }

    @Override
    public String Title() {
        return "Tax Calculator";
    }

    @Override
    public Icon Icon() {
        return null;
    }

    @Override
    public String ToolTip() {
        return "Quickly Calculate Withheld Tax (Weekly)";
    }

    @Override
    public Color TabColour() {
        return Color.ORANGE;
    }
}
