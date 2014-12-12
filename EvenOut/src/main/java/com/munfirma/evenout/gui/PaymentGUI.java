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
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The main GUI that handles payments and other events related to CostGroups.
 *
 * @author vuolleko
 */
public class PaymentGUI implements ActionListener {

    private final JFrame frame;
    private final Person user;
    private final CostGroup costGroup;

    private JPanel paymentsPanel;
    private JPanel newPaymentsPanel;
    private JTextArea outputTextArea;
    private JScrollPane scrollPane;
    private JButton newPaymentButton;
    private JButton finalizeButton;
    private JButton newParticipantButton;
    private JTextField descriptionField;
    private JFormattedTextField costField;
    private JTextField newParticipantField;
    private ButtonGroup payerButtonGroup;
    private List<JCheckBox> participantCheckBoxList;

    public PaymentGUI(JFrame frame, Person user, CostGroup costGroup) {
        this.frame = frame;
        this.user = user;
        this.costGroup = costGroup;
        try {
            this.costGroup.addPerson(user);
        } catch (IOException ex) {
            Logger.getLogger(PaymentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.frame.getContentPane().removeAll();
        initGUI();
    }

    private void initGUI() {
        paymentsPanel = new JPanel();
        paymentsPanel.setLayout(new BoxLayout(paymentsPanel, BoxLayout.Y_AXIS));
        newPaymentsPanel = newPaymentPanel();
        paymentsPanel.add(newPaymentsPanel);
        this.frame.add(paymentsPanel);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setRequestFocusEnabled(false);
        outputTextArea.setText(this.costGroup.toString());
        scrollPane = new JScrollPane(outputTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        paymentsPanel.add(scrollPane);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private JPanel newPaymentPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridLayout(2, 5));

        newPanel.add(new JLabel("Description"));
        newPanel.add(new JLabel("Cost"));
        newPanel.add(new JLabel("Paid by"));
        newPanel.add(new JLabel("Participants"));

        finalizeButton = new JButton("Finalize");
        finalizeButton.addActionListener(this);
        newPanel.add(finalizeButton);

        newParticipantField = new JTextField("New participant");
        newPanel.add(newParticipantField);

        descriptionField = new JTextField();
        descriptionField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE,
                        descriptionField.getPreferredSize().height));
        newPanel.add(descriptionField);
        costField = new JFormattedTextField(NumberFormat.getNumberInstance());
        costField.setValue(new Double(0));
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
            if (this.user.equals(p)) {
                payerButton.setSelected(true);
            }
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

        newParticipantButton = new JButton("Add new participant");
        newParticipantButton.addActionListener(this);
        newPanel.add(newParticipantButton);

        return newPanel;
    }

    /**
     * Handle button presses.
     * 
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newPaymentButton) {
            try {
                addNewPayment();
            } catch (IOException ex) {
                Logger.getLogger(PaymentGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == finalizeButton) {
            finalizeCostGroup();
        } else if (e.getSource() == newParticipantButton) {
            addNewParticipant();
        }
        this.frame.pack();
    }

    private void addNewPayment() throws IOException {
        String description = descriptionField.getText();
        if (description.isEmpty()) {
            description = " ";
        }
        double cost = ((Number) costField.getValue()).doubleValue();
        if (!(cost > 0)) {
            return;
        }
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
        if (this.costGroup.addPayment(payment)) {
            this.outputTextArea.append(payment.toString() + "\n");
        }
    }

    private void finalizeCostGroup() {
        this.costGroup.finalizeGroup();
        this.outputTextArea.append("------------------------------\n\n");

        for (Person p : this.costGroup.getPersons()) {
            this.outputTextArea.append(p.balanceStr() + "\n");
        }

        this.finalizeButton.setEnabled(false);
        this.newPaymentButton.setEnabled(false);
        this.newParticipantButton.setEnabled(false);
    }

    private void addNewParticipant() {
        String name = newParticipantField.getText();
        if (name.isEmpty() || name.equals("New participant")) {
            return;
        }
        try {
            this.costGroup.addPerson(new Person(name));
        } catch (IOException ex) {
            Logger.getLogger(PaymentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        new PaymentGUI(this.frame, this.user, this.costGroup);
    }

}
