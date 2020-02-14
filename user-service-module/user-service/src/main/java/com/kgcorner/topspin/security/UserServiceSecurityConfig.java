package com.kgcorner.topspin.security;


import com.kgcorner.topspin.config.SecurityConfiguration;
import com.kgcorner.utils.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.HashMap;
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

    @Value("${admin.urls}")
    private String adminUrls;

    @Value("${user.urls}")
    private String userUrls;


    @Override
    public String[] getPublicUrl() {
        String[] paths = null;
        if(Strings.isNullOrEmpty(publicUrls)) {
            paths = new String[]{};
            return paths;
        }
        return publicUrls.split(",");
    }

    @Override
    public Map<String, String[]> getAuthenticatedUrl() {
        Map<String, String[]> rolePath = new HashMap<>();
        String[] paths = null;
        if(Strings.isNullOrEmpty(adminUrls)) {
            paths = new String[]{};
        } else {
            paths = adminUrls.split(",");
        }
        rolePath.put("ROLE_ADMIN", paths);
        paths = null;
        if(Strings.isNullOrEmpty(userUrls)) {
            paths = new String[]{};
        } else {
            paths = userUrls.split(",");
        }
        rolePath.put("ROLE_USER", paths);

        return rolePath;
    }
}