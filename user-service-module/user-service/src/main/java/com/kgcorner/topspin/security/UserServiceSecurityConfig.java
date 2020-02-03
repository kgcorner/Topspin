package com.kgcorner.topspin.security;


import com.kgcorner.topspin.config.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Map;

/**
 * Description : Implementation of security config for user service
 * Author: kumar
 * Created on : 02/12/19
 */

@EnableWebSecurity
public class UserServiceSecurityConfig extends SecurityConfiguration {

    @Value("${public.urls}")
    private String publicUrls;


    @Override
    public String getPublicUser() {
        return publicUrls;
    }

    @Override
    public Map<String, String> getAuthenticatedUrl() {
        return null;
    }
}