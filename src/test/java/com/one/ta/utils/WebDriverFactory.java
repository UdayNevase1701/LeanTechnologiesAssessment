package com.one.ta.utils;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;



/**
 * Provides methods for managing the {@link WebDriver} instances.
 */
public class WebDriverFactory {


	private static final Integer DEFAULT_WEBDRIVER_WAIT_TIME = 40;
	private static Properties properties;



	private static ThreadLocal<WebDriver> webDrivers = new ThreadLocal<>();

	/**
	 * Prevent from initialization of the WebDriverFactory
	 */
	private WebDriverFactory() {
		// EMPTY
	}

	/**
	 * Initializes {@link ChromeDriver} for current thread.
	 */
	public static void initialize() {
		try {

			 properties = new Properties();
			FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/Data/TestData.properties");
			properties.load(file);
			if (properties.getProperty("Browser").equalsIgnoreCase("chrome")) {
				final ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);
				final WebDriver webDriver = new ChromeDriver(options);
				webDrivers.set(webDriver);
				setupBrowser(webDriver);
				setupWaits(webDriver);

			} else  if (properties.getProperty("Browser").equalsIgnoreCase("firefox")){
				final FirefoxOptions firefoxOptions = new FirefoxOptions();
				final  WebDriver webDriver = new FirefoxDriver(firefoxOptions);
				setupBrowser(webDriver);
				setupWaits(webDriver);
				webDrivers.set(webDriver);
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves an instance of {@link WebDriver} for current thread. May return {@code null} in case the
	 * {@link WebDriverFactory#initialize()} method is not called before for the current thread.
	 * 
	 * @return WebDriver instance {@link WebDriver}
	 */
	public static WebDriver getDriver() {
		return webDrivers.get();
	}

	public static Properties getProperties(){
		return properties;
	}

	/**
	 * Quits driver for current thread, if present.
	 */
	public static void quitCurrentDriver() {
		final WebDriver webDriver = webDrivers.get();
		if (webDriver != null) {
			webDriver.quit();
			webDrivers.remove();
		}
	}

	/**
	 * Gets default {@link WebDriverWait} instance with default wait of 10 seconds and 50 milliseconds retry interval.
	 * 
	 * @return {@link WebDriverWait}
	 */
	public static WebDriverWait getWebDriverWait() { 
		return getWebDriverWait(60);
	}

	/**
	 * Gets customized {@link WebDriverWait} instance. The default retry interval is 50 milliseconds
	 * 
	 * @param seconds
	 *            wait time in seconds
	 * @return {@link WebDriverWait}
	 */
	public static WebDriverWait getWebDriverWait(final int seconds) {
		final int wait = seconds > 0 ? seconds : DEFAULT_WEBDRIVER_WAIT_TIME;
		return new WebDriverWait(webDrivers.get(), Duration.ofSeconds(90));
	}

	private static void setupBrowser(final WebDriver webDriver) {

		final Dimension screenResolution = getScreenResolution();
		final Dimension targetResolution = new Dimension(1920, 1080);
		final OSEnum os = OSEnum.getOS();

		switch (os) {
			case MACOS:
				if (screenResolution.getWidth() > targetResolution.getWidth()) {
					webDriver.manage().window().setSize(targetResolution);
				} else {
					webDriver.manage().window().setSize(screenResolution);
				}
				break;
			case WINDOWS:
				if (screenResolution.getWidth() > targetResolution.getWidth()) {
					webDriver.manage().window().setSize(targetResolution);
				} else {
					webDriver.manage().window().maximize();
				}
				break;
			default:
				throw new RuntimeException("Unsupported operating system: " + os);
		}

		webDriver.manage().deleteAllCookies();
	}

	private static void setupWaits(final WebDriver webDriver) {
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}

	private static Dimension getScreenResolution() {
		final java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) screenSize.getWidth();
		final int height = (int) screenSize.getHeight();
		return new Dimension(width, height);
	}
}
