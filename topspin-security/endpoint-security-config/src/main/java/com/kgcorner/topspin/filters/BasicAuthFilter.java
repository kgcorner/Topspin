package com.kgcorner.topspin.filters;

/*
Description : <Write is class Description>
Author: kumar
Created on : 3/2/19
*/

import com.kgcorner.topspin.model.BasicAuthToken;
import com.kgcorner.topspin.model.SCHEMES;
import com.kgcorner.utils.Strings;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

public class BasicAuthFilter implements Filter {
    private static final String AUTHENTICATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = ((HttpServletRequest)request).getHeader(AUTHENTICATION_HEADER);
        if(Strings.isNullOrEmpty(authHeader) || !authHeader.toUpperCase().startsWith(SCHEMES.BASIC)) {
            chain.doFilter(request, response);
            return;
        }
        String decoded = new String(Base64.getDecoder().decode(authHeader.substring(SCHEMES.BASIC.length() + 1)));
        if(decoded.split(":").length != 2) {
            chain.doFilter(request, response);
        } else {
            String username = decoded.split(":")[0];
            String password = decoded.split(":")[1];
            BasicAuthToken basicAuthToken = new BasicAuthToken(username, password);
            SecurityContextHolder.getContext().setAuthentication(basicAuthToken);
            chain.doFilter(request, response);
        }
    }
}