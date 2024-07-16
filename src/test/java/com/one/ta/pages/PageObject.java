package com.one.ta.pages;


import com.github.javafaker.Faker;
import com.one.ta.utils.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;


/**
 * This class is having utility methods for performing web actions
 *
 * @author Uday Nevase
 */
public abstract class PageObject {
    /**
     * This method checks if the element is present in dom
     *
     * @param locator Pass the locator xpath you want to check the condition
     * @return returns true or false based on presence of element
     */
    Faker faker;

    public PageObject() {
        faker = new Faker();
    }

    protected boolean isElementVisible(final By locator) {
        final WebElement element = WebDriverFactory.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.isDisplayed();
    }

    protected String getText(WebElement element) {
        WebElement ele = WebDriverFactory.getWebDriverWait().until(ExpectedConditions.visibilityOf(element));
        return ele.getText();
    }

    /**
     * This method is used for navigation to url once the website is launched
     *
     * @param url The url you want to navigate to
     */
    protected void navigateTo(final String url) {
        WebDriverFactory.getDriver().navigate().to(url);
    }

    /**
     * This method is used to click the WebElement
     *
     * @param locator Pass the locator xpath you want to click
     */
    protected void clickElement(final By locator) {
        final WebElement element = WebDriverFactory.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(locator));
        element.click();

    }

    protected void clickElement(WebElement locator) {
        locator.click();
    }

    protected void clearTextAndSendKeys(final By locator, String text) {
        final WebElement element = WebDriverFactory.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }


    /**
     * This method will return text of the WebEment
     *
     * @param locator Pass the locator xpath for which you want the text
     * @return returns the text of WebElement
     */
    protected String getText(final By locator) {
        final WebElement element = WebDriverFactory.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }


    /**
     * This method checks if the WebElemnt is enabled or not in web page
     *
     * @param locator Pass the locator xpath for which you want to check the condition
     * @return returns true or false based on the element is enabled or disabled
     */
    protected boolean isElementEnabled(final By locator) {
        try {
            final WebElement element = WebDriverFactory.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.isEnabled();
        } catch (final TimeoutException e) {
            // Element is not enabled
            return false;
        }
    }


    public List<WebElement> getElements(final By locator) {
        return WebDriverFactory.getDriver().findElements(locator);
    }

    public WebElement getElement(String xpath) {
        By locator = By.xpath(xpath);
        return WebDriverFactory.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void clickElement(String xpath) {
        getElement(xpath).click();
    }

    protected boolean isElementVisible(String xpath) {
        return getElement(xpath).isDisplayed();
    }

    public List<WebElement> getElements(String xpath) {
        return WebDriverFactory.getDriver().findElements(By.xpath(xpath));
    }

    protected String getText(String element) {
        return getElement(element).getText();
    }

    public String getFirstName() {
        return faker.name().firstName();
    }

    public String getLastName() {
        return faker.name().lastName();
    }

    public String getPinCode() {
        return faker.address().zipCode();
    }
}



