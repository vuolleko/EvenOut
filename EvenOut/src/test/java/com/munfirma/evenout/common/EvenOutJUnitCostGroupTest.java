package com.munfirma.evenout.common;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static com.munfirma.evenout.common.Payment.SCALE;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.either;

/**
 *
 * @author vuolleko
 */
public class EvenOutJUnitCostGroupTest {

    Person person;
    Person person2;
    Person person3;
    CostGroup group;

    public EvenOutJUnitCostGroupTest() {
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
        person3 = new Person("Test Person 3", "pw1");

        List<Person> participants = new ArrayList<>();
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
        assertThat(round(group.getPaid(person) * 100 / SCALE), is(round(100 * (10 + 21.5 + 10.2 + 10))));
        assertThat(group.getPaid(person2), is((long) (SCALE * 3.20)));
        assertThat(group.getPaid(person3), is((long) 0));
    }

    @Test
    public void newGroupTotalCostOfParticipant() {
        assertThat(round(group.getCost(person) * 100 / SCALE), is(round(100 * (10 + 21.5 + 10.2 + 10 + 3.2) / 3)));
    }
    
    @Test
    public void balanceDebtOfParticipant() {
        group.balance();
        String output = "Test Person 1 has no debt.";
        assertThat(person.getDebt(), is(output));
    }

    @Test
    public void balanceCreditOfParticipant() {
        group.balance();
        String output = "Test Person 1 must get paid:\n"
                         + "    15.10 euros by Test Person 2\n"
                         + "    18.30 euros by Test Person 3";
        String output2 = "Test Person 1 must get paid:\n"
                          + "    18.30 euros by Test Person 3\n"
                          + "    15.10 euros by Test Person 2";
        System.out.println(person.getCredit());
        assertThat(person.getCredit(), anyOf(is(output),is(output2)));
    }

}
