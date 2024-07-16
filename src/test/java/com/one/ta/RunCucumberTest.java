package com.one.ta;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/features/checkout.feature"},
        glue = {"com.one.ta.steps"},
        plugin = {"html:target/cukes", "json:target/cucumber-report.json", "junit:target/cucumber-report.xml", "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        strict = true,
        tags = {"@checkout"},
        monochrome = true)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}
