/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.gui;

import com.munfirma.evenout.fileop.UserListFile;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author vuolleko
 */
public class LoginView implements ActionListener {

    JFrame frame;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JButton registerButton;

    public LoginView(JFrame frame) {
//        JFrame frame = new JFrame("Login to EvenOut");
        this.frame = frame;
        this.frame.setSize(250, 130);

        JPanel panel = new JPanel();
        this.frame.add(panel);
        initLogin(panel);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        loginButton.addActionListener(this);
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        panel.add(registerButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        
        if (username.isEmpty() || password.length == 0) {
            return;
        }

        try {

            if (e.getSource() == loginButton) {
                if (UserListFile.verifyUser(username, password)) {
                    new MainGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            (Component) loginButton, "Wrong username/password!");
                }

            } else if (e.getSource() == registerButton) {
                if (UserListFile.addUser(username, password)) {
                    JOptionPane.showMessageDialog(
                            (Component) registerButton, "User created!");
                } else {
                    JOptionPane.showMessageDialog(
                            (Component) registerButton, "Username reserved!");
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
