package com.kgcorner.topspin.service;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.topspin.model.Token;

public interface AuthenticationService {
    Token authenticateToken(String token);
    Token authenticateCode(String code, String redirectUrl);
}