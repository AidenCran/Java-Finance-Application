package com.FinanceApp.Tabs;

import com.FinanceApp.GUIExtensions.GUIScreenPosition;
import com.FinanceApp.GUIExtensions.ScreenPosition;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MainWindow {
    ArrayList<BaseTab> Tabs = new ArrayList<>();

    public MainWindow() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create Tabs
        BaseTab OverviewTab = new OverviewTab();
        BaseTab GraphTab = new GraphTab();
        BaseTab TaxWithheldTab = new TaxCalcTab();
        BaseTab StatRange = new DateRangeStatTab();

        // Compile Tabs
        Tabs.add(OverviewTab);
        Tabs.add(GraphTab);
        Tabs.add(TaxWithheldTab);
        Tabs.add(StatRange);

        SetTabs(tabbedPane);

        // Create Frame
        JFrame frame = new JFrame();
        frame.add(tabbedPane);

        frame.pack();

        GUIScreenPosition.SetScreenLocation(ScreenPosition.Center, frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setResizable(true);
        frame.setVisible(true);
    }

    void SetTabs(JTabbedPane tabPane) {
        for (int i = 0; i < Tabs.size(); i++) {
            BaseTab tab = Tabs.get(i);

            // Add Tab
            tabPane.addTab(tab.Title(), tab.Icon(), tab, tab.ToolTip());

            // Set Tab Background
            tabPane.setBackgroundAt(i, tab.TabColour());

            // Set Panel Background
            tab.setBackground(Color.decode("#E0DBD3"));
        }

        // Set Tab Hotkeys
        tabPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabPane.setMnemonicAt(2, KeyEvent.VK_3);
        tabPane.setMnemonicAt(3, KeyEvent.VK_4);

        tabPane.addChangeListener(e -> {
            Tabs.get(tabPane.getSelectedIndex()).OnTabSelection();
        });
    }
}