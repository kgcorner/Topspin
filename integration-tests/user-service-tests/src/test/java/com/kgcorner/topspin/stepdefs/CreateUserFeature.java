package com.kgcorner.topspin.stepdefs;


import com.google.gson.Gson;
import com.kgcorner.topspin.HttpUtil;
import com.kgcorner.topspin.Response;
import com.kgcorner.topspin.stepdefs.model.UserModel;
import com.kgcorner.topspin.utils.PropertiesUtil;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 20/02/20
 */

public class CreateUserFeature {
    private static final String HOST_KEY = "user.service.host";
    private static final String CREATE_USER_URL_KEY = "user.creation.url";
    private Response response = null;

    @When("^user tries to create profile with name \"([^\"]*)\", username \"([^\"]*)\", email \"([^\"]*)\", contact \"([^\"]*)\" and other infos \"([^\"]*)\"$")
    public void userTriesToCreateProfileWithNameUsernameEmailContactAndOtherInfos(String name,
                                                                                  String username,
                                                                                  String email
        , String contact, String other) throws Throwable {
        String host = PropertiesUtil.getValue(HOST_KEY);
        String url = host + PropertiesUtil.getValue(CREATE_USER_URL_KEY);
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("username", username);
        data.put("email", email);
        data.put("contact", contact);
        data.put("other", other);
        response = HttpUtil.doPost(url, null, data);
    }

    @Then("^user service should create the user profile and return created user with name \"([^\"]*)\", username \"([^\"]*)\", email \"([^\"]*)\", contact \"([^\"]*)\" and other infos \"([^\"]*)\" and status '(\\d+)'$")
    public void userServiceShouldCreateTheUserProfileAndRetunCreatedUserWithNameUsernameEmailContactAndOtherInfosAndStatus(
        String name,
        String username,
        String email
        , String contact, String other, int status
    ) throws Throwable {
        assertNotNull(response, "response can't be null");
        assertEquals(response.getStatus(),status, "Status for user creation is not matching");
        String responseData = response.getData();
        UserModel userModel = new Gson().fromJson(responseData, UserModel.class);
        assertEquals(userModel.getName(), name,"name for user creation is not matching");
        assertEquals(userModel.getUserName(), username,"username for user creation is not matching");
        assertEquals( userModel.getEmail(), email,"email for user creation is not matching");
        assertEquals( userModel.getContact(), contact,"contact for user creation is not matching");
        assertEquals( userModel.getOthers(), other,"other info for user creation is not matching");
    }

    @Then("^user service should return response with status '(\\d+)' as name is blank$")
    public void userServiceShouldReturnResponseWithStatusAsNameIsBlank(int status) {
        assertNotNull(response, "response can't be null");
        assertEquals( response.getStatus(), status,"Status for user creation with blank name is not matching");
    }

    @Then("^user service should return response with status '(\\d+)' as email is invalid$")
    public void userServiceShouldReturnResponseWithStatusAsEmailIsInvalid(int status) {
        assertNotNull(response, "response can't be null");
        assertEquals( response.getStatus(),status, "Status for user creation with invalid email is not matching");
    }

    @Then("^user service should return response with status '(\\d+)' as username is blank$")
    public void userServiceShouldReturnResponseWithStatusAsUsernameIsBlank(int status) {
        assertNotNull(response, "response can't be null");
        assertEquals( response.getStatus(), status,"Status for user creation with blank username is not matching");
    }
}