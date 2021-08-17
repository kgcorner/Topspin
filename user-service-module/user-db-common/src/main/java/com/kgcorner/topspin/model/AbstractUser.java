package com.kgcorner.topspin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/08/21
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractUser {
    private String id;
    private String userName;
    private String name;
    private String email;
    private String contact;
    private String others;
    private String gender;
    private boolean active;
}