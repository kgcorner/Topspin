package com.kgcorner.topspin.factory;


import com.kgcorner.topspin.model.User;
import com.kgcorner.topspin.model.UserModel;

/**
 * Description : Mongo port for model factory
 * Author: kumar
 * Created on : 24/11/19
 */

public class UserServiceMongoModelFactory implements UserServiceModelFactory {
    @Override
    public User createUserModel() {
        return new UserModel();
    }
}