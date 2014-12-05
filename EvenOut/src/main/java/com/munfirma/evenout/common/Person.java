/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.common;

import static com.munfirma.evenout.common.Payment.DF;
import static com.munfirma.evenout.common.Payment.SCALE;
import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class represent both users in the EvenOut system
 * and potential participants in CostGroups.
 *
 * @author vuolleko
 */
public class Person {

    private final String name;
    private final Map<Person, Long> balance;

    public Person(String name) {
        this.name = name;
        this.balance = new HashMap<>();
    }
    
    /**
     * Check for person's identity based on unique names.
     * @param o
     * @return whether same persons
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            return this.name.equals(o.toString());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public String getName() {
        return this.name;
    }

    /**
     * Adds debt to another Person to the 
     * balance of current instance.
     * 
     * @param person Some other person
     * @param amount of debt to another person (positive)
     */
    public void addDebt(Person person, long amount) {
        if (this != person) {
            long debt = -amount;
            if (this.balance.containsKey(person)) {
                debt += this.balance.get(person);
            }
            this.balance.put(person, debt);
        }
    }

    /**
     * Adds credit from another Person to the 
     * balance of current instance.
     * 
     * @param person Some other person
     * @param amount of credit from another person (positive)
     */
    public void addCredit(Person person, long amount) {
        this.addDebt(person, -amount);
    }

    /**
     * 
     * @param person
     * @return Amount of debt to or credit from another person.
     */
    public long getBalance(Person person) {
        if (this.balance.containsKey(person)) {
            return this.balance.get(person);
        }
        return 0;
    }

    @Override
    public String toString() {
        return this.name;
    }
     
    /**
     * 
     * @return A string representation of the current person's balance.
     */
    public String balanceStr() {
        String output = this.name + " must";
        if (this.balance.size() > 0) {
            for (Person p : this.balance.keySet()) {
                long amount = this.balance.get(p);
                if (amount > 0) {
                    output += "\n    receive " + DF.format(amount / SCALE)
                            + " euros from " + p;
                } else {
                    output += "\n    pay " + DF.format(-amount / SCALE)
                            + " euros to " + p;
                }
            }
        } else {
            output += " has no debt or credit.";
        }
        return output;
    }

}
