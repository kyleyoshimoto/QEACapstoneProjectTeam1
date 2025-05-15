package elements;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
 
public class YTNegativeTestElements {
 
    WebDriver driver;
 
    //sign in button on top right corner of yt homepage
    @FindBy(xpath = "//*[@id='buttons']/ytd-button-renderer/yt-button-shape") public WebElement signInButton;
    //text input to enter email
    @FindBy(id = "identifierId") public WebElement emailField;
    //next button after email is entered
    @FindBy(id = "identifierNext") public WebElement nextButton;
    //text box that shows the error message
    @FindBy(xpath = "//*[@id='yDmH0d']/c-wiz/div/div[2]/div/div/div[1]/form/span/section/div/div/div[1]/div/div[2]/div[2]/div") public WebElement errorMessage;
 
 
    public YTNegativeTestElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
