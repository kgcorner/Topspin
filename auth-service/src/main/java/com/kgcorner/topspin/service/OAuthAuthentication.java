package com.kgcorner.topspin.service;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.topspin.model.Token;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("oauth")
public class OAuthAuthentication implements AuthenticationService {


    @Override
    public Token authenticateToken(String token) {
        return null;
    }

    @Override
    public Token authenticateCode(String code, String redirectUrl) {
        return null;
    }

    @Override
    public Token resolveToken(String token, String redirect_uri, String serverName) {
        return null;
    }
}