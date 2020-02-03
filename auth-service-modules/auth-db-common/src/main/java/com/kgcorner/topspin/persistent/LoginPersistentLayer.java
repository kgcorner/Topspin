package com.kgcorner.topspin.persistent;

/*
Description : Persistent layer for Login resources
Author: kumar
Created on : 24/10/19
*/

import com.kgcorner.topspin.model.Login;

public interface LoginPersistentLayer {
    /**
     * Looks for login using user name
     * @param userName of the login
     * @return persistent Login object
     */
    Login getLogin(String userName);

    /**
     * Persist given login object
     * @param login object to persist
     * @return Persisted login object
     */
    Login createLogin(Login login);

    /**
     * Updates given login object
     * @param login
     */
    void update(Login login);
}