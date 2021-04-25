package Selenium.PageModels;

import Selenium.helpers.SeleniumUtils;
import Selenium.helpers.testProperties;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.ByAngularButtonText;
import com.paulhammant.ngwebdriver.ByAngularModel;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    private final WebDriver driver;
    @FindBy(id = "loginMail")
    private WebElement logInUsername;

    @FindBy(css = "input.[formControlName=password]")
    private WebElement logInPass;

    @FindBy(id = "login")
    private WebElement loginBtn;


    public LoginPage enterUserName(String username) {
        this.driver.findElement(By.id("loginMail")).sendKeys(username);
       /* SeleniumUtils.waitForElementToClickable(logInUsername);
        logInUsername.sendKeys(username);*/
        return this;

    }

    public LoginPage enterPassword(String password) {
        this.driver.findElement(By.id("pass")).sendKeys(password);
       /* SeleniumUtils.waitForElementToClickable(logInPass);
        logInPass.sendKeys(password);*/
        return this;

    }

    public void submitLoginForm() {
        /*   SeleniumUtils.waitForElementToClickable(loginBtn);*/
        this.loginBtn = this.driver.findElement(By.id("login"));
        loginBtn.submit();
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        NgWebDriver ngDriver = new NgWebDriver(jsDriver);
        ngDriver.waitForAngularRequestsToFinish();
    }

    public void navigateToModuleAdmin() {
        /*   SeleniumUtils.waitForElementToClickable(loginBtn);*/
        // this.linkAdmin = this.driver.findElement(By.id("login"));
        System.out.println("test"+this.driver.getCurrentUrl());
        WebElement linkAdmin = this.driver.findElement(By.id("ADMIN"));
        linkAdmin.click();
        WebElement linkUser = this.driver.findElement(By.id("utilisateurs"));
        linkUser.click();
        WebElement userView1 = this.driver.findElement(By.id("info"));
        userView1.click();
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        NgWebDriver ngDriver = new NgWebDriver(jsDriver);
        ngDriver.waitForAngularRequestsToFinish();
    }
}
