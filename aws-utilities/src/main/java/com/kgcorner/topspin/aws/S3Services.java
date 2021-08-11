package com.kgcorner.topspin.aws;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.kgcorner.topspin.aws.exceptions.AwsServiceException;
import com.kgcorner.utils.EnvironmentVariableSanityChecker;
import org.apache.http.util.Asserts;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/08/21
 */

public class S3Services implements AwsServices {
    private static final Logger LOGGER = Logger.getLogger(S3Services.class);
    private AmazonS3Client amazonS3Client = null;
    private Properties properties;
    private static final S3Services INSTANCE = new S3Services();

    public static S3Services getInstance() {
        if(INSTANCE.amazonS3Client == null) {
            try {
                INSTANCE.init();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return INSTANCE;
    }

    public void init() throws IOException {
        if(amazonS3Client == null) {
            properties = new Properties();
            properties.load(S3Services.class.getResourceAsStream("/application.properties"));
            Asserts.check(EnvironmentVariableSanityChecker.checkForAwsApiKey(System.getenv("AWS_API_KEY")),
                "invalid aws api key found ");
            String appID = System.getenv("AWS_API_KEY");
            Asserts.check(EnvironmentVariableSanityChecker.checkForAwsApiSecret(System.getenv("AWS_API_KEY")),
                "invalid aws api secret found ");
            String secret = System.getenv("AWS_API_SECRET");
            AWSCredentials credentials = new BasicAWSCredentials(appID, secret);
            amazonS3Client = new AmazonS3Client(credentials);
        }
    }

    @Override
    public void storeImage(String bucketName, String fileName, InputStream fileStream, long fileSize)
        throws AwsServiceException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileSize);
        objectMetadata.setCacheControl("max-age=2592000");
        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, fileStream, objectMetadata);
            PutObjectResult putObjectResult = amazonS3Client.putObject(request);
            if(LOGGER.isDebugEnabled()) {
                if(putObjectResult != null) {
                    LOGGER.info("ETAG:"+putObjectResult.getETag()+" expiration time:"+putObjectResult.getExpirationTime());
                }
            }
        } catch (Exception x) {
            throw new AwsServiceException(x.getLocalizedMessage());
        }
    }

    @Override
    public void deleteImage(String bucketName, String objectName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, objectName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    @Override
    public String sanitizeFileName(String filename) {
        filename = filename.replaceAll("[^a-zA-Z0-9\\-\\.]","-");
        return filename;
    }
}