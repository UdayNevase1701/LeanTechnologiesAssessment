package com.one.ta.steps;

import io.cucumber.java.en.Given;

/**
 * Cucumber steps definition class for general purpose steps.
 */
public class GeneralSteps extends PageObjectManager {

    @Given("User launches the application")
    public void userLaunchesTheVirusTotalApplication() {
        loginPage.navigateToApplicationUrl();
    }


}
