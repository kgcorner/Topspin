package com.kgcorner.topspin.model.factory;

/*
Description : Contract for Model factory
Author: kumar
Created on : 22/10/19
*/

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.Token;

public interface AuthServiceModelFactory {
    Login createNewLogin();
    Token createNewToken();
}