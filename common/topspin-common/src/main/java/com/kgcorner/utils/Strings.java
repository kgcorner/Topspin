package com.kgcorner.utils;

/*
Description : Utility class for String
Author: kumar
Created on : 04/11/19
*/

import java.util.UUID;

public final class Strings {

    private Strings(){}

    public static boolean isNullOrEmpty(String value) {
        return value == null ? true : value.trim().length() < 1;
    }

    public static String getGuid() {
        return UUID.randomUUID().toString();
    }
}