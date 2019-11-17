package com.kgcorner.utils;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description : Sanity check for Environment variables
 * Environment variables are sensitive to injection attacks, just like any other input.
 * @see <a href="https://sonarcloud.io/organizations/kgcorner-github/rules?open=squid%3AS5304&rule_key=squid%3AS5304">
 * https://sonarcloud.io/organizations/kgcorner-github/rules?open=squid%3AS5304&rule_key=squid%3AS5304
 * </a>
 *
 * Author: kumar
 * Created on : 17/11/19
 */

public final class EnvironmentVariableSanityChecker {
    //Facebook
    private static String FACEBOOK_APP_KEY_REGEX = "^[0-9].+$";
    private static String FACEBOOK_SECRET_KEY_REGEX = "^[0-9a-z].+$";
    private static Pattern FACEBOOK_APP_KEY_PATTERN = Pattern.compile(FACEBOOK_APP_KEY_REGEX);
    private static Pattern FACEBOOK_APP_SECRET_PATTERN = Pattern.compile(FACEBOOK_SECRET_KEY_REGEX);

    //Google
    private static String GOOGLE_APP_KEY_REGEX = "^[0-9a-z\\-]+\\.apps\\.googleusercontent\\.com";
    private static String GOOGLE_SECRET_KEY_REGEX = "^[0-9a-zA-Z].+$";
    private static Pattern GOOGLE_APP_KEY_PATTERN = Pattern.compile(GOOGLE_APP_KEY_REGEX);
    private static Pattern GOOGLE_APP_SECRET_PATTERN = Pattern.compile(GOOGLE_SECRET_KEY_REGEX);

    /**
     * Checks whether given key fits pattern of facebook app key
     * @param key key to be tested
     * @return true if passed false otherwise
     */
    public static boolean checkForFacebookAppKey(String key) {
       return FACEBOOK_APP_KEY_PATTERN.matcher(key).matches();
    }

    /**
     * Checks whether given key fits pattern of facebook secret key
     * @param key key to be tested
     * @return true if passed false otherwise
     */
    public static boolean checkForFacebookSecretKey(String key) {
        return FACEBOOK_APP_SECRET_PATTERN.matcher(key).matches();
    }

    /**
     * Checks whether given key fits pattern of google app key
     * @param key key to be tested
     * @return true if passed false otherwise
     */
    public static boolean checkForGoogleAppKey(String key) {
        return GOOGLE_APP_KEY_PATTERN.matcher(key).matches();
    }

    /**
     * Checks whether given key fits pattern of google secret key
     * @param key key to be tested
     * @return true if passed false otherwise
     */
    public static boolean checkForGoogleSecretKey(String key) {
        return GOOGLE_APP_SECRET_PATTERN.matcher(key).matches();
    }
}