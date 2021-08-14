package com.kgcorner.topspin.services;

import com.kgcorner.crypto.Hasher;
import com.kgcorner.exceptions.ForbiddenException;
import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.model.ApplicationRequestCredentials;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/08/21
 */

public class FileSourceApplicationAuthenticationServiceTest {
    private String appName = "app";
    private String appKey = "key";
    private String appSecret = "secret";
    private FileSourceApplicationAuthenticationService service;

    @Before
    public void setup() {
        try {
            Path tempFile = Files.createTempFile("app", "lst");
            File file = tempFile.toFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(String.format("%s-%s:%s", appName, appKey, appSecret).getBytes());
            fos.close();
            service = new FileSourceApplicationAuthenticationService();
            Whitebox.setInternalState(service, "applicationCredentialFile", file.getAbsolutePath());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void validateRequest() {
        String hash = Hasher.getCrypt(String.format("%s%s%s",appName, appKey, "123"),appSecret);
        ApplicationRequestCredentials credentials = service.validateRequest(appName, hash, "123");
        assertEquals(appKey, credentials.getApplicationKey());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void validateRequestBogusApp() {
        service.validateRequest("bogus", "hash", "123");
    }

    @Test(expected = ForbiddenException.class)
    public void validateRequestFails() {
        String hash = Hasher.getCrypt(String.format("%s%s%s","bogus", appKey, "123"),appSecret);
        service.validateRequest(appName, hash, "123");
    }

    @Test(expected = IllegalStateException.class)
    public void initFails() {
        Whitebox.setInternalState(service, "applicationCredentialFile", "/nofile");
        String hash = Hasher.getCrypt(String.format("%s%s%s",appName, appKey, "123"),appSecret);
        ApplicationRequestCredentials credentials = service.validateRequest(appName, hash, "123");
    }

    @Test(expected = IllegalStateException.class)
    public void initNpAPpFile() {
        Whitebox.setInternalState(service, "applicationCredentialFile", "");
        String hash = Hasher.getCrypt(String.format("%s%s%s",appName, appKey, "123"),appSecret);
        ApplicationRequestCredentials credentials = service.validateRequest(appName, hash, "123");
    }

}