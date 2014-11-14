/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.munfirma.evenout.common;

import java.text.DecimalFormat;
import java.util.List;

/**
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
        //TODO add check for cost >0
        this.description = description;
        this.cost = (long) (cost * SCALE);  // conversion to 0.1 cents!
        this.participants = participants;
        this.payer = payer;
    }
    
    // return the cost per participant
    public long getCost(Person person) {
        if (this.participants.contains(person)) {
            return this.cost / this.participants.size();
        }
        else {
            return 0;
        }
    }
    
    // how much a person has paid for this
    public long getPaid(Person person) {
        if (this.payer == person) {
            return this.cost;
        }
        else {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        String output = this.description;
        output += " " + DF.format(this.cost/SCALE) + " euros";
        output += ", paid by " + this.payer;
        output += " (Participants: " + this.participants + ")";
        return output;
    }
}
