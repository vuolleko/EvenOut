/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.munfirma.evenout.common;

import com.munfirma.evenout.common.Person;
import static java.lang.Double.min;
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
    
    public String balance() {
        String output = "";
        Map<Person, Double> paidLess = new HashMap<>();
        Map<Person, Double> paidMore = new HashMap<>();
        
        for (Person p : this.persons) {
            double debt = this.getCost(p) - this.getPaid(p);
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
            double balanceMinus = paidLess.get(pLess);
            
            for (Person pMore : paidMore.keySet()) {
                double balancePlus = paidMore.get(pMore);
                while (balancePlus > 0.0099) {
                    if (balanceMinus <= 0) {
                        pLess = paidLessItr.next();
                        balanceMinus = paidLess.get(pLess);
                    }
                    
                    double amount = min(balanceMinus, balancePlus);
                    balanceMinus -= amount;
                    balancePlus -= amount;
//                    output += pLess + " pays " + amount + " euros to " + pMore + "\n";
                    System.out.println(pLess + " pays " + amount + " euros to " + pMore + "\n");
                    System.out.println(balancePlus);
                }
            }
        }
        
        return output;
    }
}
