/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munfirma.evenout.fileop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Static methods for dealing with user authentication and registration based on
 * a file.
 *
 * @author vuolleko
 */
public class UserListFile {

    /** Relative path to files. */
    private static final String PATH = "data/";
    
    /** File that lists users and their passwords in format USERNAME:PASSWORD */
    private static final String USERSFILE = PATH + "users.txt";

    /**
     *
     * @param username
     * @return true if username exists in the user list file
     * @throws FileNotFoundException
     */
    public static boolean userExists(String username) throws FileNotFoundException {
        if (new File(USERSFILE).exists()) {
            Scanner input = new Scanner(new FileReader(USERSFILE));
            while (input.hasNext()) {
                String line = input.nextLine();
                if (username.equals(line.substring(0, line.indexOf(':')))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a new user to the system.
     *
     * @param username
     * @param password
     * @return true if successful
     * @throws IOException
     */
    public static boolean addUser(String username, char[] password) throws IOException {
        if (!userExists(username)) {
            File file = new File(USERSFILE);
            file.getParentFile().mkdirs();
            try (Writer writer = new FileWriter(file, true)) {
                writer.write(username + ":" + new String(password) + "\n");
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
     * @return true if password correct
     * @throws FileNotFoundException
     */
    public static boolean verifyUser(String username, char[] passwordTry) throws FileNotFoundException {
        if (userExists(username)) {
            try (Scanner input = new Scanner(new FileReader(USERSFILE))) {
                while (input.hasNext()) {
                    String line = input.nextLine();
                    int splitind = line.indexOf(':');  // format: USERNAME:PASSWORD
                    if (username.equals(line.substring(0, splitind))) {
                        boolean verified;
                        char[] password = line.substring(splitind + 1).toCharArray();
                        if (passwordTry.length != password.length) {
                            verified = false;
                        } else {
                            verified = Arrays.equals(passwordTry, password);
                        }
                        // fill char arrays for increased security
                        Arrays.fill(password, '0');
                        Arrays.fill(passwordTry, '0');
                        return verified;
                    }
                }
            }
        }
        return false;
    }

}
