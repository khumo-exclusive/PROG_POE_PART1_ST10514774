/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.whatsapp;

import java.util.Scanner;

/**
 *
 * @author 27798
 */
public class Whatsapp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== Registration ===");

        // Ask user to enter a valid username
        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = input.nextLine();

            // Check if username is valid
            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured");
                break;
            } else {
                System.out.println("Username is not correctly formatted, please ensure username contains an underscore and is no more than five characters in length.");
            }
        }

        // Ask user to enter a valid password
        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = input.nextLine();

            // Check if password meets requirements
            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured");
                break;
            } else {
                System.out.println("Password is not correctly formatted, please ensure the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        // Ask user to enter a valid phone number
        String phone;
        while (true) {
            System.out.print("Enter phone number (+27...): ");
            phone = input.nextLine();

            // Check if phone number is valid
            if (login.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully added");
                break;
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }
        }

        // Store user details after all inputs are valid
        System.out.println(login.registerUser(username, password, phone));

        System.out.println("\n=== Login ===");

        // Ask for user's personal details
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        // Ask for login credentials
        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        // Check login details
        boolean loginStatus = login.loginUser(loginUsername, loginPassword);

        // Display login result
        String message = login.returnLoginStatus(loginStatus,firstName,lastName);
        System.out.println(message);

        input.close();
    }
}