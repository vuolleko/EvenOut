/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.gui;

import com.munfirma.evenout.common.CostGroup;
import com.munfirma.evenout.common.Payment;
import com.munfirma.evenout.common.Person;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author vuolleko
 */
public class MainGUI extends JFrame implements ActionListener {

    private JPanel newPaymentPanel;
    private JPanel paymentsPanel;
    private JScrollPane scrollPane;
    private JButton newPaymentButton;
    private JTextField descriptionField;
    private JTextField costField;
    private List<Person> participants;
    private CostGroup costGroup;

    public MainGUI() {
        initGUI();

    }

    private void initGUI() {
        paymentsPanel = new JPanel();
        paymentsPanel.setLayout(new BoxLayout(paymentsPanel, BoxLayout.Y_AXIS));
        newPaymentPanel = new JPanel();
        newPaymentPanel.setLayout(new BoxLayout(newPaymentPanel, BoxLayout.X_AXIS));
        paymentsPanel.add(newPaymentPanel);
        
        descriptionField = new JTextField();
        newPaymentPanel.add(descriptionField);
        costField = new JTextField();
        newPaymentPanel.add(costField);
        
        newPaymentButton = new JButton("Add new payment");
        newPaymentButton.addActionListener(this);
        newPaymentPanel.add(newPaymentButton);
        
        scrollPane = new JScrollPane(paymentsPanel,
                                     JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                     JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        add(scrollPane);
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Person person = new Person("Test Person 1", "pw1");
        Person person2 = new Person("Test Person 2", "pw1");
        Person person3 = new Person("Test Person 3", "pw1");

        participants = new ArrayList<>();
        participants.add(person);
        participants.add(person2);
        participants.add(person3);

        costGroup = new CostGroup("Cost Group 1");
        costGroup.addPerson(person);
        costGroup.addPerson(person2);
        costGroup.addPerson(person3);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI gui = new MainGUI();
                gui.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String description = descriptionField.getText();
        double cost = Double.parseDouble(costField.getText());
        Payment payment = new Payment(description, cost, participants, participants.get(0));
        JLabel paymentLabel = new JLabel(payment.toString());
        this.paymentsPanel.add(paymentLabel);
    }

}
