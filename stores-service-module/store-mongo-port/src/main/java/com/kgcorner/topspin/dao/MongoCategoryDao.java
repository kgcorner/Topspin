package com.kgcorner.topspin.dao;


import com.kgcorner.dao.MongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */
@Component
public class MongoCategoryDao <T extends Serializable> extends MongoRepository<T> {
    public MongoCategoryDao(MongoTemplate template) {
        this.template = template;
    }

}