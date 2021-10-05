package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.model.AbstractUser;

import java.util.List;

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
    AbstractUser createUser(AbstractUser user);

    /**
     * Updates and returns updated user
     * @param user user to be updated
     * @return updated user
     */
    AbstractUser updateUser(AbstractUser user, String userId);

    /**
     * fetches user
     * @param userId id of the user
     * @return fetched user
     */
    AbstractUser getUser(String userId);

    /**
     * Fetches user by user name
     * @param userName
     * @return fetched user
     */
    AbstractUser getUserByUserName(String userName);

    /**
     * Fetches user by user name
     * @param userName
     * @return fetched user
     */
    AbstractUser getUserByEmail(String userName);

    /**
     * Deletes user
     * @param userId
     */
    void deleteUser(String userId);

    /**
     * Returns list of users
     * @param page page number
     * @param max max items per page
     * @return list of user in given page
     */
    List<AbstractUser> getUsers(int page, int max);
}