/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.fileop;

import com.munfirma.evenout.fileop.UserListFile;
import java.io.IOException;
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
public class EvenOutJUnitUserListFileTest {
    
    String name;
    char[] password;

    public EvenOutJUnitUserListFileTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        name = "TP1";
        password = "pass1".toCharArray();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addNExistUserTest() throws IOException {
        UserListFile.addUser(name, password);
        assertThat(UserListFile.userExists(name), is(true));
    }

    @Test
    public void verifyPasswordTest() throws IOException {
        char[] wrongPwd = "pass2".toCharArray();
        assertThat(UserListFile.verifyUser(name, password), is(true));
        assertThat(UserListFile.verifyUser(name, wrongPwd), is(false));
    }
    
    @Test
    public void notExistUser() throws IOException {
        String name2 = "NotMe";
        assertThat(UserListFile.userExists(name2), is(false));
    }
}
