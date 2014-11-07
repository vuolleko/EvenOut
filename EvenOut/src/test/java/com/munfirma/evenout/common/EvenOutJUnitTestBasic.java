package com.munfirma.evenout.common;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.munfirma.evenout.common.Payment;
import com.munfirma.evenout.common.Person;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
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
public class EvenOutJUnitTestBasic {

    Person person;

    public EvenOutJUnitTestBasic() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        String name = "TestPerson";
        String password = "passwort123";
        person = new Person(name, password);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void newPersonVerifyName() {
        String name = "TestPerson";
        assertThat(person.getName(), is(name));
        String otherName = "TestPerson2";
        assertThat(person.getName(), is(not(otherName)));
    }

    @Test
    public void newPersonVerifyPassword() {
        String correctPassword = "passwort123";
        assertThat(person.verifyPassword(correctPassword), is(true));
        String wrongPassword = "someText";
        assertThat(person.verifyPassword(wrongPassword), is(false));
    }
    
    @Test
    public void newPayment1Participant() {
        List<Person> participants = new ArrayList<Person>();
        participants.add(person);
        String description = "Test case";
        double cost = 15.3;
        Payment payment = new Payment(description, cost, participants, person);
        String output = description + " " + cost + " euros, paid by " + person + "(Participants: " + participants + ")";
        assertThat(payment.toString(), is(output));
    }
    
    @Test
    public void newPayment3Participants() {
        List<Person> participants = new ArrayList<Person>();
        participants.add(person);
        participants.add(new Person("Test person 2", "pw2"));
        participants.add(new Person("Test person 3", "pw3"));
        String description = "Test case";
        double cost = 15.3;
        Payment payment = new Payment(description, cost, participants, person);
        String output = description + " " + cost + " euros, paid by " + person + "(Participants: " + participants + ")";
        assertThat(payment.toString(), is(output));        
    }
    
    @Test
    public void newPaymentCostForParticipant() {
        List<Person> participants = new ArrayList<Person>();
        participants.add(person);
        participants.add(new Person("Test person 2", "pw2"));
        participants.add(new Person("Test person 3", "pw3"));
        String description = "Test case";
        double cost = 15.3;
        Payment payment = new Payment(description, cost, participants, person);
        assertThat((int)(100*payment.getCost(person)), is(equalTo(510)));
    }

    @Test
    public void newPaymentCostForNonParticipant() {
        List<Person> participants = new ArrayList<Person>();
        participants.add(person);
        participants.add(new Person("Test person 2", "pw2"));
        Person person3 = new Person("Test person 3", "pw3");
        String description = "Test case";
        double cost = 15.3;
        Payment payment = new Payment(description, cost, participants, person);
        assertThat((int)payment.getCost(person3), is(equalTo(0)));
    }

    @Test
    public void newPaymentAmountParticipantPaid() {
        List<Person> participants = new ArrayList<Person>();
        participants.add(person);
        participants.add(new Person("Test person 2", "pw2"));
        participants.add(new Person("Test person 3", "pw3"));
        String description = "Test case";
        double cost = 15.3;
        Payment payment = new Payment(description, cost, participants, person);
        assertThat((int)(100*payment.getPaid(person)), is(equalTo(1530)));
    }

    @Test
    public void newPaymentAmountParticipantPaid0() {
        List<Person> participants = new ArrayList<Person>();
        participants.add(person);
        participants.add(new Person("Test person 2", "pw2"));
        Person person3 = new Person("Test person 3", "pw3");
        participants.add(person3);
        String description = "Test case";
        double cost = 15.3;
        Payment payment = new Payment(description, cost, participants, person);
        assertThat((int)(100*payment.getPaid(person3)), is(equalTo(0)));
    }

    @Test
    public void newPaymentAmountNonParticipantPaid() {
        List<Person> participants = new ArrayList<Person>();
        participants.add(person);
        participants.add(new Person("Test person 2", "pw2"));
        Person person3 = new Person("Test person 3", "pw3");
        String description = "Test case";
        double cost = 15.3;
        Payment payment = new Payment(description, cost, participants, person3);
        assertThat((int)(100*payment.getPaid(person3)), is(equalTo(1530)));
    }
    
}
