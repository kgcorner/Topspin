package com.kgcorner.topspin;

/*
Description : Resources for authenticating using various way
Author: kumar
Created on : 9/8/19
*/

import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.service.Authenticator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResource {
    private static final String AUTHORIZATION = "Authorization";
    @GetMapping("/health")
    public String getHealth() {
        return "Ok";
    }

    @Autowired
    private Authenticator authenticator;

    @ApiOperation("Returns token for authorization")
    @GetMapping("/token")
    public Token getToken(@ApiParam(value = "token of the user, could be bearer, basic", required = true)
                          @RequestHeader(value=AUTHORIZATION, required = true) String token
                          ) {
        return authenticator.authenticateWithToken(token);
    }

    @ApiOperation("Returns token for authorization")
    @GetMapping("/token/oauth")
    public Token getTokenForOAuth(@ApiParam(value = "access token provided by oauth server in form of <server name> <token>" , required = true)
                          @RequestHeader(value=AUTHORIZATION, required = true) String token
    ) {
        return authenticator.authenticateWithToken(token);
    }

    @ApiOperation("Returns token for authorization")
    @GetMapping("/token/oauth/code")
    public Token getTokenForOAuth(@ApiParam(value = "access token provided by oauth server in form of <server name> <token>" , required = true)
                                  @RequestHeader(value=AUTHORIZATION, required = true) String token,
                                  @ApiParam(value = "used redirect uri" , required = true)
                                  @RequestHeader(value="redirect_uri", required = true) String redirect_uri
    ) {
        return authenticator.authenticateWithCode(token, redirect_uri);
    }
}