/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author khumomoloantoa
 */
public class Message {
    
 // All required fields for message data
    private String messageID;
    private int messageCount;
    private String recipient;
    private String messageText;
    private String messageHash;
    
    // Lists to store sent and saved messages
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    // Name of the JSON file we save messages into
    private static final String MESSAGES_JSON_FILE = "quickchat_messages.json";

    // Constructor - creates new message and sets all values automatically
    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageCount = sentMessages.size() + 1;
        this.messageHash = createMessageHash();
    }

    // Generates 10 digit random unique message ID
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for(int i=0; i<10; i++){
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    // Checks that message ID is maximum 10 characters
    public boolean checkMessageID() {
        return this.messageID.length() <= 10;
    }

    // Checks recipient number format is correct
    public String checkRecipientCell(String number) {
        if(number != null && number.matches("^\\+\\d{11,14}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Creates message hash exactly as required: first 2 digits of ID : count : first word + last word all uppercase
    public String createMessageHash() {
        String firstTwo = messageID.substring(0,2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length-1].toUpperCase();
        return firstTwo + ":" + messageCount + ":" + firstWord + lastWord;
    }

    // Checks message does not exceed 250 character limit
    public String checkMessageLength() {
        if(messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int over = messageText.length() - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
    }

    // Handles user choice: Send, Disregard or Store message
    public String SentMessage(int choice) {
        switch(choice) {
            case 1:
                sentMessages.add(this);
                saveAllMessagesToJSONFile(); // Save to JSON file when sent
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storedMessages.add(this);
                storeMessage();
                saveAllMessagesToJSONFile(); // Save to JSON file when stored
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // Returns all sent messages to display
    public String printMessages() {
        StringBuilder all = new StringBuilder();
        for(Message m : sentMessages) {
            all.append("Message ID: ").append(m.messageID).append("\n");
            all.append("Message Hash: ").append(m.messageHash).append("\n");
            all.append("Recipient: ").append(m.recipient).append("\n");
            all.append("Message: ").append(m.messageText).append("\n");
            all.append("-------------------------------------\n");
        }
        return all.toString();
    }

    // Returns total number of messages sent
    public int returnTotalMessagess() {
        return sentMessages.size();
    }

    // Confirms message saved successfully
    public void storeMessage() {
        System.out.println("Message saved to storage successfully.");
    }

    //  Saves all messages into a JSON file
    public void saveAllMessagesToJSONFile() {
        // Create a JSON Array to hold all messages
        JSONArray messagesArray = new JSONArray();

        // Go through every message we have sent
        for(Message singleMessage : sentMessages) {
            // Create a JSON Object for one message
            JSONObject messageObject = new JSONObject();
            messageObject.put("messageID", singleMessage.messageID);
            messageObject.put("messageHash", singleMessage.messageHash);
            messageObject.put("recipientNumber", singleMessage.recipient);
            messageObject.put("messageContent", singleMessage.messageText);
            messageObject.put("messageNumber", singleMessage.messageCount);
            
            // Add this message to the array
            messagesArray.put(messageObject);
        }

        // Write everything to the file
        try (FileWriter fileWriter = new FileWriter(MESSAGES_JSON_FILE)) {
            fileWriter.write(messagesArray.toString(4)); // 4 means it's nicely spaced and easy to read
            fileWriter.flush();
            System.out.println("All messages saved to JSON file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving messages to file: " + e.getMessage());
        }
    }

    //  Loads messages back from the JSON file when program starts
    public void loadMessagesFromJSONFile() {
        try {
            // Read all the content from the file
            String fileContent = Files.readString(Paths.get(MESSAGES_JSON_FILE));
            JSONArray messagesArray = new JSONArray(fileContent);

            // Read each message from the file
            for(int i = 0; i < messagesArray.length(); i++) {
                JSONObject messageObject = messagesArray.getJSONObject(i);
                
                // Create new message object with the saved data
                Message loadedMessage = new Message("", "");
                loadedMessage.messageID = messageObject.getString("messageID");
                loadedMessage.messageHash = messageObject.getString("messageHash");
                loadedMessage.recipient = messageObject.getString("recipientNumber");
                loadedMessage.messageText = messageObject.getString("messageContent");
                loadedMessage.messageCount = messageObject.getInt("messageNumber");
                
                // Add it back to our list
                sentMessages.add(loadedMessage);
            }
            System.out.println("Previous messages loaded from JSON file successfully.");
        } catch (IOException e) {
            // If file doesn't exist yet, just start fresh
            System.out.println("No existing message file found, starting new session.");
        }
    }

    // Getter methods to access message details
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}
    

