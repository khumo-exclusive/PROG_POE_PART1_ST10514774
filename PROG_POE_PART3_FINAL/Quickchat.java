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
        MessageManager manager = new MessageManager();

        // --------------------------
        // PART 1: REGISTRATION
        // --------------------------
        System.out.println("=== Registration ===");

        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = input.nextLine();

            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured");
                break;
            } else {
                System.out.println("Username is not correctly formatted, please ensure username contains an underscore and is no more than five characters in length.");
            }
        }

        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = input.nextLine();

            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured");
                break;
            } else {
                System.out.println("Password is not correctly formatted, please ensure the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        String phone;
        while (true) {
            System.out.print("Enter phone number (+27...): ");
            phone = input.nextLine();

            if (login.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully added");
                break;
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }
        }

        System.out.println(login.registerUser(username, password, phone));

        // --------------------------
        // PART 1: LOGIN
        // --------------------------
        System.out.println("\n=== Login ===");

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        boolean loginStatus = login.loginUser(loginUsername, loginPassword);
        String message = login.returnLoginStatus(loginStatus,firstName,lastName);
        System.out.println(message);

        // --------------------------
        // PART 2 & 3: QUICKCHAT SYSTEM
        // --------------------------
        if(loginStatus) {
            System.out.println("\n====================================");
            System.out.println("Welcome to QuickChat.");
            System.out.println("====================================\n");

            System.out.print("How many messages do you wish to enter? ");
            int maxMessages = Integer.parseInt(input.nextLine());
            int messageCount = 0;

            // Load stored messages from JSON on startup
            manager.loadStoredMessagesFromJSON();

            while(true) {
                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1. Send Messages");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Stored Messages Menu"); // NEW PART 3 OPTION
                System.out.println("4. Quit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(input.nextLine());

                switch(choice) {
                    case 1:
                        if(messageCount >= maxMessages) {
                            System.out.println("You have reached your maximum number of messages!");
                            break;
                        }
                        System.out.println("\n--- NEW MESSAGE ---");
                        
                        System.out.print("Enter recipient number: ");
                        String rec = input.nextLine();

                        System.out.print("Enter your message: ");
                        String text = input.nextLine();

                        Message msg = new Message(rec, text);
                        
                        System.out.println(msg.checkRecipientCell(rec));
                        System.out.println(msg.checkMessageLength());
                        
                        if(msg.checkMessageLength().contains("ready")) {
                            System.out.println("\n1. Send Message");
                            System.out.println("2. Disregard Message");
                            System.out.println("3. Store Message");
                            System.out.print("Choose option: ");
                            int sendOpt = Integer.parseInt(input.nextLine());
                            
                            String result = msg.SentMessage(sendOpt);
                            System.out.println(result);
                            
                            if(sendOpt == 1) {
                                System.out.println("\n--- MESSAGE DETAILS ---");
                                System.out.println("Message ID: " + msg.getMessageID());
                                System.out.println("Message Hash: " + msg.getMessageHash());
                                System.out.println("Recipient: " + msg.getRecipient());
                                System.out.println("Message: " + msg.getMessageText());
                                manager.addToSent(msg);
                                messageCount++;
                            } else if(sendOpt == 2) {
                                manager.addToDisregarded(msg);
                            } else if(sendOpt == 3) {
                                manager.addToSent(msg); 
                                manager.addToStored(msg);
                                manager.saveStoredMessagesToJSON(); // Save to file
                                messageCount++;
                                
                                //show ID and Hash
                                System.out.println("\n--- STORED MESSAGE DETAILS ---");
                                System.out.println("Message ID: " + msg.getMessageID());
                                System.out.println("Message Hash: " + msg.getMessageHash());
                            }

                            // Store ID & Hash
                            manager.addMessageID(msg.getMessageID());
                            manager.addMessageHash(msg.getMessageHash());
                        }
                        break;
                        
                    case 2:
                        System.out.println("\n--- RECENT MESSAGES ---");
                        manager.displaySentMessages();
                        break;

                    // ======================
                    // PART 3 NEW FEATURES
                    // ======================
                    case 3:
                        while(true) {
                            System.out.println("\n--- STORED MESSAGES MENU ---");
                            System.out.println("a. Show sender & recipient");
                            System.out.println("b. Show longest message");
                            System.out.println("c. Search by Message ID");
                            System.out.println("d. Search by Recipient");
                            System.out.println("e. Delete by Message Hash");
                            System.out.println("f. Full Report");
                            System.out.println("g. Back to Main Menu");
                            System.out.print("Choose: ");
                            String opt = input.nextLine().toLowerCase();

                            switch(opt) {
                                case "a":
                                    manager.showSenderRecipient();
                                    break;
                                case "b":
                                    manager.showLongestMessage();
                                    break;
                                case "c":
                                    System.out.print("Enter Message ID: ");
                                    String id = input.nextLine();
                                    manager.searchByID(id);
                                    break;
                                case "d":
                                    System.out.print("Enter Recipient Number: ");
                                    String num = input.nextLine();
                                    manager.searchByRecipient(num);
                                    break;
                                case "e":
                                    System.out.print("Enter Message Hash: ");
                                    String hash = input.nextLine();
                                    manager.deleteByHash(hash);
                                    manager.saveStoredMessagesToJSON();
                                    break;
                                case "f":
                                    manager.displayFullReport();
                                    break;
                                case "g":
                                    System.out.println("Returning to Main Menu...");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Try again");
                            }
                            if(opt.equals("g")) break;
                        }
                        break;

                    case 4:
                        System.out.println("\nTotal messages sent: " + Message.returnTotalMessagess());
                        System.out.println("Exiting QuickChat... Goodbye!");
                        input.close();
                        System.exit(0);
                        break;
                        
                    default:
                        System.out.println("Invalid option, try again.");
                }
            }
        }

        input.close();
    }
}