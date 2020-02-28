package com.kgcorner.topspin.stepdefs;


import com.google.gson.Gson;
import com.kgcorner.topspin.HttpUtil;
import com.kgcorner.topspin.Response;
import com.kgcorner.topspin.stepdefs.model.UserModel;
import com.kgcorner.topspin.utils.PropertiesUtil;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/02/20
 */

public class FetchUserFeature {

    private static final Logger LOGGER = Logger.getLogger(FetchUserFeature.class);

    private static final String HOST_KEY = "user.service.host";
    private static final String CREATE_USER_URL_KEY = "user.creation.url";
    private static final String GET_USER_URL = "get.user.url";
    private static final String NAME = "Gaurav";
    private static final String EMAIL = "gaurav@gaurav.com";
    private static final String USER_NAME = "gaurav";
    private static final String CONTACT = "88888888";
    private static final String OTHER = "other info";
    private String createdUserId = "0";
    private Response response;

    @Before("@FetchUserPositiveScenario")
    public void setup() {
        LOGGER.info("*************************");
        LOGGER.info("CREATING TEST USER");
        LOGGER.info("*************************");
        String host = PropertiesUtil.getValue(HOST_KEY);
        String url = host + PropertiesUtil.getValue(CREATE_USER_URL_KEY);
        Map<String, Object> data = new HashMap<>();
        data.put("name", NAME);
        data.put("username", USER_NAME);
        data.put("email", EMAIL);
        data.put("contact", CONTACT);
        data.put("other", OTHER);
        Response response = HttpUtil.doPost(url, null, data);
        if(response.getStatus() == 201) {
            UserModel user = new Gson().fromJson(response.getData(), UserModel.class);
            LOGGER.info("User created with ID " + user.getId() );
            createdUserId = user.getId();
        } else {
            LOGGER.error("Failed to crate user");
            Assert.fail("Failed to create user for test");
        }
    }

    @When("^request is made for fetching the user$")
    public void requestIsMadeForFetchingTheUser() {
        LOGGER.info("*************************");
        String host = PropertiesUtil.getValue(HOST_KEY);
        String url = host + PropertiesUtil.getValue(GET_USER_URL);
        url = String.format(url, createdUserId);
        response = HttpUtil.doGet(url, null);
        Assert.assertNotNull(response);
    }

    @Then("^server should revert with status '(\\d+)' and User details$")
    public void serverShouldRevertWithStatusAndUserDetails(int status) {
        Assert.assertEquals(response.getStatus(), status);
        UserModel user = new Gson().fromJson(response.getData(), UserModel.class);
        Assert.assertEquals(user.getName(),NAME);
        Assert.assertEquals(user.getUserName(), USER_NAME);
        Assert.assertEquals(user.getEmail(), EMAIL);
        Assert.assertEquals(user.getContact(), CONTACT);
        Assert.assertEquals(user.getOthers(), OTHER);
    }

    @When("^request is made for fetching the user with id '(\\d+)'$")
    public void requestIsMadeForFetchingTheUserWithId(int id) {
        String host = PropertiesUtil.getValue(HOST_KEY);
        String url = host + PropertiesUtil.getValue(GET_USER_URL);
        url = String.format(url, id+"");
        response = HttpUtil.doGet(url, null);
        Assert.assertNotNull(response);
    }

    @Then("^server should revert with status '(\\d+)'$")
    public void serverShouldRevertWithStatus(int status) {
        Assert.assertEquals(response.getStatus(), status);
    }
}