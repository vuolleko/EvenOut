/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.munfirma.evenout.common;

import com.munfirma.evenout.common.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vuolleko
 */
public class CostGroup {
    private final String groupName;
    private List<Person> persons;
    private List<Payment> payments;
    
    public CostGroup(String groupName) {
        this.groupName = groupName;
        this.persons = new ArrayList<Person>();
        this.payments = new ArrayList<Payment>();
    }
    
    public void addPerson(Person person) {
        if (!this.persons.contains(person)) {
            this.persons.add(person);
        }
    }
    
    public List<Person> getPersons() {
        return this.persons;
    }
    
    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }
    
    // total cost for person
    public double getCost(Person person) {
        double cost = 0.;
        for (Payment p : this.payments) {
            cost += p.getCost(person);
        }
        return cost;
    }

    // total paid by person
    public double getPaid(Person person) {
        double paid = 0.;
        for (Payment p : this.payments) {
            paid += p.getPaid(person);
        }
        return paid;
    }
    
    @Override
    public String toString() {
        String output = this.groupName + ":\n";
        for (Payment p : this.payments) {
            output += p + "\n";
        }
        return output;
    }
}
