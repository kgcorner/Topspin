package kgcorner.utils;

import com.kgcorner.utils.EnvironmentVariableSanityChecker;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


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
        assertFalse(EnvironmentVariableSanityChecker.checkForFacebookAppKey(null));
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
        assertFalse(EnvironmentVariableSanityChecker.checkForFacebookSecretKey(null));
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
        assertFalse(EnvironmentVariableSanityChecker.checkForGoogleAppKey(null));
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
        assertFalse(EnvironmentVariableSanityChecker.checkForGoogleSecretKey(null));
    }

    @Test
    public void checkForGoogleSecretKeyFail() {
        String key ="098*";
        assertFalse(EnvironmentVariableSanityChecker.checkForGoogleSecretKey(key));
    }

    @Test
    public void checkForAwsApiKeyFail() {
        String key = "987*";
        assertFalse(EnvironmentVariableSanityChecker.checkForAwsApiKey(key));
    }

    @Test
    public void checkForAwsApiKeyFailForNull() {
        assertFalse(EnvironmentVariableSanityChecker.checkForAwsApiKey(null));
    }

    @Test
    public void checkForAwsApiKey() {
        String key = "987ACFG";
        assertTrue(EnvironmentVariableSanityChecker.checkForAwsApiKey(key));
    }

    @Test
    public void checkForAwsApiSecretFail() {
        String key = "987*";
        assertFalse(EnvironmentVariableSanityChecker.checkForAwsApiSecret(key));
    }

    @Test
    public void checkForAwsApiSecretFailForNull() {
        assertFalse(EnvironmentVariableSanityChecker.checkForAwsApiSecret(null));
    }

    @Test
    public void checkForAwsApiSecret() {
        String key = "987ahch";
        assertTrue(EnvironmentVariableSanityChecker.checkForAwsApiSecret(key));
    }
}