package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.model.User;

/**
 * Description : Contract for persistence layer for user resource
 * Author: kumar
 * Created on : 24/11/19
 */

public interface UserPersistenceLayer {

    /**
     * Creates and returns created user
     * @param user user to be created
     * @return created user
     */
    User createUser(User user);

    /**
     * Updates and returns updated user
     * @param user user to be updated
     * @return updated user
     */
    User updateUser(User user);

    /**
     * fetches user
     * @param userId id of the user
     * @return fetched user
     */
    User getUser(String userId);

    /**
     * Fetches user by user name
     * @param userName
     * @return fetched user
     */
    User getUserByUserName(String userName);

    /**
     * Deletes user
     * @param userId
     */
    void deleteUser(String userId);
}