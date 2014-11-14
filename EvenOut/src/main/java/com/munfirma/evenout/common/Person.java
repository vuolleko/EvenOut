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
 *
 * @author vuolleko
 */
public class Person {

    private final String name;
    private final String password;
    private Map<Person, Long> debt;
    private Map<Person, Long> credit;

    public Person(String name, String password) {
        this.name = name;
        this.password = password;
        this.debt = new HashMap<>();
        this.credit = new HashMap<>();
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void addDebt(Person person, long amount) {
        //TODO allow same person, balance with debt
        this.debt.put(person, amount);
    }

    public void addCredit(Person person, long amount) {
        //TODO allow same person, balance with debt
        this.credit.put(person, amount);
    }

    public String getDebt() {
        String output = this.name;
        if (this.debt.size() > 0) {
            output += " must pay:";
            for (Person p : this.debt.keySet()) {
                output += "\n    " + DF.format(this.debt.get(p) / SCALE)
                        + " euros to " + p;
            }
        } else {
            output += " has no debt.";
        }
        return output;
    }

    public String getCredit() {
        String output = this.name;
        if (this.credit.size() > 0) {
            output += " must get paid:";
            for (Person p : this.credit.keySet()) {
                output += "\n    " + DF.format(this.credit.get(p) / SCALE)
                        + " euros by " + p;
            }
        } else {
            output += " has no credit.";
        }
        return output;
    }

}
