package com.kgcorner.topspin;

import feign.RequestTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/08/21
 */

public class ApplicationRequestInterceptorTest {


    @Test
    public void apply() {
        String appName = "name";
        String appKey = "key";
        String appSecret = "secret";
        RequestTemplate template = new RequestTemplate();

        ApplicationRequestInterceptor interceptor = new ApplicationRequestInterceptor();
        Whitebox.setInternalState(interceptor, "applicationName", appName);
        Whitebox.setInternalState(interceptor, "applicationKey", appKey);
        Whitebox.setInternalState(interceptor, "applicationSecret", appSecret);
        interceptor.apply(template);
        Assert.assertEquals(3, template.headers().size());
    }
}