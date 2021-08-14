package com.kgcorner.topspin;


import com.kgcorner.crypto.Hasher;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/08/21
 */
@Component
public class ApplicationRequestInterceptor implements RequestInterceptor {

    @Value("${topspin.application.name}")
    private String applicationName;

    @Value("${topspin.application.key}")
    private String applicationKey;

    @Value("${topspin.application.secret}")
    private String applicationSecret;

    private static final String APPLICATION_NAME_HEADER = "X-Application-Name";
    private static final String APPLICATION_HASH_HEADER = "X-Application-Hash";
    private static final String APPLICATION_REQUEST_TIME_HEADER = "X-Requested-At";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        long time = new Date().getTime();
        String payload = String.format("%s%s%s%s", applicationName, applicationKey, time, applicationSecret);
        String hash = Hasher.getCrypt(payload, applicationSecret);
        requestTemplate.header(APPLICATION_NAME_HEADER, applicationName);
        requestTemplate.header(APPLICATION_HASH_HEADER, hash);
        requestTemplate.header(APPLICATION_REQUEST_TIME_HEADER, time + "");
    }
}