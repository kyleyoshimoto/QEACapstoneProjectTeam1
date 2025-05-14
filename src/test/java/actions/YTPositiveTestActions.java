package actions;

import elements.YTPositiveTestElements;
import org.openqa.selenium.Keys;
import steps.CommonSteps;
import org.openqa.selenium.WebDriver;

public class YTPositiveTestActions {

    WebDriver driver;
    YTPositiveTestElements ytPositiveTestElements;

    public YTPositiveTestActions(CommonSteps commonSteps) {
        this.driver = commonSteps.getDriver();
        this.ytPositiveTestElements = new YTPositiveTestElements(driver);
    }

    public void search(String searchText) {
        ytPositiveTestElements.searchBox.sendKeys(searchText);
        ytPositiveTestElements.searchBox.sendKeys(Keys.ENTER);
    }

    public void clickOnShareButton() {
        ytPositiveTestElements.shareButton.click();
    }

    public void clickOnEmbedButton() {
        ytPositiveTestElements.embedButton.click();
    }

    public String getEmbedText() {
        return ytPositiveTestElements.embededTextArea.getAttribute("value");
    }
}
