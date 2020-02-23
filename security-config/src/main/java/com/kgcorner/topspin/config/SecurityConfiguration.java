package com.kgcorner.topspin.config;


import com.kgcorner.topspin.filters.BasicAuthFilter;
import com.kgcorner.topspin.filters.BearerAuthFilter;
import com.kgcorner.topspin.filters.CORSFilter;
import com.kgcorner.topspin.providers.DefaultBasicTokenAuthenticationProvider;
import com.kgcorner.topspin.providers.DefaultBearerTokenAuthenticationProvider;
import com.kgcorner.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Map;

/**
 * Description : Defines Security Configuration template
 * Author: kumar
 * Created on : 26/11/19
 */

public abstract class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * Returns list of urls which can be accesses publicly
     * @return
     */
    public abstract String[] getPublicUrl();

    /**
     * Returns a map stating which url can be accessed by which role
     * @return
     */
    public abstract Map<String, String[]> getAuthenticatedUrl();

    @Autowired
    public DefaultBearerTokenAuthenticationProvider bearerTokenAuthenticationProvider;

    @Autowired
    public DefaultBasicTokenAuthenticationProvider basicTokenAuthenticationProvider;



    public ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
            getDefaultSecurity(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
            .addFilterBefore(new CORSFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new BasicAuthFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new BearerAuthFilter(), BasicAuthenticationFilter.class)
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers(getPublicUrl()).permitAll();
        Map<String, String[]> authenticatedUrl = getAuthenticatedUrl();
        if(authenticatedUrl != null && authenticatedUrl.size() > 0) {
            for(Map.Entry<String, String[]> entry : authenticatedUrl.entrySet()) {
                if(!Strings.isNullOrEmpty(entry.getKey())  && entry.getValue() != null && entry.getValue().length > 0 )
                    registry = registry.antMatchers(entry.getValue()).hasRole(entry.getKey());
            }
        }
        return registry;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(bearerTokenAuthenticationProvider)
            .authenticationProvider(basicTokenAuthenticationProvider);
    }
}