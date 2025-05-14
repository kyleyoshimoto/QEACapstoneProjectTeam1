package Elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YTPositiveTestElements {

    WebDriver driver;

    @FindBy(xpath = "//button[@title='share']") public WebElement shareButton;

    public YTPositiveTestElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
