package com.kgcorner.topspin.dtos;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public abstract class AbstractCategory implements Category {
    private String name;
    private String description;
    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}