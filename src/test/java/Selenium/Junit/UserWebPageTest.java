/*package Selenium.Junit;



import Selenium.PageModels.UserPage;
import Selenium.helpers.testProperties;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserWebPageTest  {

    UserPage userPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Karim\\Desktop\\Lyes\\sel\\geckodriver.exe");
        org.openqa.selenium.WebDriver driver = new FirefoxDriver();
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        NgWebDriver ngDriver = new NgWebDriver(jsDriver);
        driver.get(testProperties.USER_PAGE_URL);
        ngDriver.waitForAngularRequestsToFinish();
        this.userPage = new UserPage(driver);
    }


    // write test cases here

    @Test
    public void VerifyUserDetail() {
        this.userPage.showUserDetail();
        Assert.assertTrue(this.userPage.getDriver().findElement(By.id("userMail"))!= null);
    }

}
*/


