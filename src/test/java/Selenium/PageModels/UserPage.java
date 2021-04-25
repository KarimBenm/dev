package Selenium.PageModels;

import Selenium.helpers.SeleniumUtils;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserPage {
    public UserPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    private final WebDriver driver;
    @FindBy(id = "info")
    private WebElement userView;

    @FindBy(id = "edit")
    private WebElement userEdit;

    @FindBy(css ="input.[formControlName=password]")
    private WebElement logInPass;



    public UserPage enterUserName(String username){
        this.driver.findElement(By.id("loginMail")).sendKeys(username);
       /* SeleniumUtils.waitForElementToClickable(logInUsername);
        logInUsername.sendKeys(username);*/
        return this;

    }
    public UserPage enterPassword(String password){
        this.driver.findElement(By.id("pass")).sendKeys(password);
       /* SeleniumUtils.waitForElementToClickable(logInPass);
        logInPass.sendKeys(password);*/
        return this;

    }
    public void showUserDetail(){
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        NgWebDriver ngDriver = new NgWebDriver(jsDriver);
      WebElement userView1 = this.driver.findElement(By.id("userView"));
        userView1.click();
        ngDriver.waitForAngularRequestsToFinish();
    }
}
