package com.kgcorner.topspin.model;


import java.io.Serializable;

/**
 * Description : User model
 * Author: kumar
 * Created on : 23/11/19
 */

public interface User extends Serializable {

    /**
     * Returns users id
     * @return
     */
    String getId();

    /**
     * Sets user's id
     * @param id
     */
    void setId(String id);

    /**
     * Returns user's name
     * @return
     */
    String getName();

    /**
     * Sets name
     */
    void setName(String name);

    /**
     * returns email
     * @return
     */
    String getEmail();

    /**
     * Sets email
     */
    void setEmail(String email);

    /**
     * Sets username, also used for authentication
     * @param userName
     */
    void setUserName(String userName);

    /**
     * Returns user's username
     * @return
     */
    String getUserName();

    /**
     * returns contactInfo
     * @return
     */
    String getContact();

    /**
     * Sets contact info
     */
    void setContact(String contact);

    /**
     * Sets other user related infos
     * @param other
     */
    void setOthers(String other);

    /**
     * Gets others user related infos
     * @return
     */
    String getOthers();

    /**
     * Sets active state of the user
     * @param active
     */
    void setActive(boolean active);

    /**
     * Returns active state of the user
     * @return
     */
    boolean isActive();
}