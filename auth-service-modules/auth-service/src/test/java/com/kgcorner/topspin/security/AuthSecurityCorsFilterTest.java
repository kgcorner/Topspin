package com.kgcorner.topspin.security;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 21/09/21
 */

public class AuthSecurityCorsFilterTest {

    @Test
    public void doFilter() throws IOException, ServletException {
        ServletRequest servletRequest = mock(HttpServletRequest.class);
        ServletResponse servletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(((HttpServletRequest)servletRequest).getMethod()).thenReturn("options");
        AuthSecurityCorsFilter filter = new AuthSecurityCorsFilter();
        filter.doFilter(servletRequest, servletResponse, filterChain);
        Mockito.verifyNoMoreInteractions(filterChain);
    }

    @Test
    public void doFilterWithGetRequest() throws IOException, ServletException {
        ServletRequest servletRequest = mock(HttpServletRequest.class);
        ServletResponse servletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(((HttpServletRequest)servletRequest).getMethod()).thenReturn("GET");
        AuthSecurityCorsFilter filter = new AuthSecurityCorsFilter();
        filter.doFilter(servletRequest, servletResponse, filterChain);
        Mockito.verify(filterChain).doFilter(servletRequest, servletResponse);
    }
}