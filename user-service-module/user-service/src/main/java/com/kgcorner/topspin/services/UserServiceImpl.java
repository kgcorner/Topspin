package com.kgcorner.topspin.services;


import com.kgcorner.topspin.model.User;
import com.kgcorner.topspin.persistence.UserPersistenceLayer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 02/12/19
 */

public class UserServiceImpl implements UserService {


    @Autowired
    private UserPersistenceLayer userPersistenceLayer;

    @Override
    public User getUser(String userId) {
        return userPersistenceLayer.getUser(userId);
    }

    @Override
    public List<User> getAllUsers(int page, int maxItems) {
        return (List<User>) userPersistenceLayer.getUsers(page, maxItems);
    }
}