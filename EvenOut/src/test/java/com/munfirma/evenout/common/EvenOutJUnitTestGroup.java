package com.munfirma.evenout.common;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.munfirma.evenout.common.CostGroup;
import com.munfirma.evenout.common.Payment;
import com.munfirma.evenout.common.Person;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
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
public class EvenOutJUnitTestGroup {

    Person person;
    Person person2;
    Person person3;
    CostGroup group;

    public EvenOutJUnitTestGroup() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        person = new Person("Test Person 1", "pw1");
        person2 = new Person("Test Person 2", "pw1");
        person2 = new Person("Test Person 3", "pw1");

        List<Person> participants = new ArrayList<Person>();
        participants.add(person);
        participants.add(person2);
        participants.add(person3);

        group = new CostGroup("Cost Group 1");
        group.addPerson(person);
        group.addPerson(person2);
        group.addPerson(person3);

        group.addPayment(new Payment("Event 1", 10, participants, person));
        group.addPayment(new Payment("Event 2", 21.5, participants, person));
        group.addPayment(new Payment("Event 3", 10.2, participants, person));
        group.addPayment(new Payment("Event 4", 3.2, participants, person2));
        group.addPayment(new Payment("Event 5", 10, participants, person));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void newGroupTotalPaidByParticipant() {
        assertThat((int) (100 * group.getPaid(person)), is(1000 + 2150 + 1020 + 1000));
        assertThat((int) (100 * group.getPaid(person2)), is(320));
        assertThat((int) (100 * group.getPaid(person3)), is(0));
    }
    
    @Test
    public void newGroupTotalCostOfParticipant() {
        assertThat((int) (100*group.getCost(person)), is((1000 + 2150 + 1020 + 1000 + 320) / 3));
    }
}
