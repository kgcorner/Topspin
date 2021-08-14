package com.kgcorner.topspin;

import com.kgcorner.topspin.model.ApplicationRequestCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/08/21
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityContextHolder.class)
public class ApplicationAwareFilterTest {
    private static final String APPLICATION_NAME_HEADER = "X-Application-Name";
    private static final String APPLICATION_HASH_HEADER = "X-Application-Hash";
    private static final String APPLICATION_REQUEST_TIME_HEADER = "X-Requested-At";

    private String appName = "app";
    private String hash = "hash";
    private String appRequested = "123";

    private ApplicationAwareFilter filter = null;

    @Test
    public void doFilterNoAppCall() {
        filter = new ApplicationAwareFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        mockStatic(SecurityContextHolder.class);
        SecurityContext context = mock(SecurityContext.class);
        when(SecurityContextHolder.getContext()).thenReturn(context);
        try {
            filter.doFilter(req, res, chain);
            Mockito.verifyNoMoreInteractions(context);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void doFilterWithAppHeader() {
        filter = new ApplicationAwareFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        when(req.getHeader(APPLICATION_NAME_HEADER)).thenReturn(appName);
        when(req.getHeader(APPLICATION_HASH_HEADER)).thenReturn(hash);
        when(req.getHeader(APPLICATION_REQUEST_TIME_HEADER)).thenReturn(appRequested);
        ApplicationRequestCredentials credentials = new ApplicationRequestCredentials(appName,
            null, appRequested, hash, false);
        mockStatic(SecurityContextHolder.class);
        SecurityContext context = mock(SecurityContext.class);
        when(SecurityContextHolder.getContext()).thenReturn(context);
        try {
            filter.doFilter(req, res, chain);
            Mockito.verify(context).setAuthentication(credentials);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}