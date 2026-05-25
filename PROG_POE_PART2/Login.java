/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author khumomoloantoa
 */
public class Login {
    
// Here we save the user details after they register so we can check later
    private String storedUsername;
    private String storedPassword;

    // This method checks if username is correct
    // Rule: must have an underscore and be no longer than 5 characters
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // This method checks if password meets all the rules
    // Rule: minimum 8 characters, 1 uppercase letter, 1 number and 1 special character
    public boolean checkPasswordComplexity(String password) {

        // First check if password is empty or too short
        if (password == null || password.length() < 8) {
            return false;
        }

        // Variables to keep track of what we find in the password
        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        // Go through every single character in the password one by one
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            // Mark what type of character it is
            if (Character.isUpperCase(ch)) hasUppercase = true;
            if (Character.isDigit(ch)) hasNumber = true;
            if (!Character.isLetterOrDigit(ch)) hasSpecial = true;
        }

        // Only return true if all 3 rules are satisfied
        return hasUppercase && hasNumber && hasSpecial;
    }

    // This method checks if the phone number is in the right format
    // Rule: must start with +27 and have exactly 9 digits after that
    public boolean checkCellPhoneNumber(String phone) {
        return phone != null && phone.matches("^\\+27\\d{9}$");
    }

    // This method registers the user, checks all details and saves them if correct
    public String registerUser(String username, String password, String phone) {

        // If username is wrong, send back error message
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        // If password is wrong, send back error message
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        // If phone number is wrong, send back error message
        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // If everything is good, save the user details so they can login later
        this.storedUsername = username;
        this.storedPassword = password;

        return "User successfully registered.";
    }

    // This method checks if the entered username and password match what we saved
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        // If no user is registered yet, return false
        if (storedUsername == null || storedPassword == null) {
            return false;
        }
        // Compare what user typed vs what we saved
        return storedUsername.equals(enteredUsername) && storedPassword.equals(enteredPassword);
    }

    // This method returns the correct message to show if login worked or failed
    public String returnLoginStatus(boolean loginStatus, String firstName, String lastName) {
        if (loginStatus) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
