package com.kgcorner.crypto;

/*
Description : <Write class Description>
Author: kumar
Created on : 26/08/19
*/

import org.bouncycastle.crypto.generators.BCrypt;

public class Hasher {
    //Defines number of hashing rounds. Its non configurable because passwords are supposed to
    // be protected using this. Changing this number may lead to failed password validation
    private static final int ROUNDS = 10;

    public static String getCrypt(String payload, String salt) {
        byte[] crypt = BCrypt.generate(payload.getBytes(), salt.getBytes(), ROUNDS);
        return new String(crypt);
    }
}