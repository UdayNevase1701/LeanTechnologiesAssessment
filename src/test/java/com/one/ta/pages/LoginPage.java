package com.one.ta.pages;

import com.one.ta.steps.BackgroundSteps;
import com.one.ta.utils.Screenshots;
import com.one.ta.utils.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;

public class LoginPage extends PageObject {
    private static Logger log = LogManager.getLogger(LoginPage.class);
    private static final String Application_URL = WebDriverFactory.getProperties().getProperty("applicationURL");
    private final By userName = By.id("user-name");
    private final By passWord = By.id("password");
    private final By signInBtn = By.id("login-button");
    private final By shoppingCartBtn = By.id("shopping_cart_container");
    private final By addToCartBtns = By.xpath("//div[@data-test='inventory-container']//button[text()='Add to cart']");
    private final String shoppingCartBadge = "//div[@id= 'shopping_cart_container']//span[text()='%s']";
    private final String productNames = "//div[@data-test='inventory-item-name']";
    private final String productDescText = "//div[text()='%s']//parent::a//following-sibling::div[@data-test='inventory-item-desc']";
    private final String addToCartBtn = "//div[text()='%s']//ancestor::div[@class='inventory_item_label']//following-sibling::div[@class='pricebar']//button";
    private final String productPriceCartPage = "//div[text()='%s']//parent::a//following-sibling::div[@class='item_pricebar']//div";
    private final String productPriceInvtPage = "//div[text()='%s']//ancestor::div[@class='inventory_item_label']//following-sibling::div[@class='pricebar']//div";
    private final By checkOutBtn = By.id("checkout");
    private final String buttonName = "//*[@id='%s']";
    private final By fName = By.id("first-name");
    private final By lName = By.id("last-name");
    private final By pinCode = By.id("postal-code");
    private final String itemTotal = "//div[@data-test='subtotal-label']";
    private final String finalTotal = "//div[@data-test='total-label']";
    private final String taxAmount = "//div[@data-test='tax-label']";
    private final String thankYouMessage = "//h2[text()='Thank you for your order!']";
    private final String successText = "//div[@data-test='complete-text']";
    String userID = WebDriverFactory.getProperties().getProperty("userName");
    String pwd = WebDriverFactory.getProperties().getProperty("password");


    public void setUserName() {
        clearTextAndSendKeys(userName, userID);
        log.info("UserName Added : " + userID);

    }

    public void setPassword() {
        clearTextAndSendKeys(passWord, pwd);
        log.info("Password Entered");
    }

    public void clickOnSignInButton() {
        clickElement(signInBtn);
        log.info("Clicked On Sign On Button");
    }

    public void navigateToApplicationUrl() {
        WebDriverFactory.getDriver().get(Application_URL);
        log.info("Application Launched : " + Application_URL);
    }

    public void validateLogin() {
        Assert.assertTrue(isElementVisible(shoppingCartBtn), "Shopping Cart Button Is Not Displayed");
        log.info("User Logged In Successfully");
    }


    public void selectThreeProduct(int count) {
        for (int i = 0; i < count; i++) {
            WebElement element = getElements(addToCartBtns).get(i);
            clickElement(element);
        }
    }

    public void validateShoppingCartBadge(String productCount) {
        Assert.assertTrue(isElementVisible(String.format(shoppingCartBadge, productCount)), "Cart Badge is Not showing added products total count");
        Screenshots.attachScreenShotInReport(BackgroundSteps.scenario);
        log.info("Cart Badge is Updated successfully");
    }

    public void clickOnShoppingCartBtn() {
        clickElement(shoppingCartBtn);
    }

    Map<String, List<String>> productDetailMapInvtPage = new LinkedHashMap<>();
    float productPriceTotal = 0;

    public void getProductNameAndAddProduct(int count) {
        for (int i = 0; i < count; i++) {
            List<String> list = new LinkedList<>();
            String productName = getElements(productNames).get(i).getText();
            list.add(getText(String.format(productDescText, productName)));
            list.add(getText(String.format(productPriceInvtPage, productName)));
            productPriceTotal = productPriceTotal + Float.parseFloat(getText(String.format(productPriceInvtPage, productName)).replace("$", ""));
            clickElement(String.format(addToCartBtn, productName));
            log.info("Product : " +productName +" added to the Cart");
            productDetailMapInvtPage.put(productName, list);
        }
    }

    Map<String, List<String>> productDetailMapCartPage;

    public void getProductDetailsAndValidateCart(String pageName) {
        productDetailMapCartPage = new LinkedHashMap<>();
        for (int i = 0; i < getElements(productNames).size(); i++) {
            List<String> list = new LinkedList<>();
            String productName = getElements(productNames).get(i).getText();
            list.add(getText(String.format(productDescText, productName)));
            list.add(getText(String.format(productPriceCartPage, productName)));
            productDetailMapCartPage.put(productName, list);
        }
        Assert.assertEquals(productDetailMapInvtPage, productDetailMapCartPage, "Product Details Are not Matching");
        Screenshots.attachScreenShotInReport(BackgroundSteps.scenario);
        log.info("Validated the Product Name, Description and Product Price Successfully on Page : " + pageName );
    }

    public void clickOnCheckOutBtn() {
        clickElement(checkOutBtn);
    }

    public void clicksOnButton(String name) {
        clickElement(String.format(buttonName, name));
        log.info("Clicked On : " + name + " Button");
    }

    public void enterInformation() {
        clearTextAndSendKeys(fName, getFirstName());
        clearTextAndSendKeys(lName, getLastName());
        clearTextAndSendKeys(pinCode, getPinCode());
        log.info("User Entered the personal Information Successfully");
    }

    public void validateTotalValues() {
        float overviewPageItemTotal = Float.parseFloat(getText(itemTotal).replaceAll("[^\\d.]", ""));
        Assert.assertEquals(productPriceTotal, overviewPageItemTotal, "Overview Page Item total is Wrong");
        float tax = Float.parseFloat(getText(taxAmount).replaceAll("[^\\d.]", ""));
        float overviewPageFinalTotal = Float.parseFloat(getText(finalTotal).replaceAll("[^\\d.]", ""));
        Assert.assertEquals(overviewPageFinalTotal,overviewPageItemTotal+tax);
        Screenshots.attachScreenShotInReport(BackgroundSteps.scenario);
        log.info("Item total and Final Total Values validated successfully");
    }

    public void validateSuccessMessage(){
        Assert.assertTrue(isElementVisible(thankYouMessage));
        Assert.assertEquals(WebDriverFactory.getProperties().getProperty("successMessage"),getText(successText));
        Screenshots.attachScreenShotInReport(BackgroundSteps.scenario);
        log.info("Order Placed Success Message validated successfully");
    }


}
