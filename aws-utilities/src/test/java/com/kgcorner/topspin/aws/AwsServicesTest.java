package com.kgcorner.topspin.aws;

import com.kgcorner.topspin.aws.util.EnvironmentUtility;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 11/08/21
 */

public class AwsServicesTest {
    private S3Services services;
    private String MOCK_API_KEY = "MOCKAPIKEY";
    private String MOCK_API_SECRET = "MOCKAPISECRET";

    @Before
    public void setUp() {
        EnvironmentUtility.setEnvironmentValue("AWS_API_KEY", MOCK_API_KEY);
        EnvironmentUtility.setEnvironmentValue("AWS_API_SECRET", MOCK_API_SECRET);
    }
    @Test
    public void getInstance() {
        assertEquals(AwsServices.getInstance(), AwsServices.getInstance());
    }
}