package com.kgcorner.topspin.model.factory;

import com.kgcorner.topspin.model.User;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : Test for {@link UserServiceModelFactory}
 * Author: kumar
 * Created on : 24/11/19
 */

public class UserServiceMongoModelFactoryTest {

    @Test
    public void createUserModel() {
        UserServiceModelFactory factory = new UserServiceMongoModelFactory();
        User user = factory.createUserModel();
        assertNotNull(user);
    }
}