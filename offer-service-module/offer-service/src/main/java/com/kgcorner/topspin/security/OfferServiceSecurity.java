package com.kgcorner.topspin.security;


import com.kgcorner.topspin.config.SecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */

@Configuration
public class OfferServiceSecurity extends SecurityConfiguration {

    @Override
    public String[] getPublicUrl() {
        String[] paths = new String[1];
        paths[0] = "/**";
        return paths;
    }

    @Override
    public Map<String, String[]> getAuthenticatedUrl() {
        Map<String, String[]> rolePathMap = new HashMap<>();
        String[] paths = new String[1];
        paths[0] = "/manage/**";
        rolePathMap.put("ADMIN", paths);
        return rolePathMap;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        getDefaultSecurity(http);
    }
}