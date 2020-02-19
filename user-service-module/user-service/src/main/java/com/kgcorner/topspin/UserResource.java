package com.kgcorner.topspin;

import com.kgcorner.topspin.model.User;

import java.util.List;

/**
 * Description : Defines user's resources api's contract
 * Author: kumar
 * Created on : 23/11/19
 */

public interface UserResource {

    /**
     * Returns given user
     * @param userId id of the user
     * @return found user
     */
    User getUser(String userId);

    /**
     * Returns all users in paginated form
     * @param page page number
     * @param maxPerPage number of user in one page, default is 30
     * @return list of all users
     */
    List<User> getAllUsers(int page, int maxPerPage);

    /**
     * Creates a new user with given details
     * @param name
     * @param userName
     * @param email
     * @param contact
     * @param other
     * @return
     */
    User createUser(String name, String userName, String email, String contact, String other);

}