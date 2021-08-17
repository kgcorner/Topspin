package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MongoUserDao;
import com.kgcorner.topspin.model.AbstractUser;
import com.kgcorner.topspin.model.UserModel;
import com.kgcorner.utils.Strings;
import org.springframework.beans.BeanUtils;
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
    public AbstractUser createUser(AbstractUser user) {
        Assert.notNull(user, "User can't be null");
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        return dao.create(userModel);
    }

    @Override
    public AbstractUser updateUser(AbstractUser user, String userId) {
        Assert.notNull(user, "User can't be null");
        Assert.notNull(userId, "UserId can't be null");
        UserModel userModel = (UserModel) getUser(userId);
        Assert.notNull(userModel, "No such user exists");
        BeanUtils.copyProperties(user, userModel);
        userModel.setId(userId);
        return dao.update(userModel);
    }

    @Override
    public AbstractUser getUser(String userId) {
        if(Strings.isNullOrEmpty(userId))
            throw new IllegalArgumentException("User id can't be null or empty");
        return dao.getById(userId, UserModel.class);
    }

    @Override
    public AbstractUser getUserByUserName(String userName) {
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
    public List<AbstractUser> getUsers(int page, int max) {
        if(max == 0)
            return Collections.emptyList();
        List<UserModel> all = dao.getAll(page, max, UserModel.class);
        List<AbstractUser> users = new ArrayList<>();
        for (AbstractUser user: all) {
            users.add(user);
        }
        return users;
    }
}