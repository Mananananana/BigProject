package com.lcq;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

import com.lcq.login.LoginDialog;

public class MainFrame extends JFrame {
    private ToolBar toolBar;
    private JDesktopPane desktopPane;
    private JPanel statePanel;

    public JLabel state_label;
    public static JLabel czy_label;

    public MainFrame(){
        super("采购管理系统");
        Container container = getContentPane();
        container.add(getToolBar(), BorderLayout.NORTH);
        container.add(getDesktopPane(), BorderLayout.CENTER);
        container.add(getStatePanel(), BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    public ToolBar getToolBar() {
        if(toolBar == null) {
            toolBar = new ToolBar(getDesktopPane(), getState_label());
            //toolBar = new ToolBar();
            toolBar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return toolBar;
    }

    public JPanel getStatePanel() {
        if(statePanel == null){
            statePanel = new JPanel(new BorderLayout());
            statePanel.add(getState_label(), BorderLayout.WEST);
            statePanel.add(getCzy_label(), BorderLayout.EAST);
            statePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
        return statePanel;
    }

    public JDesktopPane getDesktopPane() {
        if(desktopPane == null){
            desktopPane = new JDesktopPane();
        }
        return desktopPane;
    }

    public JLabel getState_label() {
        if(state_label == null){
            state_label = new JLabel("当前没有选择窗体");
        }
        return state_label;
    }

    public static JLabel getCzy_label() {
        if (czy_label == null) {
            czy_label = new JLabel("无");
        }
        return czy_label;
    }

    public static void main(String[] args) {
        new LoginDialog();
    }
}
