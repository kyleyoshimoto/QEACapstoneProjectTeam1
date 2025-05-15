package actions;

import elements.YTPositiveTestElements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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

    // Removes the dynamic '?si=...' parameter from the YouTube embed URL for stable comparison.
    public String stripEmbedTextOfDynamicExpression(String embedText) {
        return embedText.replaceAll("\\?si=.*?\"","\"");
    }


    public boolean isVideoPresent(String expectedTitle) {
        int maxScrolls = 10;

        for (int i = 0; i < maxScrolls; i++) {
            for (WebElement video : ytPositiveTestElements.videoTitles) {
                if (video.getText().toLowerCase().contains(expectedTitle.toLowerCase())) {
                    return true;
                }
            }
        }

        return false;
    }


}
