package com.kgcorner.topspin.dao;


import com.kgcorner.dao.MySqlRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 07/06/21
 */
@Component
public class MysqlDao<T extends Serializable> extends MySqlRepository<T> {
}