package com.kgcorner.topspin.util;


import com.kgcorner.topspin.model.DummyUser;
import com.kgcorner.topspin.model.User;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/02/20
 */

public class UserServiceTestUtility {
    public static User createDummyUser() {
        User user = new DummyUser();
        user.setId("0");
        user.setName("Gaurav");
        return user;
    }

    public static User createDummyUser(String id, String name) {
        User user = new DummyUser();
        user.setId(id);
        user.setName(name);
        return user;
    }
}