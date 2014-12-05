/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author vuolleko
 */
public class UserListFile {

    private static final String PATH = "data/";
    private static final String USERSFILE = PATH + "users.txt";

    /**
     *
     * @param username
     * @return whether username exists in the user list file.
     * @throws FileNotFoundException
     */
    public static boolean userExists(String username) throws FileNotFoundException {
        List<String> userList = new ArrayList<>();
        try (Scanner input = new Scanner(new FileReader(USERSFILE))) {
            while (input.hasNext()) {
                String line = input.nextLine();
                userList.add(line.substring(0, line.indexOf(':')));
            }
        }
        return userList.contains(username);
    }

    /**
     * Adds a new user to the system.
     *
     * @param username
     * @param password
     * @return whether successful
     * @throws IOException
     */
    public static boolean addUser(String username, String password) throws IOException {
        if (!userExists(username)) {
            try (Writer writer = new FileWriter(USERSFILE, true)) {
                writer.write(username + ":" + password + "\n");
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the username has given the correct password.
     *
     * @param username
     * @param passwordTry
     * @return whether password correct
     * @throws FileNotFoundException
     */
    public static boolean verifyUser(String username, String passwordTry) throws FileNotFoundException {
        if (userExists(username)) {
            try (Scanner input = new Scanner(new FileReader(USERSFILE))) {
                while (input.hasNext()) {
                    String line = input.nextLine();
                    int splitind = line.indexOf(':');
                    if (username.equals(line.substring(0, splitind))) {
                        if (passwordTry.equals(line.substring(splitind + 1))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
