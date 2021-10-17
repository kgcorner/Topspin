package com.kgcorner.topspin;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/10/21
 */
@RestController
public class HealthResource {
    @GetMapping("/ok")
    public ResponseEntity getHealth() {
        return ResponseEntity.ok().build();
    }
}