package com.munfirma.evenout.common;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static com.munfirma.evenout.common.Payment.SCALE;
import java.io.IOException;
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
    List<Person> participants = new ArrayList<>();
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

        group.addPayment(new Payment("Event 1", 10, participants, person));
        group.addPayment(new Payment("Event 2", 21.5, participants, person));
        group.addPayment(new Payment("Event 3", 10.2, participants, person));
        group.addPayment(new Payment("Event 4", 3.2, participants, person2));
        group.addPayment(new Payment("Event 5", 10, participants, person));
    }

    @After
    public void tearDown() {
        group.finalizeGroup();
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
    public void balanceCreditOfParticipant() {
        group.finalizeGroup();
        assertThat(round(person.getBalance(person2) * 100 / SCALE), is((long) 1510));
        assertThat(round(person.getBalance(person3) * 100 / SCALE), is((long) 1830));
        assertThat(round(person2.getBalance(person) * 100 / SCALE), is((long) -1510));
        assertThat(person3.getBalance(person2), is((long) 0));
    }

    @Test
    public void noNewEventsAfterGroupFinalized() throws IOException {
        group.finalizeGroup();
        assertThat(group.addPayment(new Payment("Event x", 10, participants, person)), is(false));
    }

    @Test
    public void personReferenceTest() {
        assertThat(group.getPersonByName(person.getName()), is(person));
    }
}
