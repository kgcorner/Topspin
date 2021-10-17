package com.kgcorner.topspin;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/10/21
 */

public class HealthResourceTest {

    @Test
    public void getHealth() {
        ResponseEntity health = new HealthResource().getHealth();
        Assert.assertEquals(HttpStatus.OK, health.getStatusCode());
    }
}