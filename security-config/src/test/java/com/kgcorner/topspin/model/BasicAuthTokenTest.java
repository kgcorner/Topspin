package com.kgcorner.topspin.model;

import org.junit.Assert;
import org.junit.Test;

import javax.security.auth.Subject;
import java.util.Collections;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/02/20
 */

public class BasicAuthTokenTest {

    @Test
    public void testImpliesWithUserNameAndPasswordOnly() {
        BasicAuthToken token = new BasicAuthToken("user", "password");
        Assert.assertFalse(token.implies(new Subject()));
    }

    @Test
    public void testImpliesWithUserNamePasswordAndGrants() {
        BasicAuthToken token = new BasicAuthToken("user", "password", Collections.emptyList());
        Assert.assertFalse(token.implies(new Subject()));
    }
}