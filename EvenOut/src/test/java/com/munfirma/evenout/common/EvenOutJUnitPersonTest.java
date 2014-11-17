/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
public class EvenOutJUnitPersonTest {
    
    Person person;

    public EvenOutJUnitPersonTest() {
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
    public void testNoDebt() {
        assertThat(person.getBalance(new Person("TP2", "pwd2")), is((long)0));
    }
    
    @Test
    public void testDebt() {
        Person person2 = new Person("TestPerson 2", "pwd2");
        person.addCredit(person2, 1000);
        person.addDebt(person2, 3000);
        assertThat(person.getBalance(person2), is((long) (1000-3000)));
    }
}
