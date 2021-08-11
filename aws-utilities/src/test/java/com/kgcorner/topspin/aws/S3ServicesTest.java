package com.kgcorner.topspin.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kgcorner.topspin.aws.exceptions.AwsServiceException;
import com.kgcorner.topspin.aws.util.EnvironmentUtility;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 11/08/21
 */

public class S3ServicesTest {

    private S3Services services;
    private String MOCK_API_KEY = "MOCKAPIKEY";
    private String MOCK_API_SECRET = "MOCKAPISECRET";
    private AmazonS3Client amazonS3Client;

    @Before
    public void setUp() throws Exception {
        EnvironmentUtility.setEnvironmentValue("AWS_API_KEY", MOCK_API_KEY);
        EnvironmentUtility.setEnvironmentValue("AWS_API_SECRET", MOCK_API_SECRET);
        services = S3Services.getInstance();
        amazonS3Client = mock(AmazonS3Client.class);
        Whitebox.setInternalState(services, "amazonS3Client", amazonS3Client);
        //Whitebox.setInternalState(spy, "amazonS3Client", amazonS3Client);
    }

    @Test
    public void getInstance() {
        assertEquals(services, S3Services.getInstance());
    }

    @Test
    public void testInit() {
        try {
            services.init();
            assertEquals(services, S3Services.getInstance());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void storeImageForTest() {
        String bucketName = "testBucket";
        String fileName = "testfile";
        long size = 10000L;
        InputStream stream = S3Services.class.getResourceAsStream("application.properties");
        try {
            services.storeImage(bucketName, fileName, stream, size);
            Mockito.verify(amazonS3Client).putObject(Matchers.any(PutObjectRequest.class));
        } catch (AwsServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void storeImageForTestFailed() {
        String bucketName = "testBucket";
        String fileName = "testfile";
        long size = 10000L;
        InputStream stream = S3Services.class.getResourceAsStream("application.properties");
        String testMessage = "testMessage";
        try {
            when(amazonS3Client.putObject(Matchers.any(PutObjectRequest.class)))
                .thenThrow(new RuntimeException(testMessage));
            services.storeImage(bucketName, fileName, stream, size);
            fail("Method should throw exception");
        } catch (AwsServiceException e) {
            assertEquals(testMessage, e.getLocalizedMessage());
        }
    }

    @Test
    public void deleteImage() {
        String bucketName = "testBucket";
        String fileName = "testfile";
        services.deleteImage(bucketName, fileName);
        Mockito.verify(amazonS3Client).deleteObject(Matchers.any(DeleteObjectRequest.class));
    }
    @Test
    public void sanitizeFileName() {
        String fileName = "abc def% anc, amcn";
        String result = services.sanitizeFileName(fileName);
        String expected = "abc-def--anc--amcn";
        assertEquals(expected, result);

    }
}