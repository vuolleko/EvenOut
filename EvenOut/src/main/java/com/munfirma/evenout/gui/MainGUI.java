/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.gui;

import com.munfirma.evenout.common.CostGroup;
import com.munfirma.evenout.common.Payment;
import com.munfirma.evenout.common.Person;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author vuolleko
 */
public class MainGUI extends JFrame implements ActionListener {

    private JPanel paymentsPanel;
    private JScrollPane scrollPane;
    private JButton newPaymentButton;
    private JTextField descriptionField;
    private JTextField costField;
    private CostGroup costGroup;
    private ButtonGroup payerButtonGroup;
    private List<JCheckBox> participantCheckBoxList;

    public MainGUI() {
        initGUI();

    }

    private void initGUI() {
        paymentsPanel = new JPanel();
        paymentsPanel.setLayout(new BoxLayout(paymentsPanel, BoxLayout.Y_AXIS));
        paymentsPanel.add(newPaymentPanel());

        scrollPane = new JScrollPane(paymentsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private JPanel newPaymentPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridLayout(2, 5));

        Person person = new Person("Test Person 1", "pw1");
        Person person2 = new Person("Test Person 2", "pw1");
        Person person3 = new Person("Test Person 3", "pw1");

        costGroup = new CostGroup("Cost Group 1");
        costGroup.addPerson(person);
        costGroup.addPerson(person2);
        costGroup.addPerson(person3);

        newPanel.add(new JLabel("Description"));
        newPanel.add(new JLabel("Cost"));
        newPanel.add(new JLabel("Paid by"));
        newPanel.add(new JLabel("Participants"));
        newPanel.add(new JLabel());

        descriptionField = new JTextField();
        descriptionField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE,
                        descriptionField.getPreferredSize().height));
        newPanel.add(descriptionField);
        costField = new JTextField();
        costField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE,
                        descriptionField.getPreferredSize().height));
        newPanel.add(costField);

        payerButtonGroup = new ButtonGroup();
        JPanel payerButtonPanel = new JPanel();
        payerButtonPanel.setLayout(
                new BoxLayout(payerButtonPanel, BoxLayout.Y_AXIS));

        participantCheckBoxList = new ArrayList();
        JPanel participantCheckBoxPanel = new JPanel();
        participantCheckBoxPanel.setLayout(
                new BoxLayout(participantCheckBoxPanel, BoxLayout.Y_AXIS));

        int ii = 0;

        for (Person p : costGroup.getPersons()) {
            JRadioButton payerButton = new JRadioButton(p.getName());
            payerButton.setActionCommand("" + ii);  // button gives index to person
            payerButtonGroup.add(payerButton);
            payerButtonPanel.add(payerButton);

            JCheckBox participantCheckBox = new JCheckBox(p.getName());
            participantCheckBox.setActionCommand("" + ii);
            participantCheckBox.setSelected(true);
            participantCheckBoxList.add(participantCheckBox);
            participantCheckBoxPanel.add(participantCheckBox);

            ii++;
        }
        newPanel.add(payerButtonPanel);
        newPanel.add(participantCheckBoxPanel);

        newPaymentButton = new JButton("Add new payment");
        newPaymentButton.addActionListener(this);
        newPanel.add(newPaymentButton);

        return newPanel;
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
        List<Person> allPersons = costGroup.getPersons();
        List<Person> participants = new ArrayList();

        Person payer = allPersons.get(
                Integer.parseInt(
                        payerButtonGroup.getSelection().getActionCommand()));

        for (JCheckBox box : participantCheckBoxList) {
            if (box.isSelected()) {
                participants.add(allPersons.get(
                        Integer.parseInt(
                                box.getActionCommand())));
            }
        }

        Payment payment = new Payment(description, cost, participants, payer);
        JLabel paymentLabel = new JLabel(payment.toString());
        this.paymentsPanel.add(paymentLabel);
    }

}
