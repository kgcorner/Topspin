package com.kgcorner.topspin;

/*
Description : Test root for Auth Services
Author: kumar
Created on : 15/09/19
*/

import com.kgcorner.crypto.Hasher;
import com.kgcorner.topspin.util.PropertiesUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.bson.Document;
import org.testng.annotations.*;

import java.io.IOException;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.kgcorner.topspin.stepdefs"},
    tags = {"~@Ignore"},
    format = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty",
        "json:target/cucumber-reports/CucumberTestReport.json",
        "rerun:target/cucumber-reports/rerun.txt"
    })
public class AuthServicesTestsRunner {
    private TestNGCucumberRunner runner = null;

    //private static final Logger LOGGER  = Logger.getLogger(AuthServicesTestsRunner.class);

    public AuthServicesTestsRunner() throws IOException {

    }

    @BeforeClass
    public void setup(){
        prepareDB();
        runner = new TestNGCucumberRunner(this.getClass());
    }

    /**
     * Prepares DB for Auth service testing
     */
    private void prepareDB() {
        MongoClient mongoClient = MongoClients.create(PropertiesUtil.getValue("mongodb.server"));
        MongoDatabase authDb = mongoClient.getDatabase(PropertiesUtil.getValue("db.name"));
        if(authDb == null) {
            throw new RuntimeException("Unable to create DB");
        }
        MongoCollection<Document> loginCollection = authDb.getCollection("loginModel");
        Document testUser = new Document();
        testUser.append("userId", "0")
                .append("username", "user")
                .append("password", Hasher.getCrypt("password", PropertiesUtil.getValue("password.salt")));
        loginCollection.insertOne(testUser);
        mongoClient.close();
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper wrapper) {
        CucumberFeature cucumberFeature = wrapper.getCucumberFeature();
        System.out.println("feature path:"+ cucumberFeature.getPath());
        runner.runCucumber(cucumberFeature);
    }

    @DataProvider
    public Object[][] features() {
        return runner.provideFeatures();
    }

    @AfterClass
    public void tearDown() {
        runner.finish();
    }
}