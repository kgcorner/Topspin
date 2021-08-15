package com.kgcorner.topspin.model;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/02/20
 */

public class DummyUser implements User {
    private String id;
    private String userName;
    private String name;
    private String email;
    private String contact;
    private String others;
    private boolean active;
    private String gender;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getContact() {
        return contact;
    }

    @Override
    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String getOthers() {
        return others;
    }

    @Override
    public void setOthers(String others) {
        this.others = others;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }
}