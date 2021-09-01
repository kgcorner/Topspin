package com.kgcorner.topspin.service;

/*
Description : Responsible for registering Login for user
Author: kumar
Created on : 28/08/19
*/

import com.kgcorner.crypto.Hasher;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.persistent.LoginPersistentLayer;
import com.kgcorner.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private LoginPersistentLayer loginPersistentLayer;

    @Autowired
    private AuthServiceModelFactory authServiceModelFactory;

    @Autowired
    private Properties properties;

    /**
     * Creates a login for given user
     * @param userName username of the user
     * @param password password given by user
     * @param userId id of the user
     * @return created login
     */
    public Login createLogin(String userName, String password, String userId) {
        return saveLogin(userName, password, userId, "USER");
    }

    public Login createLogin(String userName, String password, String userId, List<String> roles) {
        Login login = authServiceModelFactory.createNewLogin(roles);
        login.setUserId(userId);
        login.setUsername(userName);
        if(!Strings.isNullOrEmpty(password)) {
            String salt = properties.getPasswordSalt();
            login.setPassword(Hasher.getCrypt(password, salt));
        }
        return loginPersistentLayer.createLogin(login);
    }

    public Login createAdmin(String userName, String password, String userId) {
        return saveLogin(userName, password, userId, "ADMIN");
    }

    private Login saveLogin(String userName, String password, String userId, String role) {
        List<String> roles = new ArrayList<>();
        roles.add(role);
        Login existingUser = loginPersistentLayer.getLogin(userName);
        if(existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        }
        Login login = authServiceModelFactory.createNewLogin(roles);
        login.setUserId(userId);
        login.setUsername(userName);
        if(!Strings.isNullOrEmpty(password)) {
            String salt = properties.getPasswordSalt();
            login.setPassword(Hasher.getCrypt(password, salt));
        }
        return loginPersistentLayer.createLogin(login);
    }
}