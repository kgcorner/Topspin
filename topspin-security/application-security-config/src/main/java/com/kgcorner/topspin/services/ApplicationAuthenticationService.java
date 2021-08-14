package com.kgcorner.topspin.services;

import com.kgcorner.topspin.model.ApplicationRequestCredentials;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 12/08/21
 */

public interface ApplicationAuthenticationService {
    /**
     * Validates the request and return the object of ApplicationRequestCredentials Object
     * @param appName name of the application
     * @param hash bcrypt Hash of the {appName}{appKey}{requestedAt} using app secret as salt
     * @param requestedAt time when the request was made
     * @return
     */
    ApplicationRequestCredentials validateRequest(String appName, String hash, String requestedAt);
}