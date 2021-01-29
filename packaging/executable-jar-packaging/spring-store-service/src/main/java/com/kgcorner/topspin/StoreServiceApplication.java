package com.kgcorner.topspin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/01/21
 */

@SpringBootApplication
@EnableDiscoveryClient
public class StoreServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class, args);
    }
}