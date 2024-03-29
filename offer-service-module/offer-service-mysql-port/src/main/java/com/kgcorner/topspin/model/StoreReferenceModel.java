package com.kgcorner.topspin.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */
@Entity
@Table(name="STORE")
public class StoreReferenceModel extends StoreRef implements Serializable {

    @Id
    @Column(name ="ID")
    @Override
    public String getId() {
        return super.getId();
    }

    @Column(name ="NAME")
    @Override
    public String getName() {
        return super.getName();
    }

    @Column(name ="DESCRIPTION")
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Column(name ="MAX_CASHBACK")
    @Override
    public String getMaxCashback() {
        return super.getMaxCashback();
    }
}