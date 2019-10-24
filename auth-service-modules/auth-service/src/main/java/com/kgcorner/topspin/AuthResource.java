package com.kgcorner.topspin;

/*
Description : Resources for authenticating using various way
Author: kumar
Created on : 9/8/19
*/

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.service.Authenticator;
import com.kgcorner.topspin.service.RegistrationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthResource {
    private static final String AUTHORIZATION = "Authorization";
    @GetMapping("/health")
    public String getHealth() {
        return "Ok";
    }

    @Autowired
    private Authenticator authenticator;

    @Autowired
    private RegistrationService registrationService;

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
                                  @RequestHeader(value="redirect-uri", required = true) String redirect_uri
    ) {
        return authenticator.authenticateWithCode(token, redirect_uri);
    }

    @ApiOperation("Returns token for authorization")
    @GetMapping("/token/access_token")
    public Token resolveAccessToken(@ApiParam(value = "auth code provided by the oauth server" , required = true)
                                    @RequestParam(value=AUTHORIZATION, required = true) String token,
                                    @ApiParam(value = "used redirect uri" , required = true)
                                    @RequestParam(value="redirect_uri", required = true) String redirect_uri,
                                    @ApiParam(value = "name of the oauth service" , required = true)
                                    @RequestParam(value="oauth-server", required = true) String serverName
    ) {
        return authenticator.resolveToken(token, redirect_uri,serverName);
    }

    @ApiOperation("creates a login for user")
    @PostMapping("/login")
    public Login createLogin(
        @ApiParam(value = "User name of the user, incase of oauth login, consider user id from oauth server to be user name" , required = true)
        @RequestParam(value="user-name", required = true) String userName,
        @ApiParam(value = "password for login, user blank for oauth login" , required = true)
        @RequestParam(value="password", required = false) String password,
        @ApiParam(value = "id of the user stored in top spin" , required = true)
        @RequestParam(value="user-id", required = true) String userId
        ) {
        return registrationService.createLogin(userName, password, userId);
    }
}