package com.one.ta.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class LoginSteps extends PageObjectManager {


    @And("User Enters UserName,Password")
    public void userEntersUserNamePasswordAndClicksOnSingInButton() {
        loginPage.setUserName();
        loginPage.setPassword();
    }

    @Then("User should login successfully")
    public void userShouldLoginSuccessfully() {
        loginPage.validateLogin();
    }


    @Then("cart badge is updated with the added products count that is {string}")
    public void cartBadgeIsUpdatedWithTheAddedProductsCountThatIs(String count) {
        loginPage.validateShoppingCartBadge(count);
    }

    @And("User clicks on the {string} Button")
    public void userClicksOnTheButton(String name) {
        loginPage.clicksOnButton(name);

    }

    @And("User store the product details and adds {int} products to the cart")
    public void userStoreTheProductDetailsAndAddsProductsToTheCart(int count) {
        loginPage.getProductNameAndAddProduct(count);
    }

    @And("User Enters the personal Information")
    public void userEntersThePersonalInformation() {
        loginPage.enterInformation();
    }

    @Then("validate the product details on {string} page")
    public void validateTheProductDetailsOnPage(String pageName) {
        loginPage.getProductDetailsAndValidateCart(pageName);
    }

    @Then("validate item total and final total values")
    public void validateItemTotalAndFinalTotalValues() {
        loginPage.validateTotalValues();
    }

    @Then("validate success message")
    public void validateSuccessMessage() {
        loginPage.validateSuccessMessage();
    }
}
