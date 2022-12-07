package com.FinanceApp.Tabs;

import com.FinanceApp.Data.DataHandler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.FinanceApp.App.DATAHANDLER;

public class DateRangeStatTab extends BaseTab {

    DataHandler dataHandler = DATAHANDLER;

    JTextArea OutputArea;
    JTextField StartInput;
    JTextField EndInput;

    public DateRangeStatTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(SetupDatePanel());
        add(SetupOutputPanel());

        var button = new JButton("Search");
        button.setPreferredSize(new Dimension(80, 30));

        button.addActionListener((x)-> {
            if (!TryParseDate(StartInput.getText())){
                System.out.println("Start Input Invalid");
                return;
            }

            if (!TryParseDate(EndInput.getText())){
                System.out.println("End Input Invalid");
                return;
            }

            var startDate = LocalDate.parse(StartInput.getText());
            var endDate = LocalDate.parse(EndInput.getText());

            float totalGross = 0;

            // Else Correct Inputs
            for (var shift : dataHandler.allShifts) {
                // Is Before End & After Start
                if (shift.date.isBefore(endDate) && shift.date.isAfter(startDate)){
                    totalGross += shift.gross;
                }
            }

            String output = startDate.toString() + " - " + endDate.toString() + "\n" +
                    "Gross: $" + totalGross + "\n" +
                    "Super: $" + Math.floor(totalGross * 0.105) + "\n";

            OutputArea.setText(output);
        });

        add(button);
    }

    JPanel SetupDatePanel() {
        var dateStartLabel = new JLabel("Start");
        StartInput = new JTextField();
        StartInput.setText(LocalDate.now().withDayOfMonth(1).toString());
        StartInput.setToolTipText("Year-Month-Day");

        var dateEndLabel = new JLabel("End");
        EndInput = new JTextField();
        EndInput.setText(LocalDate.now().toString());
        EndInput.setToolTipText("Year-Month-Day");

        var newDim = new Dimension(80,30);
        StartInput.setPreferredSize(newDim);
        EndInput.setPreferredSize(newDim);

        var panel = new JPanel();
        panel.setOpaque(false);
        panel.add(dateStartLabel);
        panel.add(StartInput);

        panel.add(dateEndLabel);
        panel.add(EndInput);
        return panel;
    }

    JPanel SetupOutputPanel(){
        var titleLabel = new JLabel("Output");
        OutputArea = new JTextArea();
        OutputArea.setPreferredSize(new Dimension(200, 200));
        OutputArea.setEditable(false);

        var panel = new JPanel();
        panel.setOpaque(false);
        panel.add(titleLabel);
        panel.add(OutputArea);
        return panel;
    }

    Boolean TryParseDate(String dateStr){
        try {
            LocalDate.parse(dateStr);
        }
        catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

    @Override
    public void OnTabSelection() {

    }

    @Override
    public String Title() {
        return "Range Selection";
    }

    @Override
    public Icon Icon() {
        return null;
    }

    @Override
    public String ToolTip() {
        return null;
    }

    @Override
    public Color TabColour() {
        return Color.PINK;
    }
}
