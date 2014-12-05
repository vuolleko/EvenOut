/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.common;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Instances of this class represent single Payment events.
 *
 * @author vuolleko
 */
public class Payment {

    private final String description;
    private final long cost;
    private final List<Person> participants;
    private final Person payer;

    public static final double SCALE = 100000.;  // used for conversion of money
    public static final DecimalFormat DF = new DecimalFormat("#.00");

    public Payment(String description, double cost, List participants, Person payer) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost must not be negative!");
        }
        if (description.length() < 1) {
            throw new IllegalArgumentException("No description!");
        }
        if (participants.size() < 1) {
            throw new IllegalArgumentException("No participants!");
        }

        this.description = description;
        this.cost = (long) (cost * SCALE);  // conversion to 0.1 cents!
        this.participants = participants;
        this.payer = payer;
    }

    public String getDescription() {
        return this.description;
    }

    public double getCost() {
        return (double) (this.cost / SCALE);
    }

    public Person getPayer() {
        return this.payer;
    }

    public List<Person> getParticipants() {
        return this.participants;
    }

    /**
     *
     * @param person
     * @return cost of this event for person
     */
    public long getCostFor(Person person) {
        if (this.participants.contains(person)) {
            return this.cost / this.participants.size();
        } else {
            return 0;
        }
    }

    /**
     *
     * @param person
     * @return amount paid by person for this event
     */
    public long getPaidBy(Person person) {
        if (this.payer == person) {
            return this.cost;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        String output = this.description;
        output += " " + DF.format(this.cost / SCALE) + " euros";
        output += ", paid by " + this.payer;
        output += " (Participants: " + this.participants + ")";
        return output;
    }
}
