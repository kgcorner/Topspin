package com.kgcorner.topspin.dao;

/*
Description : <Write class Description>
Author: kumar
Created on : 26/08/19
*/



import com.kgcorner.dao.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class AuthenticationDao<T extends Serializable> extends MongoRepository<T> {


    public AuthenticationDao(MongoTemplate template) {
        this.template = template;
    }
}