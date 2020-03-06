package com.kgcorner.topspin.stepdefs;


import com.google.gson.Gson;
import com.kgcorner.topspin.HttpUtil;
import com.kgcorner.topspin.Response;
import com.kgcorner.topspin.stepdefs.model.UserModel;
import com.kgcorner.topspin.utils.PropertiesUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.bson.Document;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/02/20
 */

public class FetchAllUsersFeature {

    private static long userCount = 10;
    private static final String HOST_KEY = "user.service.host";
    private static final String CREATE_USER_URL_KEY = "user.creation.url";
    private static final String GET_USERS_URL = "get.users.url";
    private static final String NAME = "Gaurav";
    private static final String EMAIL = "gaurav@gaurav.com";
    private static final String USER_NAME = "gaurav";
    private static final String CONTACT = "88888888";
    private static final String OTHER = "other info";
    private Response response;

    @Before("@FetchAllAvailableUserPositive")
    public void setup() {
        MongoClient mongoClient = MongoClients.create(PropertiesUtil.getValue("mongodb.server"));
        MongoDatabase userDb = mongoClient.getDatabase(PropertiesUtil.getValue("db.name"));
        if(userDb == null) {
            throw new RuntimeException("Unable to create DB");
        }
        MongoCollection<Document> usersCollection = userDb.getCollection("userModel");
        long existing = usersCollection.countDocuments();
        for (int i = 0; i < userCount; i++) {
            String host = PropertiesUtil.getValue(HOST_KEY);
            String url = host + PropertiesUtil.getValue(CREATE_USER_URL_KEY);
            Map<String, Object> data = new HashMap<>();
            data.put("name", NAME);
            data.put("username", USER_NAME);
            data.put("email", EMAIL);
            data.put("contact", CONTACT);
            data.put("other", OTHER);
            Response response = HttpUtil.doPost(url, null, data);
            if(response.getStatus() != 201) {
                Assert.fail("Failed to create test users");
                System.out.println(response.getData());
            }
        }
        userCount = userCount + existing;
    }

    @When("^request is made for fetching all available user$")
    public void requestIsMadeForFetchingAllAvailableUser() {
        String host = PropertiesUtil.getValue(HOST_KEY);
        String url = host + PropertiesUtil.getValue(GET_USERS_URL);
        url = String.format(url, userCount);
        response = HttpUtil.doGet(url, null);
    }

    @Then("^response should be returned with all users and status '(\\d+)'$")
    public void responseShouldBeReturnedWithAllUsersAndStatus(int status) {
        Assert.assertEquals(response.getStatus(), status);
        UserModel[] users = new Gson().fromJson(response.getData(), UserModel[].class);
        Assert.assertEquals(users.length, userCount);
    }
}