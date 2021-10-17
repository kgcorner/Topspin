package com.kgcorner.topspin.security;


import com.kgcorner.topspin.config.SecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 31/08/21
 */

@Configuration
@Order(102)
public class StoreServiceSecurity extends SecurityConfiguration {

    @Override
    public String[] getPublicUrl() {
        String[] paths = new String[2];
        paths[0] = "/stores/**";
        paths[1] = "/categories/**";
        return paths;
    }

    @Override
    public Map<String, String[]> getAuthenticatedUrl() {
        String[] paths = {"/manage/**"};
        Map<String, String[]> rolePathMap = new HashMap<>();
        rolePathMap.put("ADMIN", paths);
        return rolePathMap;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        getDefaultSecurity(http);
    }
}