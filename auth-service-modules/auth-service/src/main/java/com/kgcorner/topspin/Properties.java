package com.kgcorner.topspin;

/*
Description : <Write class Description>
Author: kumar
Created on : 26/08/19
*/

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties()
public class Properties {
    @Value("${password.salt}")
    private String passwordSalt;

    @Value("${token.expiration.second}")
    private int tokenExpirationInSecond;

    @Value("${token.salt}")
    private String tokenSalt;

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public int getTokenExpirationInSecond() {
        return tokenExpirationInSecond;
    }

    public void setTokenExpirationInSecond(int tokenExpirationInSecond) {
        this.tokenExpirationInSecond = tokenExpirationInSecond;
    }

    public String getTokenSalt() {
        return tokenSalt;
    }

    public void setTokenSalt(String tokenSalt) {
        this.tokenSalt = tokenSalt;
    }
}