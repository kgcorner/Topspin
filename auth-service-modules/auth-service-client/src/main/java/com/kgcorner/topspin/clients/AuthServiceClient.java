package com.kgcorner.topspin.clients;


import com.kgcorner.topspin.clients.model.LoginResponse;
import com.kgcorner.topspin.clients.model.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description : Feign Client for authentication service
 * Author: kumar
 * Created on : 25/11/19
 */

@FeignClient("auth-service")
public interface AuthServiceClient {
    String AUTHORIZATION = "Authorization";

    /**
     * Authenticate and return Access token
     * @param token
     * @return
     */
    @GetMapping("/token")
    TokenResponse getToken(@RequestHeader(value=AUTHORIZATION, required = true) String token);

    /**
     * Authenticate given access token against given oAuth Service and returns access token
     * @param token
     * @param serverName
     * @return
     */
    @GetMapping("/token/access_token")
    TokenResponse getTokenForOAuth(@RequestHeader(value=AUTHORIZATION, required = true) String token,
                                   @RequestHeader(value="server-name", required = true) String serverName);

    /**
     * authenticate give auth code against given oath service, resolves access token
     * and returns access token generated for topspin
     * @param token
     * @param redirectUri
     * @param serverName
     * @return
     */
    @GetMapping("/token/oauth/code")
    TokenResponse resolveAccessToken(@RequestHeader(value=AUTHORIZATION, required = true) String token,
                                     @RequestHeader(value="redirect-uri", required = true) String redirectUri,
                                     @RequestHeader(value="oauth-server", required = true) String serverName);

    /**
     * Creates login resource 
     * @param userName
     * @param password
     * @param userId
     * @return
     */
    @PostMapping("/login")
    LoginResponse createLogin(
        @RequestParam(value="user-name", required = true) String userName,
        @RequestParam(value="password", required = false) String password,
        @RequestParam(value="user-id", required = true) String userId
    );
}