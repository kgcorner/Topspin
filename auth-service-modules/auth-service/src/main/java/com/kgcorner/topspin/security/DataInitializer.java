package com.kgcorner.topspin.security;


import com.kgcorner.topspin.persistent.LoginPersistentLayer;
import com.kgcorner.topspin.service.RegistrationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/10/21
 */
@Configuration
public class DataInitializer {

    private static final Logger LOGGER = Logger.getLogger(DataInitializer.class);
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private LoginPersistentLayer loginPersistentLayer;

    @EventListener(ApplicationReadyEvent.class)
    public void createAdminUser() {
        if(loginPersistentLayer.getLogin("admin") == null) {
            LOGGER.info("Creating admin account");
            registrationService.createAdmin("admin", "admin", "thisisfirstaccount");
        } else {
            LOGGER.info("Service already has admin account");
        }
    }
}