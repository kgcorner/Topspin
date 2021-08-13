package com.kgcorner.topspin;


import com.kgcorner.topspin.model.ApplicationRequestCredentials;
import com.kgcorner.utils.Strings;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 12/08/21
 */

public class ApplicationAwareFilter extends HttpFilter {
    private static final String APPLICATION_NAME_HEADER = "X-Application-Name";
    private static final String APPLICATION_HASH_HEADER = "X-Application-Hash";
    private static final String APPLICATION_REQUEST_TIME_HEADER = "X-Requested-At";


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {
        String appName = req.getHeader(APPLICATION_NAME_HEADER);
        String appHash = req.getHeader(APPLICATION_HASH_HEADER);
        String requestedAt = req.getHeader(APPLICATION_REQUEST_TIME_HEADER);
        if(Strings.isNullOrEmpty(appHash) || Strings.isNullOrEmpty(appName) || Strings.isNullOrEmpty(requestedAt)) {
            chain.doFilter(req, res);
            return;
        } else {
            ApplicationRequestCredentials credentials = new ApplicationRequestCredentials(appName,
                null, requestedAt, appHash, false);
            SecurityContextHolder.getContext().setAuthentication(credentials);
            chain.doFilter(req, res);
        }
    }
}