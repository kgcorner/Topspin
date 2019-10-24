package com.kgcorner.topspin;

/*
Description : Test root for Auth Services
Author: kumar
Created on : 15/09/19
*/

import com.kgcorner.crypto.Hasher;
import com.kgcorner.topspin.stepdefs.model.Login;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Properties;

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
    private Properties properties;
    //private static final Logger LOGGER  = Logger.getLogger(AuthServicesTestsRunner.class);

    public AuthServicesTestsRunner() throws IOException {
        this.properties = new Properties();
        properties.load(AuthServicesTestsRunner.class.getResourceAsStream("/application.properties"));
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

        MongoClient mongoClient = MongoClients.create(properties.getProperty("mongodb.server"));
        MongoDatabase authDb = mongoClient.getDatabase(properties.getProperty("db.name"));
        if(authDb == null) {
            throw new RuntimeException("Unable to create DB");
        }
        MongoCollection<Document> loginCollection = authDb.getCollection("loginModel");
        Document testUser = new Document();
        testUser.append("userId", "0")
                .append("userName", "user")
                .append("password", Hasher.getCrypt("password", properties.getProperty("password.salt")));

        System.out.println("Creating Test user");
        loginCollection.insertOne(testUser);
        System.out.println("Collection contains: " + loginCollection.countDocuments() + " Documents" );
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