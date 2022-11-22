package com.FinanceApp.GUI;

import com.FinanceApp.GUIExtensions.GUIScreenPosition;
import com.FinanceApp.GUIExtensions.ScreenPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static com.FinanceApp.GUI.GUIManager.COMMANDHANDLER;

public class GenericGUI {

    protected JFrame frame;
    protected JPanel panel;
    protected JLabel title;
    protected JTextPane textPane;
    protected JButton button;
    protected int baseFrameWidth = 600;
    protected int baseFrameHeight = 400;

    public GenericGUI() {
    }

    public GenericGUI(String FrameTitle, String LabelTitle, String PanelContent) {
        frame = new JFrame(FrameTitle);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText(PanelContent);

        button = new JButton();
        button.setText("Close");
        button.addActionListener(e -> frame.dispose());

        panel = new JPanel();
        panel.add(textPane);

        title = new JLabel(LabelTitle, JLabel.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 40));

        frame.getContentPane().add(BorderLayout.NORTH, title);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, button);

        GUIScreenPosition.SetScreenLocation(ScreenPosition.Center, frame);

        //#region Key Binds

        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = panel.getInputMap(condition);
        ActionMap actionMap = panel.getActionMap();

        String f1 = "f1";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), f1);
        actionMap.put(f1, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                COMMANDHANDLER.PromptSearchInput();
            }
        });

        //#endregion
    }

    public JFrame GetFrame() {
        return frame;
    }
}