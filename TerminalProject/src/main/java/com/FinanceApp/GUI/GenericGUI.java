package com.FinanceApp.GUI;

import com.FinanceApp.GUIExtensions.GUIScreenPosition;
import com.FinanceApp.GUIExtensions.ScreenPosition;

import javax.swing.*;
import java.awt.*;

public class GenericGUI {

    public GenericGUI() {
    }

    public JFrame GetFrame() {
        return frame;
    }

    protected JFrame frame;
    protected JPanel panel;
    protected JLabel title;
    protected JTextPane textPane;

    protected int baseFrameWidth = 600;
    protected int baseFrameHeight = 400;

    public GenericGUI(String FrameTitle, String LabelTitle, String PanelContent) {
        frame = new JFrame(FrameTitle);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText(PanelContent);

        panel = new JPanel();
        panel.add(textPane);

        title = new JLabel(LabelTitle, JLabel.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 40));

        frame.getContentPane().add(BorderLayout.NORTH, title);
        frame.getContentPane().add(BorderLayout.CENTER, panel);

        GUIScreenPosition.SetScreenLocation(ScreenPosition.Center, frame);
    }
}
