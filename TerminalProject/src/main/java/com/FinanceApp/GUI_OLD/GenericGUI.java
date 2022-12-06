package com.FinanceApp.GUI_OLD;

import com.FinanceApp.GUIExtensions.GUIScreenPosition;
import com.FinanceApp.GUIExtensions.ScreenPosition;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static com.FinanceApp.GUI_OLD.GUIManager.COMMANDHANDLER;

public class GenericGUI extends JFrame {
    protected JPanel panel = new JPanel();
    protected JLabel titleLabel = new JLabel();
    protected JTextPane textPane = new JTextPane();
    protected JButton closeButton = new JButton();
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

    private void CreateCoreGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        closeButton.setText("Close");
        closeButton.addActionListener(e -> dispose());

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

    protected void CreateTextPanel(String content) {
        textPane.setText(content);
        textPane.setEditable(false);

        textPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        panel.add(textPane);

        SetDarkView();
    }

    protected void SetDarkView(){
        // #F54B64 -> #F78361 Primary 'Rose'
        // #242a38 Secondary
        // #4E586E DarkGrey
        // #FFFFFF White

        getContentPane().setBackground(Color.decode("#4E586E"));

        textPane.setBackground(Color.decode("#4E586E"));
        panel.setBackground(Color.decode("#242a38"));

        setJTextPaneFont(textPane, Color.decode("#FFFFFF"));
    }

    public void setJTextPaneFont(JTextPane jtp, Color c, int from, int to) {
        // Start with the current input attributes for the JTextPane. This
        // should ensure that we do not wipe out any existing attributes
        // (such as alignment or other paragraph attributes) currently
        // set on the text area.

        StyleContext sc = StyleContext.getDefaultStyleContext();

        // MutableAttributeSet attrs = jtp.getInputAttributes();

        AttributeSet attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        // Set the font color
        //StyleConstants.setForeground(attrs, c);

        // Retrieve the pane's document object
        StyledDocument doc = jtp.getStyledDocument();
        // System.out.println(doc.getLength());

        // Replace the style for the entire document. We exceed the length
        // of the document by 1 so that text entered at the end of the
        // document uses the attributes.
        doc.setCharacterAttributes(from, to, attrs, true);
    }

    public void setJTextPaneFont(JTextPane jtp, Color c){
        setJTextPaneFont(jtp, c, 0, jtp.getDocument().getLength());
    }
}