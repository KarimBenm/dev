package Selenium.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;

public class SeleniumUtils {
    private static  WebDriver driver;
    public SeleniumUtils(WebDriver driver) {
        this.driver = driver;
    }
    public static void WaitUntilVisible(WebElement element) {
        waitForCondition(ExpectedConditions.visibilityOf(element), testProperties.elementWaitTimeout);
    }
    public static void waitBy(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    private static void waitForCondition(ExpectedCondition condition, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(condition);
    }
    public static WebElement waitForElementToClickable(WebElement element) {
        if(element == null){
            System.out.println("00000");
        }
        waitForCondition(ExpectedConditions.elementToBeClickable(element), testProperties.elementWaitTimeout);
        return element;
    }

    public static WebElement waitForVisibleFluentWait(WebElement element) {
        final Wait<WebDriver> wait = getDefaultFluentWait()
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }
    private static FluentWait<WebDriver> getDefaultFluentWait() {
        return getCustomFluentWait(testProperties.DEFAULT_WAIT_TIME, testProperties.DEFAULT_POLL_TIME);
    }
    private static FluentWait<WebDriver> getCustomFluentWait(int waitTime, int pollTime) {
        return new FluentWait<>(driver)
                .withTimeout(waitTime, TimeUnit.SECONDS)
                .pollingEvery(pollTime, TimeUnit.SECONDS);
    }

    private static WebDriver goToDriver() {
        System.setProperty("webdriver.gecko.driver","C:\\Users\\Karim\\Desktop\\Lyes\\sel\\geckodriver.exe");
        org.openqa.selenium.WebDriver driver = new FirefoxDriver();
        return driver;
    }
    public static String  getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}
