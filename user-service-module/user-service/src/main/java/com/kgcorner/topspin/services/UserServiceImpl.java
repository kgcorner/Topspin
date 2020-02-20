package com.kgcorner.topspin.services;


import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.factory.UserServiceModelFactory;
import com.kgcorner.topspin.model.User;
import com.kgcorner.topspin.persistence.UserPersistenceLayer;
import com.kgcorner.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 02/12/19
 */

public class UserServiceImpl implements UserService {
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

    @Autowired
    private UserPersistenceLayer userPersistenceLayer;

    @Autowired
    private UserServiceModelFactory userServiceModelFactory;

    @Override
    public User getUser(String userId) {
        return userPersistenceLayer.getUser(userId);
    }

    @Override
    public List<User> getAllUsers(int page, int maxItems) {
        return (List<User>) userPersistenceLayer.getUsers(page, maxItems);
    }

    @Override
    public User createUser(String name, String userName, String email, String contact, String other) {
        if(!email.matches(EMAIL_PATTERN))
            throw new IllegalArgumentException("Invalid email");
        if(Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("Name can't be blank");
        if(Strings.isNullOrEmpty(userName))
            throw new IllegalArgumentException("Username can't be blank");

        User user = userServiceModelFactory.createUserModel();
        user.setName(name);
        user.setActive(true);
        user.setContact(contact);
        user.setEmail(email);
        user.setOthers(other);
        user.setUserName(userName);
        return userPersistenceLayer.createUser(user);
    }

    @Override
    public User updateUser(String id, String name, String email, String contact, String other) {
        User user = userPersistenceLayer.getUser(id);
        if(user == null)
            throw new ResourceNotFoundException("No such user exists");
        if(!Strings.isNullOrEmpty(name))
            user.setName(name);
        if(!Strings.isNullOrEmpty(email)) {
            if(email.matches(EMAIL_PATTERN)) {
                user.setEmail(email);
            } else {
                throw new IllegalArgumentException("Invalid email");
            }
        }
        if(!Strings.isNullOrEmpty(contact))
            user.setContact(contact);
        if(!Strings.isNullOrEmpty(other))
            user.setOthers(other);
        return userPersistenceLayer.updateUser(user);
    }
}