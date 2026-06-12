/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author khumomoloantoa
 */

public class MessageManager {
    // Required Arrays
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> disregardedMessages = new ArrayList<>();
    private List<Message> storedMessages = new ArrayList<>();
    private List<String> messageHashes = new ArrayList<>();
    private List<String> messageIDs = new ArrayList<>();

    // --- Add to arrays ---
    public void addToSent(Message m) { sentMessages.add(m); }
    public void addToDisregarded(Message m) { disregardedMessages.add(m); }
    public void addToStored(Message m) { storedMessages.add(m); }
    public void addMessageHash(String h) { messageHashes.add(h); }
    public void addMessageID(String id) { messageIDs.add(id); }

    // --- Display ---
    public void displaySentMessages() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages sent yet.");
            return;
        }
        for (Message m : sentMessages) {
            System.out.printf("ID: %s | To: %s | Text: %s%n",
                    m.getRecipient(), m.getMessageID(), m.getMessageText());
        }
    }

    // --- PART 3 FEATURES ---

    // a. Show sender & recipient + ID
    public void showSenderRecipient() {
        if (storedMessages.isEmpty()) { 
            System.out.println("No stored messages found."); 
            return; 
        }
        System.out.println("ID | Sender | Recipient");
        System.out.println("---------------------------");
        for (Message m : storedMessages) {
            System.out.printf("%s | You | %s%n", m.getMessageID(), m.getRecipient());
        }
    }

    // b. Longest message
    public void showLongestMessage() {
        if (storedMessages.isEmpty()) { 
            System.out.println("No stored messages found."); 
            return; 
        }
        Message longest = storedMessages.get(0);
        for (Message m : storedMessages) {
            if (m.getMessageText().length() > longest.getMessageText().length())
                longest = m;
        }
        System.out.println("Longest stored message:");
        System.out.println("ID: " + longest.getMessageID());
        System.out.println("Text: " + longest.getMessageText());
    }

    // c. Search by ID
    public void searchByID(String id) {
        boolean found = false;
        for (Message m : storedMessages) {
            if (m.getMessageID().equals(id)) {
                System.out.println("Message Found:");
                System.out.printf("ID: %s | Hash: %s | Recipient: %s | Text: %s%n" , m.getMessageID(), m.getMessageHash(), m.getRecipient(), m.getMessageText());
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Message ID not found.");
    }

    // d. Search by recipient
    public void searchByRecipient(String num) {
        boolean found = false;
        System.out.println("Results for recipient: " + num);
        for (Message m : storedMessages) {
            if (m.getRecipient().equals(num)) {
                System.out.printf("ID: %s | Text: %s%n", m.getMessageID(), m.getMessageText());
                found = true;
            }
        }
        if (!found) System.out.println("No messages for this recipient.");
    }

    // e. Delete by hash
    public void deleteByHash(String hash) {
        Iterator<Message> it = storedMessages.iterator();
        while (it.hasNext()) {
            Message m = it.next();
            if (m.getMessageHash().equals(hash)) {
                it.remove();
                messageHashes.remove(hash);
                System.out.println("Sucessfully Deleted: \" " + m.getMessageText() +"\"");
                return;
            }
        }
        System.out.println("Hash not found.");
    }

    // f. Full report
    public void displayFullReport() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages to report");
            return;
        }
        System.out.println("\n=== FULL STORED MESSAGES REPORT ===");
        System.out.println("ID | Hash | Recipient | Message");
        System.out.println("--------------------------------------------------------------------------");
        for (Message m : storedMessages) {
            System.out.printf("%s | %s | %s | %s%n",
                    m.getMessageID(), m.getMessageHash(), m.getRecipient(), m.getMessageText());
        }
    }

    // --- JSON Handling ---
    public void saveStoredMessagesToJSON() {
        JSONArray list = new JSONArray();
        for (Message m : storedMessages) {
            JSONObject obj = new JSONObject();
            obj.put("id", m.getMessageID());
            obj.put("recipient", m.getRecipient());
            obj.put("text", m.getMessageText());
            obj.put("hash", m.getMessageHash());
            list.add(obj);
        }
        try (FileWriter fw = new FileWriter("stored_messages.json")) {
            fw.write(list.toJSONString());
        } catch (Exception e) { 
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadStoredMessagesFromJSON() {
        try (FileReader fr = new FileReader("stored_messages.json")) {
            JSONArray arr = (JSONArray) new JSONParser().parse(fr);
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;
                Message m = new Message((String)obj.get("recipient"), (String)obj.get("text"));
                addToStored(m);
                addMessageID((String)obj.get("id"));
                addMessageHash((String)obj.get("hash"));
            }
            if (!arr.isEmpty()) System.out.println("Previous stored messages loaded.");
        } catch (Exception e) { 
        // File may not exist yet 
    
        }
        
    }
    // --- For Testing ---
    public List<Message> getSentMessages() { return sentMessages; }
    public List<Message> getStoredMessages() { return storedMessages; }
}
