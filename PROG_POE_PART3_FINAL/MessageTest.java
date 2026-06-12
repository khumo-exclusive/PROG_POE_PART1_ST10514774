/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * @author khumomoloantoa
 */
public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }
    
    @Test
    public void testMessageLengthFail() {
        String longText = "a".repeat(300);
        Message msg = new Message("+27718693002", longText);
        assertTrue(msg.checkMessageLength().contains("exceeds 250 characters"));
    }

    @Test
    public void testRecipientSuccess() {
        Message msg = new Message("+27718693002", "Test");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell("+27718693002"));
    }
    
    @Test
    public void testRecipientFail() {
        Message msg = new Message("08575975889", "Test");
        assertTrue(msg.checkRecipientCell("08575975889").contains("incorrectly formatted"));
    }

    @Test
    public void testMessageHash() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.createMessageHash().contains("HITONIGHT"));
    }

    @Test
    public void testMessageID() {
        Message msg = new Message("+27718693002", "Test message");
        assertTrue(msg.checkMessageID());
        assertEquals(10, msg.getMessageID().length());
    }

    @Test
    public void testSendOptionSend() {
        Message msg = new Message("+27718693002", "Test");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }
    
    @Test
    public void testSendOptionDisregard() {
        Message msg = new Message("+27718693002", "Test");
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
    }
    
    @Test
    public void testSendOptionStore() {
        Message msg = new Message("+27718693002", "Test");
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }
}