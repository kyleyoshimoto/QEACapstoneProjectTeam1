package steps;
 
import io.cucumber.java.en.*;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;
 
import java.time.Duration;
 
public class YTNegativeTestSteps {
 
    private WebDriver driver;
    private WebDriverWait wait;
 
    @Before
    public void setup() {
        driver = new ChromeDriver();
        wait  = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
 
    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
 
    @Given("I am on a YouTube Video Page in the Chrome Browser")
    public void i_am_on_the_youtube_video_page_in_the_chrome_browser() {
        // go to youtube
        driver.get("https://www.youtube.com");
    }
 
    @And("I am not signed in")
    public void i_am_not_signed_in() {
        //click the signin button
        WebElement signinBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"buttons\"]/ytd-button-renderer/yt-button-shape")));
        signinBtn.click();
    }
 
    @When("I sign in with email {string}")
    public void i_sign_in_with_email(String email) {
        // click into the email input box and enter the test email
        WebElement emailBox = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("identifierId")));
        emailBox.click();       
        emailBox.clear();
        emailBox.sendKeys(email);
        //click the next button
        driver.findElement(By.id("identifierNext")).click();
    }
 
    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
    // wait until error pops up
    WebElement error = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/div/div/div[1]/form/span/section/div/div/div[1]/div/div[2]/div[2]/div")));
    // make sure the error is as expected
    String actual   = error.getText().trim().toLowerCase();
    String expected = "enter a valid email or phone number";
    Assert.assertEquals("Unexpected error text!", expected, actual);
}   
    
}
