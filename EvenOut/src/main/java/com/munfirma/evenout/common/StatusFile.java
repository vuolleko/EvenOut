/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author vuolleko
 */
public class StatusFile {

    private static final String PATH = "data/";

    private final CostGroup costGroup;
    private final String personsFilename;
    private final String paymentsFilename;

    public StatusFile(CostGroup costGroup) {
        this.costGroup = costGroup;
        String groupName = costGroup.getGroupName();
        this.personsFilename = PATH + groupName + ".persons";
        this.paymentsFilename = PATH + groupName + ".payments";
    }

    /**
     * Check if data for the CostGroup exists and read it.
     *
     * @throws IOException
     */
    public void checkOld() throws IOException {
        if (new File(this.personsFilename).exists()) {
            this.readOldPersons();
            
            if (new File(this.paymentsFilename).exists()) {
                this.readOldPayments();
            }
        }
    }

    /**
     * Append the person's name to file.
     *
     * @param person
     * @throws IOException
     */
    public void addPerson(Person person) throws IOException {
        try (Writer writer = new FileWriter(this.personsFilename, true)) {
            writer.write(person.getName() + "\n");
        }
    }

    /**
     * Append the payment to file.
     *
     * @param payment
     * @throws IOException
     */
    public void addPayment(Payment payment) throws IOException {
        try (Writer writer = new FileWriter(this.paymentsFilename, true)) {
            writer.write(payment.getDescription() + "\n");
            writer.write(payment.getCost() + "\n");
            writer.write(payment.getPayer().getName() + "\n");
            for (Person p : payment.getParticipants()) {
                writer.write(p.getName() + ";");
            }
            writer.write("\n");
        }
    }

    /**
     * Deletes person and payment lists.
     */
    public void deleteFiles() {
        new File(this.personsFilename).delete();
        new File(this.paymentsFilename).delete();
    }

    /**
     * Reads a past list of persons from file and adds them to the CostGroup.
     */
    private void readOldPersons() throws FileNotFoundException, IOException {
        String inputFilename = renameOldFile(this.personsFilename);

        try (Scanner input = new Scanner(new FileReader(inputFilename))) {
            while (input.hasNext()) {
                String name = input.nextLine();
                this.costGroup.addPerson(new Person(name));
            }
        }
    }

    /**
     * Reads a past list of payments from file and adds them to the CostGroup.
     */
    private void readOldPayments() throws FileNotFoundException, IOException {
        String inputFilename = renameOldFile(this.paymentsFilename);
        List<Person> persons = this.costGroup.getPersons();

        try (Scanner input = new Scanner(new FileReader(inputFilename))) {
            while (input.hasNext()) {
                String description = input.nextLine();
                double cost = Double.parseDouble(input.nextLine());
                Person payer = this.costGroup.getPersonByName(input.nextLine());
                List<Person> participants = new ArrayList<>();
                for (String name : input.nextLine().split(";")) {
                    participants.add(this.costGroup.getPersonByName(name));
                }
                this.costGroup.addPayment(
                        new Payment(description, cost, participants, payer));
            }
        }
    }

    /**
     * Renames a file.
     *
     * @param filename
     * @return new filename
     */
    private static String renameOldFile(String filename) {
        String newFilename = filename + ".old";
        File oldFile = new File(filename);
        oldFile.renameTo(new File(newFilename));
        oldFile.delete();
        return newFilename;
    }

}
