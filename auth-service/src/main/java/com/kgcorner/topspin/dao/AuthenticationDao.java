package com.kgcorner.topspin.dao;

/*
Description : <Write class Description>
Author: kumar
Created on : 26/08/19
*/



import com.kgcorner.dao.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class AuthenticationDao<T extends Serializable> extends MongoRepository<T> {}