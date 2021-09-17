package com.kgcorner.topspin.security;

import org.junit.Assert;
import org.junit.Test;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/09/21
 */

public class OfferServiceSecurityTest {

    private OfferServiceSecurity serviceSecurity = new OfferServiceSecurity();


    @Test
    public void getPublicUrl() {
        Assert.assertEquals(1, serviceSecurity.getPublicUrl().length);
    }

    @Test
    public void getAuthenticatedUrl() {
        Assert.assertEquals(1, serviceSecurity.getAuthenticatedUrl().size());
    }
}