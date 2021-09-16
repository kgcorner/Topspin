package com.kgcorner.topspin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class OfferServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OfferServiceApplication.class, args);
    }
}