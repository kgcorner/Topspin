package com.kgcorner.crypto;

/*
Description : <Write class Description>
Author: kumar
Created on : 26/08/19
*/

import java.security.SecureRandom;
import java.util.Random;

public class BigStringGenerator {
    private static final Random random = new SecureRandom();
    public static String generateBigString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}