package com.kgcorner.crypto;

import com.auth0.jwt.JWT;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Description : Unit test for {@code JwtUtility}
 * Author: kumar
 * Created on : 18/11/19
 */

@PrepareForTest(JWT.class)
public class JwtUtilityTest {
    private static final String SALT = "salt";
    private static final int EXPIRE_IN = 100;

    @Test
    public void createJWTToken() {
        Map<String, String> claims = new HashMap<>();
        claims.put("payload","abc");
        String token = JwtUtility.createJWTToken(SALT, claims, EXPIRE_IN);
        assertNotNull("Generated toke is null", token);
        String payload = JwtUtility.getClaim("payload", token);

        assertNotNull("submitted Payload is null", payload);
        assertEquals("Submitted payload is not matching", "abc", payload);

        payload = JwtUtility.getClaim("payload", token, String.class);
        assertEquals("Submitted payload is not matching", "abc", payload);
    }

    @Test
    public void validateToken() {
        Map<String, String> claims = new HashMap<>();
        claims.put("payload","abc");
        String token = JwtUtility.createJWTToken(SALT, claims, EXPIRE_IN);
        assertNotNull("Generated toke is null", token);
        assertTrue("Token validation failed", JwtUtility.validateToken(SALT, token));
    }

}