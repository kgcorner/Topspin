package com.kgcorner.topspin;

/*
Description : <Write class Description>
Author: kumar
Created on : 9/8/19
*/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResource {
    @GetMapping("/health")
    public String getHealth() {
        return "Ok";
    }
}