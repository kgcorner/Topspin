package com.kgcorner.crypto;


import java.security.SecureRandom;
import java.util.Random;

/**
 * Generates Random String
 */
public final class BigStringGenerator {

    private static final Random random = new SecureRandom();
    private BigStringGenerator(){}

    /**
     * Returns a big string
     * @return a big string
     */
    public static String generateBigString() {
        int leftLimit = 65; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit +
                (Math.abs(random.nextInt() * (rightLimit - leftLimit + 1)) % 25);
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}