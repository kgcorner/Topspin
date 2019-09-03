package com.kgcorner.topspin.service;

/*
Description : Responsible for registering Login for user
Author: kumar
Created on : 28/08/19
*/

import com.kgcorner.crypto.Hasher;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.dao.AuthenticationDao;
import com.kgcorner.topspin.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private AuthenticationDao<Login> loginAuthenticationDao;

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
        Login login = new Login();
        login.setUserId(userId);
        login.setUserName(userName);
        String salt = properties.getPasswordSalt();
        login.setPassword(Hasher.getCrypt(password, salt));
        return loginAuthenticationDao.create(login);
    }
}