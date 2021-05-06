package com.kgcorner.topspin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/05/21
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProductOfferServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductOfferServiceApplication.class, args);
    }
}