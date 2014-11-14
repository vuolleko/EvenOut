/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.gui;

import com.munfirma.evenout.common.CostGroup;
import com.munfirma.evenout.common.Payment;
import com.munfirma.evenout.common.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vuolleko
 */
public class main {
    
    public static void main(String[] args) {
        Person person = new Person("Test Person 1", "pw1");
        Person person2 = new Person("Test Person 2", "pw1");
        Person person3 = new Person("Test Person 3", "pw1");

        List<Person> participants = new ArrayList<>();
        participants.add(person);
        participants.add(person2);
        participants.add(person3);

        CostGroup group = new CostGroup("Cost Group 1");
        group.addPerson(person);
        group.addPerson(person2);
        group.addPerson(person3);

        group.addPayment(new Payment("Event 1", 10, participants, person));
        group.addPayment(new Payment("Event 2", 21.5, participants, person));
        group.addPayment(new Payment("Event 3", 10.2, participants, person));
        group.addPayment(new Payment("Event 4", 3.2, participants, person2));
        group.addPayment(new Payment("Event 5", 10, participants, person));
        
        System.out.println(group);
        
        group.balance();
        
        for (Person p : participants) {
            System.out.println(p.getDebt());
            System.out.println(p.getCredit());
        }
    }
    
}