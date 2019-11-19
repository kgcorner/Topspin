package com.kgcorner.utils;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/11/19
 */

public class EnvironmentVariableSanityCheckerTest {

    @Test
    public void checkForFacebookAppKey() {
        String key = "098";
        assertTrue(EnvironmentVariableSanityChecker.checkForFacebookAppKey(key));
    }

    @Test
    public void checkForFacebookAppKeyFail() {
        String key = "+_";
        assertFalse(EnvironmentVariableSanityChecker.checkForFacebookAppKey(key));
    }

    @Test
    public void checkForFacebookSecretKey() {
        String key = "098";
        assertTrue(EnvironmentVariableSanityChecker.checkForFacebookSecretKey(key));
    }

    @Test
    public void checkForFacebookSecretKeyFail() {
        String key = "*98";
        assertFalse(EnvironmentVariableSanityChecker.checkForFacebookSecretKey(key));
    }

    @Test
    public void checkForGoogleAppKey() {
        String key ="098.apps.googleusercontent.com";
        assertTrue(EnvironmentVariableSanityChecker.checkForGoogleAppKey(key));
    }

    @Test
    public void checkForGoogleAppKeyFail() {
        String key ="098";
        assertFalse(EnvironmentVariableSanityChecker.checkForGoogleAppKey(key));
    }

    @Test
    public void checkForGoogleSecretKey() {
        String key ="098";
        assertTrue(EnvironmentVariableSanityChecker.checkForGoogleSecretKey(key));
    }

    @Test
    public void checkForGoogleSecretKeyFail() {
        String key ="098*";
        assertFalse(EnvironmentVariableSanityChecker.checkForGoogleSecretKey(key));
    }
}