package com.FinanceApp.GUIExtensions;

import javax.swing.*;
import java.awt.*;

public class GUIScreenPosition {

    public static void SetScreenLocation(ScreenPosition pos, JFrame GUI) {
        switch (pos) {
            case Top:
                NotImplementedYet();
                break;
            case Center:
                CenterGUI(GUI);
                break;
            case Bottom:
                NotImplementedYet();
                break;
            case Left:
                NotImplementedYet();
                break;
            case Right:
                NotImplementedYet();
                break;
        }
    }

    private static void CenterGUI(JFrame GUI) {
        // Center The Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xLocation = screenSize.width / 2 - GUI.getWidth() / 2;
        int yLocation = screenSize.height / 2 - GUI.getHeight() / 2;
        GUI.setLocation(xLocation, yLocation);
    }

    private static void NotImplementedYet() {
        System.out.println("Not Implemented Yet");
    }
}
