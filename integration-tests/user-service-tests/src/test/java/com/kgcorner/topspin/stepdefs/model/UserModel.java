package com.kgcorner.topspin.stepdefs.model;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 20/02/20
 */

public class UserModel {
    private String id;
    private String userName;
    private String name;
    private String email;
    private String contact;
    private String others;
    private boolean active;

    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }

    
    public String getUserName() {
        return userName;
    }

    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    
    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    
    public boolean isActive() {
        return active;
    }

    
    public void setActive(boolean active) {
        this.active = active;
    }
}