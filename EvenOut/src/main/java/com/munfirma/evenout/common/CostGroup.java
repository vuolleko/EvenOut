/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.munfirma.evenout.common;

import static com.munfirma.evenout.common.Payment.DF;
import static com.munfirma.evenout.common.Payment.SCALE;
import static java.lang.Long.min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vuolleko
 */
public class CostGroup {
    private final String groupName;
    private final List<Person> persons;
    private final List<Payment> payments;
    
    public CostGroup(String groupName) {
        this.groupName = groupName;
        this.persons = new ArrayList<>();
        this.payments = new ArrayList<>();
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
    public long getCost(Person person) {
        long cost = 0;
        for (Payment p : this.payments) {
            cost += p.getCost(person);
        }
        return cost;
    }

    // total paid by person
    public long getPaid(Person person) {
        long paid = 0;
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
    
    public void balance() {
        Map<Person, Long> paidLess = new HashMap<>();
        Map<Person, Long> paidMore = new HashMap<>();
        
        for (Person p : this.persons) {
            long debt = this.getCost(p) - this.getPaid(p);
            if (debt > 0) {
                paidLess.put(p, debt);
            }
            else if (debt < 0) {
                paidMore.put(p, -debt);
            }
        }
        
        if (paidLess.size() > 0) {
            Iterator<Person> paidLessItr = paidLess.keySet().iterator();
            Person pLess = paidLessItr.next();
            long debt = paidLess.get(pLess);
            
            for (Person pMore : paidMore.keySet()) {
                long credit = paidMore.get(pMore);
                while (credit > 0.01 * SCALE) {  // allows an error of 1 cent
                    if (debt <= 0) {
                        if (!paidLessItr.hasNext()) {
                            System.out.println(pMore + " paid " + DF.format(credit/SCALE) 
                                      + " euros too much.\n");
                            break;
                        }
                        pLess = paidLessItr.next();
                        debt = paidLess.get(pLess);
                    }
                    
                    long amount = min(debt, credit);
                    debt -= amount;
                    credit -= amount;
                    pLess.addDebt(pMore, amount);
                    pMore.addCredit(pLess, amount);
                }
            }
        }        
    }
}
