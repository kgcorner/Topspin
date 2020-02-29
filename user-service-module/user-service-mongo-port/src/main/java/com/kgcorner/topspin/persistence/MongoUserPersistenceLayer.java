package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MongoUserDao;
import com.kgcorner.topspin.model.User;
import com.kgcorner.topspin.model.UserModel;
import com.kgcorner.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description : Implementation of Persistence layer for mongo port
 * Author: kumar
 * Created on : 24/11/19
 */

@Repository
public class MongoUserPersistenceLayer implements UserPersistenceLayer {
    @Autowired
    private MongoUserDao<UserModel> dao;

    @Override
    public User createUser(User user) {
        Assert.notNull(user, "User can't be null");
        Assert.isTrue(user instanceof UserModel, "user isn't " +UserModel.class.getCanonicalName());
        return dao.create((UserModel) user);
    }

    @Override
    public User updateUser(User user) {
        Assert.notNull(user, "User can't be null");
        Assert.isTrue(user instanceof UserModel, "user isn't " +UserModel.class.getCanonicalName());
        return dao.update((UserModel) user);
    }

    @Override
    public User getUser(String userId) {
        if(Strings.isNullOrEmpty(userId))
            throw new IllegalArgumentException("User id can't be null or empty");
        return dao.getById(userId, UserModel.class);
    }

    @Override
    public User getUserByUserName(String userName) {
        if(Strings.isNullOrEmpty(userName))
            throw new IllegalArgumentException("userName can't be null or empty");
        return dao.getByKey("userName", userName, UserModel.class);

    }

    @Override
    public void deleteUser(String userId) {
        if(Strings.isNullOrEmpty(userId))
            throw new IllegalArgumentException("User id can't be null or empty");
        UserModel user = (UserModel) getUser(userId);
        if(user == null)
            throw new IllegalArgumentException("No user exists with id " + userId);
        dao.remove(user);
    }

    @Override
    public List<User> getUsers(int page, int max) {
        if(max == 0)
            return Collections.emptyList();
        List<UserModel> all = dao.getAll(page, max, UserModel.class);
        List<User> users = new ArrayList<>();
        for (User user: all) {
            users.add(user);
        }
        return users;
    }
}