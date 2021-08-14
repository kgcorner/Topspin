package com.kgcorner.crypto;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

/**
 * Description : Generates BCrypt Hash for given payload and salt
 * Author: kumar
 * Created on : 26/08/19
 */
public final class Hasher {
    //Defines number of hashing rounds. Its non configurable because passwords are supposed to
    // be protected using this. Changing this number may lead to failed password validation
    private static final int ROUNDS = 10;

    private Hasher(){}

    public static String getCrypt(String payload, String salt) {
        SecureRandom random = new SecureRandom(salt.getBytes());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(ROUNDS, random);

        return encoder.encode(payload);
    }

    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public static void main(String[] args) {
        System.out.println(getCrypt("gatewaykey123secret", "secret"));
    }
}