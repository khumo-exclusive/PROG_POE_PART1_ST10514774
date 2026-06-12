/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

import java.util.Random;

/**
 *
 * @author khumomoloantoa
 */
public class Message {
    
 private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;
    private static int totalMessages = 0;

    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = createMessageID();
        this.messageHash = createMessageHash();
    }

    // Check recipient format
    public String checkRecipientCell(String phone) {
        if (phone != null && phone.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
    }

    // Check message length (<=250)
    public String checkMessageLength() {
        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            return "Error: Message exceeds 250 characters.";
        }
    }

    // Create 10-digit random ID
    private String createMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    // Create hash from message
    public String createMessageHash() {
        if (messageText == null || messageText.isEmpty()) return "HASH_EMPTY";
        String[] words = messageText.split(" ");
        String lastWord = words[words.length - 1].toUpperCase().replaceAll("[^A-Z]", "");
        return "HASH_" + lastWord;
    }

    // Check ID format
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10 && messageID.matches("\\d+");
    }

    // Send options
    public String SentMessage(int option) {
        switch (option) {
            case 1:
                totalMessages++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    public static int returnTotalMessagess() {
        return totalMessages;
    }

    // Getters
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
}