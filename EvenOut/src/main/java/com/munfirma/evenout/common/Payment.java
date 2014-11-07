/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.munfirma.evenout.common;

import com.munfirma.evenout.common.Person;
import java.util.List;

/**
 *
 * @author vuolleko
 */
public class Payment {
    private final String description;
    private final double cost;
    private final List<Person> participants;
    private final Person payer;
    
    public Payment(String description, double cost, List participants, Person payer) {
        //TODO add check for cost >0
        this.description = description;
        this.cost = cost;
        this.participants = participants;
        this.payer = payer;
    }
    
    // return the cost per participant
    public double getCost(Person person) {
        if (this.participants.contains(person)) {
            return this.cost / this.participants.size();
        }
        else {
            return 0;
        }
    }
    
    // how much a person has paid for this
    public double getPaid(Person person) {
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
        output += " " + this.cost + " euros";
        output += ", paid by " + this.payer;
        output += " (Participants: " + this.participants + ")";
        return output;
    }
}
