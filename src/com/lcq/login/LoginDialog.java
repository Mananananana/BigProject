package com.lcq.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import com.lcq.MainFrame;
import com.lcq.dao.Dao;

public class LoginDialog extends JFrame {
    private JLabel user_name_label;
    private JTextField user_name_field;
    private JLabel password_label;
    private JPasswordField password_field;
    private JButton loginButton;
    private JButton exitButton;

    private MainFrame frame;

    public JLabel getUser_name_label() {
        if(user_name_label == null){
            user_name_label = new JLabel("用户名:");
            user_name_label.setBounds(new Rectangle(95, 69, 65, 22));
        }
        return user_name_label;
    }

    public JTextField getUser_name_field() {
        if(user_name_field == null){
            user_name_field = new JTextField();
            user_name_field.setBounds(new Rectangle(143, 69, 125, 22));
        }
        return user_name_field;
    }

    public JLabel getPassword_label() {
        if(password_label == null){
            password_label = new JLabel("密码:");
            password_label.setBounds(new Rectangle(100, 93, 43, 22));
        }
        return password_label;
    }

    public JPasswordField getPassword_field() {
        if(password_field == null){
            password_field = new JPasswordField();
            password_field.setBounds(new Rectangle(143, 93, 125, 22));
            password_field.setEchoChar('*');
            password_field.addKeyListener(new java.awt.event.KeyAdapter(){
                @Override
                public void keyTyped(KeyEvent e) {
                    if(e.getKeyChar() == '\n'){
                        loginButton.doClick();
                    }
                }
            });
        }
        return password_field;
    }

    public JButton getLoginButton() {
        if(loginButton == null){
            loginButton = new JButton("登录");
            loginButton.setBounds(new Rectangle(105, 160, 60, 30));
            loginButton.addActionListener(e->{
                try {
                    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    //SwingUtilities.updateComponentTreeUI(frame);
                    String userName = user_name_field.getText();
                    String password = new String(password_field.getPassword());
                    if(!Dao.checkLogin(userName, password)){
                        JOptionPane.showMessageDialog(LoginDialog.this, "用户名或密码错误！", "登录失败！", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        System.out.println("登陆成功！");
                        this.setVisible(false);
                        MainFrame.czy_label.setText(user_name_field.getText());
                        frame.setVisible(true);
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            });
        }
        return loginButton;
    }

    public JButton getExitButton() {
        if(exitButton == null){
            exitButton = new JButton("退出");
            exitButton.setBounds(new Rectangle(185, 160, 60, 30));
            exitButton.addActionListener(e->{
                System.exit(0);
            });
        }
        return exitButton;
    }

    public LoginDialog(){
        super("系统登录");
        frame = new MainFrame();
        Container container  = getContentPane();
        LoginPanel panel = new LoginPanel();
        panel.setLayout(null);
        panel.add(getUser_name_label());
        panel.add(getUser_name_field());
        panel.add(getPassword_label());
        panel.add(getPassword_field());
        panel.add(getLoginButton());
        panel.add(getExitButton());
        container.add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginDialog();
    }
}
