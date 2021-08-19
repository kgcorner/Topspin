package com.kgcorner.topspin.dao;


import com.kgcorner.dao.MongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description : Mongo repository for {@link com.kgcorner.topspin.model.TransactionModel} model
 * Author: kumar
 * Created on : 24/11/19
 */

@Component
public class MongoTransactionDao<T extends Serializable> extends MongoRepository<T> {
    public MongoTransactionDao(MongoTemplate template) {
        this.template = template;
    }

}