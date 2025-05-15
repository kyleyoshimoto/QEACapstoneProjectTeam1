package elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class YTPositiveTestElements {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='top-level-buttons-computed']/yt-button-view-model/button-view-model/button[@title='Share']") public WebElement shareButton;
    @FindBy(xpath = "//button[@id='target' and @title='Embed']") public WebElement embedButton;
    @FindBy(xpath = "//*[@id='center']/yt-searchbox/div[1]/form/input") public WebElement searchBox;
    @FindBy(id = "textarea") public WebElement embededTextArea;
    @FindBy(id = "close-panel-icon") public WebElement modalCloseButton;
    @FindBy(css = "#additional-section #sort-menu tp-yt-paper-button") public WebElement commentsSortByButton;
    @FindBy(xpath = "//tp-yt-paper-listbox[@id=\"menu\"]/a[2]") public WebElement newestFirstButton;
    @FindBy(id = "published-time-text") public List<WebElement> publishedTimeList;

    public YTPositiveTestElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}