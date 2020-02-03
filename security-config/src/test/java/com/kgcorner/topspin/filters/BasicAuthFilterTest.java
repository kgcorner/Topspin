package com.kgcorner.topspin.filters;

import com.kgcorner.topspin.model.BasicAuthToken;
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
import java.util.Base64;

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
public class BasicAuthFilterTest {

    private BasicAuthFilter basicAuthFilter;
    private FilterChain mockedFilterChain;
    private HttpServletRequest mockedRequest;
    private static final String AUTHENTICATION_HEADER = "Authorization";
    private SecurityContext mockedSecurityContext;

    @Before
    public void setUp() throws Exception {
        basicAuthFilter = new BasicAuthFilter();
        mockedRequest = PowerMockito.mock(HttpServletRequest.class);
        mockedSecurityContext = PowerMockito.mock(SecurityContext.class);
        mockedFilterChain = PowerMockito.mock(FilterChain.class);
        mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(mockedSecurityContext);
    }

    @Test
    public void doFilterWithHeader() {
        when(mockedRequest.getHeader(AUTHENTICATION_HEADER)).thenReturn("BASIC " + new String(Base64.getEncoder().encode("a:b".getBytes())));
        try {
            basicAuthFilter.doFilter(mockedRequest, null, mockedFilterChain);
        } catch (Exception x) {
            fail();
        }
        verify(mockedSecurityContext).setAuthentication(ArgumentMatchers.any(BasicAuthToken.class));
        verifyNoMoreInteractions(mockedSecurityContext);
    }

    @Test
    public void doFilterWithoutHeader() {
        try {
            basicAuthFilter.doFilter(mockedRequest, null, mockedFilterChain);
        } catch (Exception x) {
            fail();
        }
        verifyZeroInteractions(mockedSecurityContext);

    }

    @Test
    public void doFilterInvalidHeader() {
        when(mockedRequest.getHeader(AUTHENTICATION_HEADER)).thenReturn("BASIC invalid");
        try {
            basicAuthFilter.doFilter(mockedRequest, null, mockedFilterChain);
        } catch (Exception x) {
            fail();
        }
        verifyZeroInteractions(mockedSecurityContext);
    }
}