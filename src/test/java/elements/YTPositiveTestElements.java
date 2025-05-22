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
    @FindBy(xpath = "//div[@id=\"item-with-badge\"]/div[normalize-space()='Newest first']") public WebElement newestFirstButton;
    @FindBy(id = "published-time-text") public List<WebElement> publishedTimeList;

    @FindBy(css = "a#video-title")
    public List<WebElement> videoTitles;

    @FindBy(xpath = "//tp-yt-paper-button[@id='expand']") //used for P2 when clicking "...more" button to expand description box
    public WebElement moreButton;

    @FindBy(css = "#info > span:nth-child(3)") //used for P2 for the shown upload date when the description box is expanded
    public WebElement uploadDate;

    // Used for P5
    @FindBy(xpath = "//*[@id='owner']/ytd-video-owner-renderer/a") public WebElement channelIcon;

    @FindBy(xpath = "//*[@id='dismiss-button']/yt-button-shape/button") public WebElement promoDismissButton;

    @FindBy(xpath = "//*[@id='main']/div[1]/div/h2/yt-formatted-string[text()='Fast forward to better TV']") public WebElement promoOverlay;
    
    public YTPositiveTestElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}