/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

import java.util.Scanner;

/**
 *
 * @author khumomoloantoa
 */
public class Quickchat {

     public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login = new Login();

        // --------------------------
        // PART 1: REGISTRATION
        // --------------------------
        System.out.println("=== Registration ===");

        // Ask user for username and loop until everything is correct
        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = input.nextLine();

            // Check username rules
            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured");
                break;
            } else {
                System.out.println("Username is not correctly formatted, please ensure username contains an underscore and is no more than five characters in length.");
            }
        }

        // Ask user for password and loop until everything is correct
        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = input.nextLine();

            // Check password rules
            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured");
                break;
            } else {
                System.out.println("Password is not correctly formatted, please ensure the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        // Ask user for phone number and loop until everything is correct
        String phone;
        while (true) {
            System.out.print("Enter phone number (+27...): ");
            phone = input.nextLine();

            // Check phone number rules
            if (login.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully added");
                break;
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }
        }

        // Save all user details now that everything is correct
        System.out.println(login.registerUser(username, password, phone));

        // --------------------------
        // PART 1: LOGIN
        // --------------------------
        System.out.println("\n=== Login ===");

        // Get user's names to use in welcome message
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        // Ask for login details
        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        // Check if login was successful
        boolean loginStatus = login.loginUser(loginUsername, loginPassword);

        // Show correct message based on login result
        String message = login.returnLoginStatus(loginStatus,firstName,lastName);
        System.out.println(message);

        // --------------------------
        // PART 2: QUICKCHAT SYSTEM
        // Only runs if login was successful
        // --------------------------
        if(loginStatus) {
            // Show welcome message 
            System.out.println("\n====================================");
            System.out.println("Welcome to QuickChat.");
            System.out.println("====================================\n");

            // Ask user how many messages they want to send
            System.out.print("How many messages do you wish to enter? ");
            int maxMessages = Integer.parseInt(input.nextLine());
            int messageCount = 0;

            // Main menu loops  until user selects quit
            while(true) {
                // Show the main menu options 
                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1. Send Messages");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Quit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(input.nextLine());

                switch(choice) {
                    case 1:
                        // Check if user already sent all their allowed messages
                        if(messageCount >= maxMessages) {
                            System.out.println("You have reached your maximum number of messages!");
                            break;
                        }
                        System.out.println("\n--- NEW MESSAGE ---");
                        
                        // Ask for recipient number
                        System.out.print("Enter recipient number: ");
                        String rec = input.nextLine();

                        // Ask for message text
                        System.out.print("Enter your message: ");
                        String text = input.nextLine();

                        // Create new message object with details
                        Message msg = new Message(rec, text);
                        
                        // Check recipient number and message length
                        System.out.println(msg.checkRecipientCell(rec));
                        System.out.println(msg.checkMessageLength());
                        
                        // If message is valid, show send options
                        if(msg.checkMessageLength().contains("ready")) {
                            System.out.println("\n1. Send Message");
                            System.out.println("2. Disregard Message");
                            System.out.println("3. Store Message to send later");
                            System.out.print("Choose option: ");
                            int sendOpt = Integer.parseInt(input.nextLine());
                            
                            // Do what user selected and show result
                            String result = msg.SentMessage(sendOpt);
                            System.out.println(result);
                            
                            // If they sent the message, show all details as required
                            if(sendOpt == 1) {
                                System.out.println("\n--- MESSAGE DETAILS ---");
                                System.out.println("Message ID: " + msg.getMessageID());
                                System.out.println("Message Hash: " + msg.getMessageHash());
                                System.out.println("Recipient: " + msg.getRecipient());
                                System.out.println("Message: " + msg.getMessageText());
                                messageCount++; // Add to count of messages sent
                            }
                        }
                        break;
                        
                    case 2:
                        // Show all messages sent so far
                        System.out.println("\n--- RECENT MESSAGES ---");
                        System.out.println("Coming Soon.");
                        break;
                        
                    case 3:
                        // Before exiting, show total number of messages sent
                        System.out.println("\nTotal messages sent: " + new Message("", "").returnTotalMessagess());
                        System.out.println("Exiting QuickChat... Goodbye!");
                        input.close();
                        System.exit(0);
                        break;
                        
                    default:
                        // If user types wrong number
                        System.out.println("Invalid option, try again.");
                }
            }
        }

        input.close();
    }
}
