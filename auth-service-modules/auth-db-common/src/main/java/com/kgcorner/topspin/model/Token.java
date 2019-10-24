package com.kgcorner.topspin.model;

/*
Description : contract for Token Resource Object
Author: kumar
Created on : 25/08/19
*/

import java.io.Serializable;

public interface Token extends Serializable {
    String getAccessToken();

    void setAccessToken(String accessToken);

    String getRefreshToken();

    void setRefreshToken(String refreshToken);

    int getExpiresInSeconds();

    void setExpiresInSeconds(int expiresInSeconds);
}