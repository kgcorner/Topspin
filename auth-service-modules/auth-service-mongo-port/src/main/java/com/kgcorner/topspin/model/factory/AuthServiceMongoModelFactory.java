package com.kgcorner.topspin.model.factory;


import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.LoginModel;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.model.TokenModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of AuthServiceModelFactory
 */
@Component
public class AuthServiceMongoModelFactory implements AuthServiceModelFactory {

    @Override
    public Login createNewLogin(List<String> roles) {
        LoginModel loginModel = new LoginModel();
        if(roles != null && roles.isEmpty()) {
            for(String role : roles) {
                loginModel.addRole(role);
            }
        }

        return loginModel;
    }

    @Override
    public Token createNewToken() {
        return new TokenModel();
    }
}