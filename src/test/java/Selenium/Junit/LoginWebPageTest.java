/*package Selenium.Junit;


import Selenium.PageModels.LoginPage;
import Selenium.PageModels.UserPage;
import Selenium.helpers.SeleniumUtils;
import Selenium.helpers.testProperties;
import com.paulhammant.ngwebdriver.NgWebDriver;
import fr.lyes.gds.GdsApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)

public class LoginWebPageTest {

    LoginPage loginPage;
    UserPage userPage;


    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Karim\\Desktop\\Lyes\\sel\\geckodriver.exe");
        org.openqa.selenium.WebDriver driver = new FirefoxDriver();
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        NgWebDriver ngDriver = new NgWebDriver(jsDriver);
        driver.get(testProperties.FIRST_PAGE_URL);
        ngDriver.waitForAngularRequestsToFinish();
        this.loginPage = new LoginPage(driver);
    }


    // write test cases here

    @Test
    public void VerifyLogin() {
        this.loginPage.enterPassword(testProperties.ADMIN_LOGIN_PASSWORD).enterUserName(testProperties.ADMIN_LOGIN_USERNAME).submitLoginForm();
        Assert.assertTrue(this.loginPage.getDriver().getCurrentUrl().contains("home"));
        this.loginPage.navigateToModuleAdmin();
      //  this.userPage = new UserPage(this.loginPage.getDriver());
     //   this.userPage.showUserDetail();
        Assert.assertTrue(this.loginPage.getDriver().findElement(By.id("userMail"))!= null);
    }

}

*/

