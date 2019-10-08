package com.kgcorner.topspin;

/*
Description : Test root for Auth Services
Author: kumar
Created on : 15/09/19
*/

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.*;

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

    @BeforeClass
    public void setup(){
        runner = new TestNGCucumberRunner(this.getClass());
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