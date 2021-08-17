package com.kgcorner.topspin.model;


import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Description : Represents UserModel
 * Author: kumar
 * Created on : 24/11/19
 */

public class UserModel extends AbstractUser implements Serializable {

    @Id
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}