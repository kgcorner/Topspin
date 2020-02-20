package com.kgcorner.topspin.stepdefs;

/*
Description : <Write class Description>
Author: kumar
Created on : 15/09/19
*/

import com.kgcorner.topspin.HttpUtil;
import com.kgcorner.topspin.Response;
import com.kgcorner.topspin.stepdefs.model.Token;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import gherkin.deps.com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class NativeLoginTests {
    private URLs urls = URLs.getInstance();
    private Response RESPONSE_FOR_VALID_NATIVE_LOGIN = null;
    private Response RESPONSE_FOR_BEARER_TOKEN_LOGIN = null;
    private Token token;
    @Then("^Service should revert with a token object and status '(\\d+)'$")
    public void serviceShouldRevertWithATokenObjectAndStatus(int status) {
        Assert.assertEquals(RESPONSE_FOR_VALID_NATIVE_LOGIN.getStatus(), status,
            "Status for RESPONSE_FOR_VALID_NATIVE_LOGIN is not matching");
        if(status == HttpStatus.SC_OK) {
            Gson gson = new Gson();
            final Token token = gson.fromJson(RESPONSE_FOR_VALID_NATIVE_LOGIN.getData(), Token.class);
            Assert.assertNotNull(token, "received token is null");
            Assert.assertNotNull(token.getAccessToken(), "Received access token is null");
            Assert.assertNotNull(token.getExpiresInSeconds(), "Received expiration time is null");
            Assert.assertNotNull(token.getRefreshToken(), "Received refresh token is null");
        }
    }

    @Given("^User exists with user name \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void userExistsWithUserNameAndPassword(String username, String password) throws Throwable {
        String tokenUrl = urls.getTokenUrl();
        String basicToken = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        basicToken = "Basic " + basicToken;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", basicToken);
        RESPONSE_FOR_VALID_NATIVE_LOGIN = HttpUtil.doGet(tokenUrl, headers);
    }

    @Then("^User logs in in the system to obtain an access token$")
    public void userLogsInInTheSystemToObtainAnAccessToken() {
        Assert.assertNotNull(RESPONSE_FOR_VALID_NATIVE_LOGIN, "Login responds with null");
        token = new Gson().fromJson(RESPONSE_FOR_VALID_NATIVE_LOGIN.getData(), Token.class);
        Assert.assertNotNull(token, "Login api responds with invalid token response");
        Assert.assertNotNull(token.getRefreshToken(), "No refresh token available in token");
        Assert.assertNotNull(token.getAccessToken(), "No access token available in token");
        Assert.assertTrue(token.getExpiresInSeconds() > 1, "Api responds with invalid expiration value");

    }

    @Then("^User tries to validate that given access token$")
    public void userTriesToValidateThatGivenAccessToken() {
        String tokenUrl = urls.getTokenUrl();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token.getAccessToken());
        RESPONSE_FOR_BEARER_TOKEN_LOGIN = HttpUtil.doGet(tokenUrl, headers);
    }

    @Then("^server should revert the token with status '(\\d+)'$")
    public void serverShouldRevertTheTokenWithStatus(int status) {
        Assert.assertEquals(RESPONSE_FOR_BEARER_TOKEN_LOGIN.getStatus(), status, "Response status is not matching");
        if(status == HttpStatus.SC_OK) {
            Assert.assertNotNull(RESPONSE_FOR_BEARER_TOKEN_LOGIN, "Login responds with null");
            token = new Gson().fromJson(RESPONSE_FOR_BEARER_TOKEN_LOGIN.getData(), Token.class);
            Assert.assertNotNull(token, "Login api responds with invalid token response");
            Assert.assertNotNull(token.getAccessToken(), "No access token available in token");
        }
    }

    @Given("^User tries to validate invalid bearer token$")
    public void userTriesToValidateInvalidBearerToken() {
        String tokenUrl = urls.getTokenUrl();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer invalid_bearer_token");
        RESPONSE_FOR_BEARER_TOKEN_LOGIN = HttpUtil.doGet(tokenUrl, headers);
    }
}