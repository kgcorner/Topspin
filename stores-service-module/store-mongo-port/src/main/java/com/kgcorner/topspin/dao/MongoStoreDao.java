package com.kgcorner.topspin.dao;


import com.kgcorner.dao.MongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

@Component
public class MongoStoreDao <T extends Serializable> extends MongoRepository<T> {
    public MongoStoreDao(MongoTemplate template) {
        this.template = template;
    }

}