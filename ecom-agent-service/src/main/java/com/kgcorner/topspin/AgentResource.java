package com.kgcorner.topspin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
Description : <Write class Description>
Author: kumar
Created on : 9/8/19
*/
@RestController
public class AgentResource {
    @GetMapping("/health")
    public String getHealth() {
        return "Ok";
    }
}