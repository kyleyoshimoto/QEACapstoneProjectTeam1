package actions;

import elements.YTPositiveTestElements;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import steps.CommonSteps;

import java.util.List;

public class YTPositiveTestActions {

    WebDriver driver;
    WebDriverWait wait;
    YTPositiveTestElements ytPositiveTestElements;

    public YTPositiveTestActions(CommonSteps commonSteps) {
        this.driver = commonSteps.getDriver();
        this.ytPositiveTestElements = new YTPositiveTestElements(driver);
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
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
    

    public void clickOnSpecificVideo(String videoTitle) {
        boolean clicked = false;
        int maxScrolls = 10;

        for (int i = 0; i < maxScrolls && !clicked; i++) {
            for (WebElement video : ytPositiveTestElements.videoTitles) {
                if (video.getText().toLowerCase().contains(videoTitle.toLowerCase())) {
                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(video));
                        video.click();
                        clicked = true;
                        break;
                    } catch (ElementClickInterceptedException e) {
                        // Try to click with JavaScript if normal click is intercepted
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].click();", video);
                        clicked = true;
                        break;
                    }
                }
            }

            if (!clicked) {
                // Scroll down to try to find the video
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0, 500)");

                try {
                    Thread.sleep(1000); // Wait for content to load
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!clicked) {
            throw new NoSuchElementException("Could not find and click video with title: " + videoTitle);
        }

        // Wait for video page to load
        wait.until(ExpectedConditions.urlContains("youtube.com/watch"));
    }

    public void expandVideoDescription() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(ytPositiveTestElements.moreButton));
            ytPositiveTestElements.moreButton.click();
        } catch (TimeoutException | ElementClickInterceptedException e) {
            // If "more" button doesn't appear or can't be clicked normally
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", ytPositiveTestElements.moreButton);
            } catch (Exception ex) {
                // Description might already be expanded
                System.out.println("Note: Description may already be expanded or more button not available");
            }
        }

    }

    public String getVideoUploadDate() {
        wait.until(ExpectedConditions.visibilityOf(ytPositiveTestElements.uploadDate));
        return ytPositiveTestElements.uploadDate.getText();
    }

    public void compareEmbedText(String expectedEmbedText, String actualEmbedText) {
        String expected = stripEmbedTextOfDynamicExpression(expectedEmbedText);
        String actual = stripEmbedTextOfDynamicExpression(actualEmbedText);
        Assert.assertEquals(expected, actual);
    }

    public void closeModal() {
        ytPositiveTestElements.modalCloseButton.click();
    }

    public void scrollCommentsIntoView() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", ytPositiveTestElements.commentsSortByButton);
    }

    public void exterminatePopup() {

        // Wait for potential overlay to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("tp-yt-paper-item-body.style-scope.yt-dropdown-menu")
        ));

        // Click using JavaScript to bypass overlay interception
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", ytPositiveTestElements.newestFirstButton);
    }

    public void sortCommentsByNewestFirst() {
        ytPositiveTestElements.commentsSortByButton.click();
        // Wait for potential overlay to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("tp-yt-paper-item-body.style-scope.yt-dropdown-menu")
        ));

        // Click using JavaScript to bypass overlay interception
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", ytPositiveTestElements.newestFirstButton);
    }

    private int timestampToMinutes(String timestamp) {
        int numPart = Integer.parseInt(timestamp.split(" ")[0]);
        String unitPart = timestamp.split(" ")[1];
        System.out.println(numPart + " " + unitPart);
        switch (unitPart) {
            case "seconds" -> {
                return 0;
            }
            case "minute", "minutes" -> {
                return numPart;
            }
            case "hour", "hours" -> {
                return numPart * 60;
            }
            case "day", "days" -> {
                return numPart * 60 * 24;
            }
            case "week", "weeks" -> {
                return numPart * 60 * 24 * 7;
            }
            case "month", "months" -> {
                return numPart * 60 * 24 * 30;
            }
            case "year", "years" -> {
                return numPart * 60 * 24 * 365;
            }
        }
        return 0;
    }

    public void verifyCommentsAreChronologicallyOrdered() {
        List<WebElement> timestamp = ytPositiveTestElements.publishedTimeList;

        for (int i = 0; i < timestamp.size() - 1; i++) {
            System.out.println("============================================================");

            System.out.println("Timestamp above: " + timestamp.get(i) + " at index " + i);
            int timestampAbove = timestampToMinutes(timestamp.get(i).getText());
            System.out.println(timestampAbove);

            System.out.println("Timestamp below: " + timestamp.get(i + 1) + " at index " + (i+1));
            int timestampBelow = timestampToMinutes(timestamp.get(i + 1).getText());
            System.out.println(timestampBelow);

            if (timestampAbove > timestampBelow) {
                Assert.fail("Timestamps aren't ordered in chronological order.");
            }
            Assert.assertTrue(true);
        }
    }
}
