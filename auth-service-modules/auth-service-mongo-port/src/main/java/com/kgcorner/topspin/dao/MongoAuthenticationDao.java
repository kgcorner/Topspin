package com.kgcorner.topspin.dao;

/*
Description : <Write class Description>
Author: kumar
Created on : 26/08/19
*/



import com.kgcorner.dao.MongoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class MongoAuthenticationDao<T extends Serializable> extends MongoRepository<T> {
    public MongoAuthenticationDao(MongoTemplate template) {
        this.template = template;
    }
}