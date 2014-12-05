/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vuolleko
 */
public class EvenOutJUnitStatusFileTest {

    Person person;
    Person person2;
    Person person3;
    List<Person> participants = new ArrayList<>();
    CostGroup group;
    CostGroup group2;

    public EvenOutJUnitStatusFileTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
        person = new Person("Test Person 1");
        person2 = new Person("Test Person 2");
        person3 = new Person("Test Person 3");

        participants.add(person);
        participants.add(person2);
        participants.add(person3);

        group = new CostGroup("Cost Group 1");
        group.addPerson(person);
        group.addPerson(person2);
        group.addPerson(person3);
    }

    @After
    public void tearDown() {
        group.finalizeGroup();
    }

    @Test
    public void writeNReadPersons() throws IOException {
        List<Person> persons = group.getPersons();
        group = new CostGroup("Cost Group 1");

        assertEquals(group.getPersons(), persons);
    }

    @Test
    public void writeNReadPayments() throws IOException {
        group.addPayment(new Payment("Event 1", 10, participants, person));
        group.addPayment(new Payment("Event 2", 21.5, participants, person));
        group.addPayment(new Payment("Event 3", 10.2, participants, person));
        group.addPayment(new Payment("Event 4", 3.2, participants, person2));
        group.addPayment(new Payment("Event 5", 10, participants, person));

        List<Long> paid = new ArrayList<>();
        List<Long> cost = new ArrayList<>();
        for (Person p : group.getPersons()) {
            paid.add(group.getPaid(p));
            cost.add(group.getCost(p));
        }
        
        // person objects have new ids
        group = new CostGroup("Cost Group 1");
        List<Long> paid2 = new ArrayList<>();
        List<Long> cost2 = new ArrayList<>();
        for (Person p : group.getPersons()) {
            paid2.add(group.getPaid(p));
            cost2.add(group.getCost(p));
        }        
        
        assertEquals(paid, paid2);
        assertEquals(cost, cost2);
    }
}
