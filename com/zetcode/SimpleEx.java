package com.zetcode;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class SimpleEx extends JFrame {

    JLabel backgroundImgaeLabel;
    Image backgroundImage;
    public SimpleEx() {

        initUI();
    }

    private void initUI() {
        
        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SimpleEx ex = new SimpleEx();
            ex.setVisible(true);
        });
    }
}