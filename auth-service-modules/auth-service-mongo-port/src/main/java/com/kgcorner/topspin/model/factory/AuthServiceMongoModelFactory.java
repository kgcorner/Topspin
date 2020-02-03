package com.kgcorner.topspin.model.factory;


import com.kgcorner.topspin.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.LoginModel;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.TokenModel;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuthServiceModelFactory
 */
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