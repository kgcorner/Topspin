package com.kgcorner.topspin.aws;

import com.kgcorner.topspin.aws.exceptions.AwsServiceException;

import java.io.IOException;
import java.io.InputStream;

public interface AwsServices {

    static AwsServices getInstance() {
        return S3Services.getInstance();
    }

    /**
     * Stores file in amazon S3 in given bucket
     * @param bucketName name of the bucket
     * @param fileName name of the file
     * @param fileStream Inputstream of the file to be saved
     * @param fileSize size of the file to be saved
     */
    void storeImage(String bucketName, String fileName, InputStream fileStream, long fileSize) throws AwsServiceException, IOException;

    /**
     * Deletes an object from S3 bucket
     * @param bucketName
     * @param objectName
     */
    void deleteImage(String bucketName, String objectName) throws IOException;

    String sanitizeFileName(String filename);
}
