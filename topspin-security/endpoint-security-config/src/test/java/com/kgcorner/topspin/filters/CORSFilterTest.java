package com.kgcorner.topspin.filters;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 02/12/19
 */

public class CORSFilterTest {

    private CORSFilter corsFilter;
    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;
    private FilterChain mockedFilterChain;

    @Before
    public void setUp() throws Exception {
        corsFilter = new CORSFilter();
        mockedFilterChain = PowerMockito.mock(FilterChain.class);
        mockedRequest = PowerMockito.mock(HttpServletRequest.class);
        mockedResponse = PowerMockito.mock(HttpServletResponse.class);
    }

    @Test
    public void doFilter() {
        when(mockedRequest.getMethod()).thenReturn("GET");
        try {
            corsFilter.doFilter(mockedRequest, mockedResponse, mockedFilterChain);
            verify(mockedFilterChain).doFilter(mockedRequest, mockedResponse);
        } catch (Exception x) {
            fail();
        }
    }

    @Test
    public void doFilterForOption() {
        when(mockedRequest.getMethod()).thenReturn("OPTIONS");
        try {
            corsFilter.doFilter(mockedRequest, mockedResponse, mockedFilterChain);
            verify(mockedFilterChain, times(0)).doFilter(mockedRequest, mockedResponse);
        } catch (Exception x) {
            fail();
        }
    }
}