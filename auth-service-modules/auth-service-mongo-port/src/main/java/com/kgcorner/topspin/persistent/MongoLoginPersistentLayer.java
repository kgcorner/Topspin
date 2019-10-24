package com.kgcorner.topspin.persistent;

/*
Description : <Write class Description>
Author: kumar
Created on : 24/10/19
*/

import com.kgcorner.dao.MongoRepository;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.LoginModel;
import com.kgcorner.topspin.model.persistent.LoginPersistentLayer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoLoginPersistentLayer extends MongoRepository<LoginModel> implements LoginPersistentLayer {

    public MongoLoginPersistentLayer(MongoTemplate template) {
        this.template = template;
    }
    @Override
    public Login getLogin(String userName) {
        return getByKey(Login.USER_NAME_COLUMN, userName, LoginModel.class);
    }

    @Override
    public Login createLogin(Login login) {
        if(login instanceof LoginModel)
            return create((LoginModel) login);
        throw new IllegalArgumentException("Not LoginModel");
    }

    @Override
    public void update(Login login) {
        if(login instanceof LoginModel)
            update((LoginModel) login);
        else
            throw new IllegalArgumentException("Not LoginModel");
    }
}