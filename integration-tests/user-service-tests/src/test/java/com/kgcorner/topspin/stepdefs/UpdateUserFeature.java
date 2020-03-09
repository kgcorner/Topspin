package com.kgcorner.topspin.stepdefs;


import com.google.gson.Gson;
import com.kgcorner.topspin.HttpUtil;
import com.kgcorner.topspin.Response;
import com.kgcorner.topspin.stepdefs.model.UserModel;
import com.kgcorner.topspin.utils.PropertiesUtil;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/03/20
 */

public class UpdateUserFeature {
    private static final String USER_SERVICE_HOST = PropertiesUtil.getValue("user.service.host");
    private static final String AUTH_SERVICE_HOST = PropertiesUtil.getValue("auth.service.host");
    private static final String USER_CREATION_URL = USER_SERVICE_HOST + PropertiesUtil.getValue("user.creation.url");
    private static final String USER_UPDATE_URL = USER_SERVICE_HOST + PropertiesUtil.getValue("user.update.url");
    private static final String LOGIN_CREATION_URL = AUTH_SERVICE_HOST + PropertiesUtil.getValue("login.creation.url");
    private String userName = null;
    private String password = null;
    private UserModel userModel = null;
    private Response failedResponse;

    @Given("^User exists with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void userExistsWithUsernameAndPassword(String username, String password) throws Throwable {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", "gaurav");
        userData.put("username", username);
        userData.put("email", "email@email.com");
        userData.put("contact", "8569856985");
        userData.put("other", "other");
        Response response = HttpUtil.doPost(USER_CREATION_URL, null, userData);
        if(response.getStatus() != 201)
            Assert.fail("Failed to create test user");
        UserModel userModel = new Gson().fromJson(response.getData(), UserModel.class);

        Map<String, Object> loginData = new HashMap<>();
        loginData.put("user-name", username);
        loginData.put("password", password);
        loginData.put("user-id", userModel.getId());
        response = HttpUtil.doPost(LOGIN_CREATION_URL, null, loginData);
        if(response.getStatus() != 201)
            Assert.fail("Failed to create test login, status " + response.getStatus());
        this.userName = username;
        this.password = password;
        this.userModel = userModel;
    }

    @When("^user tries to change his email to \"([^\"]*)\"$")
    public void userTriesToChangeHisEmailTo(String email) throws Throwable {
        String authHeader = new String(Base64.getEncoder().encode((this.userName+":"+this.password).getBytes()));
        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", "Basic "+ authHeader);
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", "gaurav");
        userData.put("username", this.userName);
        userData.put("email", email);
        userData.put("contact", "8569856985");
        userData.put("other", "other");
        Response response = HttpUtil.doPut(String.format(USER_UPDATE_URL, userModel.getId()), header, userData);
        if(response.getStatus() != 200)
            Assert.fail("Failed to update test user, status " + response.getStatus());
        this.userModel = new Gson().fromJson(response.getData(), UserModel.class);
    }

    @Then("^User should be able to see email changed to \"([^\"]*)\" when user detail is fetched$")
    public void userShouldBeAbleToSeeEmailChangedToWhenUserDetailIsFetched(String email) throws Throwable {
        Assert.assertEquals(this.userModel.getEmail(), email);
    }

    @When("^user tries to change his email to \"([^\"]*)\" without logging in$")
    public void userTriesToChangeHisEmailToWithoutLoggingIn(String email) throws Throwable {
        String authHeader = new String(Base64.getEncoder().encode((this.userName+":"+this.password).getBytes()));
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", "gaurav");
        userData.put("username", this.userName);
        userData.put("email", email);
        userData.put("contact", "8569856985");
        userData.put("other", "other");
        failedResponse = HttpUtil.doPut(String.format(USER_UPDATE_URL, userModel.getId()), null, userData);
    }

    @Then("^service should respond with status '(\\d+)'$")
    public void serviceShouldRespondWithStatus(int status) {
        Assert.assertEquals(failedResponse.getStatus(), status);
    }
}
