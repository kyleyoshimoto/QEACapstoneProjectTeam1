package elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YTPositiveTestElements {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='top-level-buttons-computed']/yt-button-view-model/button-view-model/button[@title='Share']") public WebElement shareButton;
    @FindBy(xpath = "//button[@id='target' and @title='Embed']") public WebElement embedButton;
    @FindBy(xpath = "//*[@id='center']/yt-searchbox/div[1]/form/input") public WebElement searchBox;
    @FindBy(id = "textarea") public WebElement embededTextArea;

    public YTPositiveTestElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
