/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.munfirma.evenout.common;

/**
 *
 * @author vuolleko
 */
public class Person {
    private final String name;
    private final String password;
    
    public Person(String name, String password) {
        this.name = name;
        this.password = password;
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
    
}
