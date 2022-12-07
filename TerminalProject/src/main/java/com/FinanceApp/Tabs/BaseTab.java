package com.FinanceApp.Tabs;

import javax.swing.*;
import java.awt.*;

public abstract class BaseTab extends JPanel {
    public abstract void OnTabSelection();
    public abstract String Title();
    public abstract Icon Icon();
    public abstract String ToolTip();
    public abstract Color TabColour();
}