/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * 
 * @author khumomoloantoa
 */
public class LoginTest {

    // Test that username is accepted when it is correct
    @Test
    public void testUserNameCorrect() {
        Login login = new Login();
        assertTrue(login.checkUserName("ab_cd"));
    }

    // Test that username is rejected when it is wrong
    @Test
    public void testUserNameIncorrect() {
        Login login = new Login();
        assertFalse(login.checkUserName("abcde"));
    }

    // Test that password is accepted when it meets all rules
    @Test
    public void testPasswordCorrect() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Password1!"));
    }

    // Test that password is rejected when it is wrong
    @Test
    public void testPasswordIncorrect() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("pass"));
    }

    // Test that phone number is accepted when format is right
    @Test
    public void testPhoneNumberCorrect() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27792600950"));
    }

    // Test that phone number is rejected when format is wrong
    @Test
    public void testPhoneNumberIncorrect() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("0792600950"));
    }

    // Test that login works when details are correct
    @Test
    public void testLoginSuccess() {
        Login login = new Login();
        login.registerUser("ab_cd", "Password1!", "+27792600950");
        assertTrue(login.loginUser("ab_cd", "Password1!"));
    }

    // Test that login fails when details are wrong
    @Test
    public void testLoginFail() {
        Login login = new Login();
        login.registerUser("ab_cd", "Password1!", "+27792600950");
        assertFalse(login.loginUser("ab_cd", "wrongPass")); 
    }
}