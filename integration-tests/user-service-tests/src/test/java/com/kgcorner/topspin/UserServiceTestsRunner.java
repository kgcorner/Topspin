package com.kgcorner.topspin;


import com.kgcorner.topspin.utils.PropertiesUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.bson.Document;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 20/02/20
 */

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
public class UserServiceTestsRunner {
    private TestNGCucumberRunner runner;

    @BeforeClass
    public void setup() {
        runner = new TestNGCucumberRunner(this.getClass());
        prepareDb();
    }

    private void prepareDb() {
        MongoClient mongoClient = MongoClients.create(PropertiesUtil.getValue("mongodb.server"));
        MongoDatabase userDb = mongoClient.getDatabase(PropertiesUtil.getValue("db.name"));
        if(userDb == null) {
            throw new RuntimeException("Unable to create DB");
        }
        MongoCollection<Document> usersCollection = userDb.getCollection("userModel");
        Document testUser = new Document();
        testUser.append("id", "0")
            .append("userName", "user")
            .append("name", "gaurav")
            .append("email", "gaurav@domail.com")
            .append("contact", "4544554545")
            .append("other", "gaurav's info")
            .append("active", true);

        System.out.println("Creating Test user");
        usersCollection.insertOne(testUser);
        System.out.println("Collection contains: " + usersCollection.countDocuments() + " Documents" );
        mongoClient.close();
    }

    @Test(groups = "User service features", description = "All features of user service", dataProvider = "features")
    public void runFeatures(CucumberFeatureWrapper wrapper) {
        CucumberFeature feature = wrapper.getCucumberFeature();
        System.out.println("Running feature: " + feature.getPath());
        runner.runCucumber(feature);
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