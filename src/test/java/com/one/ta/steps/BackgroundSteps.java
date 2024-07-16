package com.one.ta.steps;

import com.one.ta.utils.WebDriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Cucumber hook steps definition class responsible for actions taken before and after test execution.
 */
public class BackgroundSteps {
    public static Scenario scenario;

    @Before(order = 0)
    public void beforeUITests() {
        WebDriverFactory.initialize();
    }

    @Before(order = 1)
    public void getScenario(Scenario scenario) {
        BackgroundSteps.scenario = scenario;
    }

    @AfterStep
    public void addScreenshotOnFailure(final Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) WebDriverFactory.getDriver();
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.embed(src, "image/png", "FailShot");
        }
    }

    @After
    public void afterUITests(final Scenario scenario) {
        if (!scenario.isFailed()) {
            WebDriverFactory.quitCurrentDriver();
        }
    }
}
