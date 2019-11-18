package com.kgcorner.utils;

/*
Description : <Write class Description>
Author: kumar
Created on : 04/11/19
*/

public final class Strings {

    private Strings(){}
    public static boolean isNullOrEmpty(String value) {
        return value == null ? true : value.trim().length() < 1;
    }
}