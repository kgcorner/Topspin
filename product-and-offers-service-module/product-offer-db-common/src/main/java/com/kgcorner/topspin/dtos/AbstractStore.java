package com.kgcorner.topspin.dtos;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public abstract class AbstractStore implements Store {
    private String id;
    private String name;
    private String description;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}