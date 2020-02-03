package com.kgcorner.topspin.dao;


import com.kgcorner.dao.MongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description : Mongo repository for {@link com.kgcorner.topspin.model.User} model
 * Author: kumar
 * Created on : 24/11/19
 */

@Component
public class MongoUserDao<T extends Serializable> extends MongoRepository<T> {
    public MongoUserDao(MongoTemplate template) {
        this.template = template;
    }

}