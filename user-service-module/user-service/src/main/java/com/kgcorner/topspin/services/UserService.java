package com.kgcorner.topspin.services;

import com.kgcorner.topspin.model.User;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 02/12/19
 */

public interface UserService {
    /**
     * Returns given user
     * @param userId id of the user
     * @return found user
     */
    User getUser(String userId);

    /**
     * Returns all users in paginated form
     * @param page page number
     * @return list of all users
     */
    List<User> getAllUsers(int page, int maxItems);
}