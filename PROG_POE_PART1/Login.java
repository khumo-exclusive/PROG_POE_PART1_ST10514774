/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.whatsapp;

/**
 *
 * @author 27798
 */
public class Login {

    // Stored user details
    private String storedUsername;
    private String storedPassword;

    // Check username
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Check password complexity
    public boolean checkPasswordComplexity(String password) {

        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) hasUppercase = true;
            if (Character.isDigit(ch)) hasNumber = true;
            if (!Character.isLetterOrDigit(ch)) hasSpecial = true;
        }

        return hasUppercase && hasNumber && hasSpecial;
    }

    // Regex sourced from: https://regex101.com
    public boolean checkCellPhoneNumber(String phone) {
        return phone != null && phone.matches("^\\+27\\d{9}$");
    }

    // Register user and STORE details
    public String registerUser(String username, String password, String phone) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // STORE USER DETAILS
        this.storedUsername = username;
        this.storedPassword = password;

        return "User successfully registered.";
    }

    // Login using stored values
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (storedUsername == null || storedPassword == null) {
          return  false; 
    }
       return storedUsername.equals(enteredUsername) && storedPassword.equals(enteredPassword);
    }

    // Login message
    public String returnLoginStatus(boolean loginStatus, String firstName, String lastName) {
        if (loginStatus) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
