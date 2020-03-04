package com.kgcorner.topspin.providers;


import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.clients.AuthServiceClient;
import com.kgcorner.topspin.clients.model.TokenModel;
import com.kgcorner.topspin.model.BasicAuthToken;
import com.kgcorner.topspin.model.BearerAuthToken;
import com.kgcorner.topspin.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 02/12/19
 */

public abstract class DefaultTokenAuthenticationProvider  implements AuthenticationProvider {

    @Autowired
    private AuthServiceClient client;


    public Authentication getAuthDetails(String tokenString, Class tokenClass) {
        TokenModel tokenResponse = client.getToken(tokenString);
        String roleClaim = JwtUtility.getClaim("ROLE", tokenResponse.getAccessToken());
        List<Role> roleList = new ArrayList<>();
        if(roleClaim.contains(",")) {
            String[] roles = roleClaim.split(",");
            for(String r : roles) {
                Role role = new Role(r);
                roleList.add(role);
            }
        } else {
            Role role = new Role(roleClaim);
            roleList.add(role);
        }
        AbstractAuthenticationToken token = null;
        if(tokenClass.isAssignableFrom(BearerAuthToken.class))
            token = new BearerAuthToken(roleList, tokenResponse.getAccessToken());
        else
            token = new BasicAuthToken(tokenResponse.getAccessToken(), null, roleList);
        return token;
    }

}