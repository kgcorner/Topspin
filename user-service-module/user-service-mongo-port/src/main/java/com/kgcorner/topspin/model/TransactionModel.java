package com.kgcorner.topspin.model;


import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/08/21
 */

public class TransactionModel extends AbstractTransaction implements Serializable {
    @Id
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}