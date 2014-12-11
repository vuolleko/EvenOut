/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.gui;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author vuolleko
 */
public class LoginView {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JButton registerButton;
    
    public LoginView() {
        JFrame frame = new JFrame("Login to EvenOut");
        frame.setSize(300, 150);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        initLogin(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void initLogin(JPanel panel) {
        panel.setLayout(new GridLayout(3, 2));
        
        JLabel usernameLabel = new JLabel("Username");
        panel.add(usernameLabel);
        panel.add(new JLabel("Password"));
        
        usernameField = new JTextField(20);
        panel.add(usernameField);
        
        passwordField = new JPasswordField(20);
        panel.add(passwordField);
        
        loginButton = new JButton("Login");
        panel.add(loginButton);
        
        registerButton = new JButton("Register");
        panel.add(registerButton);
    }
    
}
