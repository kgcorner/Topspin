package com.kgcorner.topspin.filters;

import com.kgcorner.topspin.model.BearerAuthToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 02/12/19
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityContextHolder.class)
public class BearerAuthFilterTest {

    private FilterChain mockedFilterChain;
    private HttpServletRequest mockedRequest;
    private static final String AUTHENTICATION_HEADER = "Authorization";
    private SecurityContext mockedSecurityContext;
    private BearerAuthFilter bearerAuthFilter;

    @Before
    public void setUp() throws Exception {
        bearerAuthFilter = new BearerAuthFilter();
        mockedRequest = PowerMockito.mock(HttpServletRequest.class);
        mockedSecurityContext = PowerMockito.mock(SecurityContext.class);
        mockedFilterChain = PowerMockito.mock(FilterChain.class);
        mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(mockedSecurityContext);

    }

    @Test
    public void doFilterWithHeader() {
        when(mockedRequest.getHeader(AUTHENTICATION_HEADER)).thenReturn("BEARER jshdadhakhdashdkj");
        try {
            bearerAuthFilter.doFilter(mockedRequest, null, mockedFilterChain);
        } catch (Exception x) {
            fail();
        }
        verify(mockedSecurityContext).setAuthentication(ArgumentMatchers.any(BearerAuthToken.class));
        verifyNoMoreInteractions(mockedSecurityContext);
    }

    @Test
    public void doFilterWithoutHeader() {
        try {
            bearerAuthFilter.doFilter(mockedRequest, null, mockedFilterChain);
        } catch (Exception x) {
            fail();
        }
        verifyZeroInteractions(mockedSecurityContext);

    }

    @Test
    public void doFilterInvalidHeader() {
        when(mockedRequest.getHeader(AUTHENTICATION_HEADER)).thenReturn("invalid");
        try {
            bearerAuthFilter.doFilter(mockedRequest, null, mockedFilterChain);
        } catch (Exception x) {
            fail();
        }
        verifyZeroInteractions(mockedSecurityContext);
    }
}