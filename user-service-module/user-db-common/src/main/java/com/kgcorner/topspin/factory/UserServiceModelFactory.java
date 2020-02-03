package com.kgcorner.topspin.factory;


import com.kgcorner.topspin.model.User;

/**
 * Description : Model Factory for User service
 * Author: kumar
 * Created on : 24/11/19
 */

public interface UserServiceModelFactory {
    /**
     * Returns an instance of User model
     * @return
     */
    User createUserModel();
}