package com.kgcorner.topspin.model;

/*
Description : Contract for Login
Author: kumar
Created on : 26/08/19
*/


import java.io.Serializable;

public interface Login extends Serializable, Requester {
    String USER_NAME_COLUMN = "userName";

    String getId();

    void setId(String id);

    String getUserName();

    void setUserName(String userName);

    String getPassword();

    void setPassword(String password);

    String getUserId();

    void setUserId(String userId);

    String getRefreshToken();

    void setRefreshToken(String refreshToken);

    void setLoginProvider(String loginProvider);

    void setOAuthAccessToken(String oAuthAccessToken);

    String getLoginProvider();

    String getOAuthAccessToken();
}