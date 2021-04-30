package com.kgcorner.topspin.dao;


import com.kgcorner.dao.MySqlRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Component
public class MysqlStoreDao<T extends Serializable> extends MySqlRepository<T> {
    public MysqlStoreDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}