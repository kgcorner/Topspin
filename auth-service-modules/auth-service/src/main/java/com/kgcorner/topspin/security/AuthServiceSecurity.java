package com.kgcorner.topspin.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/09/21
 */
@Configuration
@Order(103)
public class AuthServiceSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new AuthSecurityCorsFilter(), BasicAuthenticationFilter.class)
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests().antMatchers("/**").permitAll()
            .antMatchers("/manage/*").hasRole("ADMIN");;
    }
}

class AuthSecurityCorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, " +
            "remember-me"
            + ", X-ACCESS-TOKEN, X-DATE-TOKEN, X-USER-ID, Authorization, X-SOCIAL-TOKEN, X-SYNC-TOKEN, " +
            "X-SYNC-TOKEN-PHRASE,X-LOGIN-PROVIDER");
        String method = ((HttpServletRequest) servletRequest).getMethod();
        if(method.equalsIgnoreCase("options")) {
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}