package com.kgcorner.topspin.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/11/19
 */

public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me"
            + ", X-ACCESS-TOKEN, X-DATE-TOKEN, X-USER-ID, Authorization, X-SOCIAL-TOKEN, X-SYNC-TOKEN, X-SYNC-TOKEN-PHRASE,X-LOGIN-PROVIDER");
        String method = ((HttpServletRequest) servletRequest).getMethod();
        if(method.equalsIgnoreCase("options")) {
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}