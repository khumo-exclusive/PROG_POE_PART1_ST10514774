/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.whatsapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 27798
 */


public class LoginTest {

    // Test valid username
    @Test
    public void testUserNameCorrect() {
        Login login = new Login();
        assertTrue(login.checkUserName("ab_cd"));
    }

    // Test invalid username
    @Test
    public void testUserNameIncorrect() {
        Login login = new Login();
        assertFalse(login.checkUserName("abcde"));
    }

    // Test valid password
    @Test
    public void testPasswordCorrect() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Password1!"));
    }

    // Test invalid password
    @Test
    public void testPasswordIncorrect() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("pass"));
    }

    // Test valid phone number
    @Test
    public void testPhoneNumberCorrect() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27792600950"));
    }

    // Test invalid phone number
    @Test
    public void testPhoneNumberIncorrect() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("0792600950"));
    }

    // Test successful login
    @Test
    public void testLoginSuccess() {
        Login login = new Login();
        login.registerUser("ab_cd", "Password1!", "+27792600950");
        assertTrue(login.loginUser("ab_cd", "Password1!"));
    }

    // Test failed login
    @Test
    public void testLoginFail() {
        Login login = new Login();
        login.registerUser("ab_cd", "Password1!", "+27792600950");
        assertFalse(login.loginUser("ab_cd", "wrongPass")); 
    }
}