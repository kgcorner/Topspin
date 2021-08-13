package com.kgcorner.topspin.services;


import com.kgcorner.crypto.Hasher;
import com.kgcorner.exceptions.ForbiddenException;
import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.model.ApplicationRequestCredentials;
import com.kgcorner.utils.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : Validates the request where the records are written to a file
 * Author: kumar
 * Created on : 12/08/21
 */
@Service
public class FileSourceApplicationAuthenticationService implements ApplicationAuthenticationService {

    @Value("${application.credential.file}")
    private String applicationCredentialFile;

    private Map<String, String> applications;
    private static final Object lock = new Object();

    /**
     * initializes the application map
     */
    private void init() throws IOException {
        if(Strings.isNullOrEmpty(applicationCredentialFile))
            throw new IllegalStateException("Credential file location is missing");
        File file  = new File(applicationCredentialFile);
        if(!file.exists())
            throw new IllegalStateException("Credential file location is missing");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String applicationDetails = null;
        applications = new HashMap<>();
        while ((applicationDetails = bufferedReader.readLine()) != null) {
            String[] appKeyNamePair = applicationDetails.split("-");
            String appName = appKeyNamePair[0];
            String appKeySecretPair = appKeyNamePair[1];
            applications.put(appName, appKeySecretPair);
        }
        bufferedReader.close();
    }

    @Override
    public ApplicationRequestCredentials validateRequest(String appName, String hash, String requestedAt) {
        if(applications == null) {
            synchronized (lock) {
                try {
                    init();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        String appKeySecretPair = applications.get(appName);
        if(Strings.isNullOrEmpty(appKeySecretPair))
            throw new ResourceNotFoundException("App not found");
        String[] pair = appKeySecretPair.split(":");
        String key = pair[0];
        String secret = pair[1];
        String payload = String.format("%s%s%s",appName, key, requestedAt);
        if(!Hasher.checkPassword(payload, hash)) {
            throw new ForbiddenException("Invalid credentials");
        }
        return new ApplicationRequestCredentials(appName, key, requestedAt, hash, true);
    }
}