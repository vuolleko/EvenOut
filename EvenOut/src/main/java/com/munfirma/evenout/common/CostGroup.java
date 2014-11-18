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
 * Instances of this class represent groups of related Payment events.
 *
 * @author vuolleko
 */
public class CostGroup {

    private boolean finalized;
    private final String groupName;
    private final List<Person> persons;
    private final List<Payment> payments;

    public CostGroup(String groupName) {
        this.finalized = false;
        this.groupName = groupName;
        this.persons = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    /**
     * Add a Person to CostGroup as a participant.
     *
     * @param person
     * @return whether successful
     */
    public boolean addPerson(Person person) {
        if (this.persons.contains(person)) {
            return false;
        }
        this.persons.add(person);
        return true;
    }

    /**
     *
     * @return List of participants in CostGroup.
     */
    public List<Person> getPersons() {
        return this.persons;
    }

    /**
     * Add a Payment event to CostGroup.
     *
     * @param payment
     * @return whether successful
     */
    public boolean addPayment(Payment payment) {
        if (this.finalized) {
            //System.out.println("Already finalized!");
            return false;
        }
        this.payments.add(payment);
        return true;
    }

    /**
     * Total cost of CostGroup for person.
     *
     * @param person
     * @return total cost for person
     */
    public long getCost(Person person) {
        long cost = 0;
        for (Payment p : this.payments) {
            cost += p.getCost(person);
        }
        return cost;
    }

    /**
     * Total paid by person for this CostGroup.
     *
     * @param person
     * @return total paid
     */
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

    /**
     * Finalize the CostGroup: calculate the final debt status between
     * participants.
     *
     * @return Whether successful.
     */
    public boolean finalizeGroup() {
        if (this.finalized) {
            return false;  // the method has already been run!
        }

        Map<Person, Long> paidLess = new HashMap<>();
        Map<Person, Long> paidMore = new HashMap<>();

        // first split the list of participants to those who paid more
        // than their cost, and those who paid less
        for (Person p : this.persons) {
            long debt = this.getCost(p) - this.getPaid(p);
            if (debt > 0) {
                paidLess.put(p, debt);
            } else if (debt < 0) {
                paidMore.put(p, -debt);
            }
        }

        // calculates debts and credits so that the number of transactions
        // is (almost) minimal
        if (paidLess.size() > 0) {
            Iterator<Person> paidLessItr = paidLess.keySet().iterator();
            Person pLess = paidLessItr.next();
            long debt = paidLess.get(pLess);

            for (Person pMore : paidMore.keySet()) {
                long credit = paidMore.get(pMore);
                while (credit > 0.01 * SCALE) {  // allows an error of 1 cent
                    if (debt <= 0) {
                        if (!paidLessItr.hasNext()) {
                            System.out.println(pMore + " paid " + DF.format(credit / SCALE)
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
        this.finalized = true;
        return true;
    }
}
