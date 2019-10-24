package com.kgcorner.topspin.model.factory;

/*
Description : Implementation of AuthServiceModelFactory
Author: kumar
Created on : 22/10/19
*/

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.LoginModel;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.TokenModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceMongoModelFactory implements AuthServiceModelFactory {

    @Override
    public Login createNewLogin() {
        return new LoginModel();
    }

    @Override
    public Token createNewToken() {
        return new TokenModel();
    }
}