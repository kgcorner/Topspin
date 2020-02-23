package com.kgcorner.topspin.utils;

/*
Description : <Write class Description>
Author: kumar
Created on : 11/11/19
*/

import com.kgcorner.topspin.UserServiceTestsRunner;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private static Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(UserServiceTestsRunner.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {

        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}