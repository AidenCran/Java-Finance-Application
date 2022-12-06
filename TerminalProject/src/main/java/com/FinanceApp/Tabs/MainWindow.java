package com.FinanceApp.Tabs;

import com.FinanceApp.GUIExtensions.GUIScreenPosition;
import com.FinanceApp.GUIExtensions.ScreenPosition;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MainWindow {
    public MainWindow(){
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setVisible(true);

        BaseTab panel1 = new OverviewTab();
        tabbedPane.addTab(panel1.Title(), panel1.Icon(), panel1, panel1.ToolTip());
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        BaseTab panel2 = new GraphTab();
        tabbedPane.addTab(panel2.Title(), panel2.Icon(), panel2, panel2.ToolTip());
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        BaseTab panel3 = new TaxCalcTab();
        tabbedPane.addTab(panel3.Title(), panel3.Icon(), panel3, panel3.ToolTip());
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JFrame frame = new JFrame();
        frame.add(tabbedPane);
        frame.pack();

        frame.setSize(500, 500);
        GUIScreenPosition.SetScreenLocation(ScreenPosition.Center, frame);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}