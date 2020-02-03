package com.kgcorner.topspin.filters;

/*
Description : Implementation of bearer token
Author: kumar
Created on : 3/2/19
*/

import com.kgcorner.topspin.model.BearerAuthToken;
import com.kgcorner.topspin.model.SCHEMES;
import com.kgcorner.utils.Strings;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BearerAuthFilter implements Filter {

    private static final String AUTHENTICATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = ((HttpServletRequest)request).getHeader(AUTHENTICATION_HEADER);
        if(Strings.isNullOrEmpty(authHeader) || !authHeader.toUpperCase().startsWith(SCHEMES.BEARER)) {
            chain.doFilter(request, response);
            return;
        }
        String bearerTokenStr = authHeader.substring(SCHEMES.BEARER.length() + 1);
        BearerAuthToken token = new BearerAuthToken(null, bearerTokenStr);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
}