package com.FinanceApp.GUI;

import com.FinanceApp.GUIExtensions.GUIScreenPosition;
import com.FinanceApp.GUIExtensions.ScreenPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static com.FinanceApp.GUI.GUIManager.COMMANDHANDLER;

public class GenericGUI extends JFrame {
    protected JPanel panel;
    protected JLabel titleLabel;
    protected JTextPane textPane;
    protected JButton closeButton;
    protected int baseFrameWidth = 600;
    protected int baseFrameHeight = 400;

    public GenericGUI() {
        CreateCoreGUI();
    }

    public GenericGUI(String FrameTitle, String LabelTitle, String PanelContent) {
        CreateCoreGUI();

        setName(FrameTitle);
        setSize(500, 300);

        CreateTextPanel(PanelContent);

        titleLabel.setText(LabelTitle);
    }

    private void CreateCoreGUI(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        closeButton = new JButton();
        closeButton.setText("Close");
        closeButton.addActionListener(e -> dispose());

        panel = new JPanel();

        titleLabel = new JLabel("<TITLE>", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 40));

        getContentPane().add(BorderLayout.NORTH, titleLabel);
        getContentPane().add(BorderLayout.CENTER, panel);
        getContentPane().add(BorderLayout.SOUTH, closeButton);

        GUIScreenPosition.SetScreenLocation(ScreenPosition.Center, this);

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

    protected void CreateTextPanel(String content){
        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText(content);

        panel.add(textPane);
    }
}