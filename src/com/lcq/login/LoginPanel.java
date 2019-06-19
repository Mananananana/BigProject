package com.lcq.login;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginPanel extends JPanel {
    public int width, height;
    private Image image;
    public LoginPanel(){
        super();
        URL url = getClass().getResource("res/login_user.png");
        image = new ImageIcon(url).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
