package com.kgcorner.topspin.resources;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 06/05/21
 */

@RestController
public class HealthResource {
    @GetMapping("/")
    public String ok() {
        return "ok";
    }
}