package com.kgcorner.crypto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * Description : Utility Class for JWT token
 * Author: kumar
 * Created on : 26/08/19
 */
public final class JwtUtility {
    private static final String ISSUER = "topspin";
    private JwtUtility(){}

    public static String createJWTToken(String salt, Map<String, String> claim, int expiresInSeconds) {
        Instant now = Instant.now();
        Algorithm algorithm = Algorithm.HMAC256(salt);
        JWTCreator.Builder jwtBuilder = JWT.create()
            .withIssuedAt(Date.from(now))
            .withIssuer(ISSUER)
            .withExpiresAt(Date.from(now.plusSeconds(expiresInSeconds)));
        if(claim != null) {
            for (Map.Entry<String, String> entry : claim.entrySet()) {
                jwtBuilder.withClaim(entry.getKey(), entry.getValue());
            }
        }
        return jwtBuilder.sign(algorithm);
    }

    public static boolean validateToken(String salt, String token) {
        Algorithm algorithm = Algorithm.HMAC256(salt);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        try {
            verifier.verify(token);
        } catch (TokenExpiredException x) {
            return false;
        }
        return true;
    }

    public static String getClaim(String key, String token) {
        try {
            DecodedJWT decodedJwt = JWT.decode(token);
            return decodedJwt.getClaim(key).asString();
        }
        catch (JWTVerificationException x) {
            throw new IllegalArgumentException(x.getMessage());
        }
    }

    public static <T> T getClaim(String key, String token, Class<T> type) {
        try {
            DecodedJWT decodedJwt = JWT.decode(token);
            return decodedJwt.getClaim(key).as(type);
        }
        catch (JWTVerificationException x) {
            throw new IllegalArgumentException(x.getMessage());
        }
    }


}