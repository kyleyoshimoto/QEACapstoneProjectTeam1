package actions;

import elements.YTPositiveTestElements;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import steps.CommonSteps;

import java.time.Duration;
import java.util.List;

public class YTPositiveTestActions {

    WebDriver driver;
    WebDriverWait wait;
    YTPositiveTestElements testElements;

    public YTPositiveTestActions(CommonSteps commonSteps) {
        this.driver = commonSteps.getDriver();
        this.testElements = new YTPositiveTestElements(driver);
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    // Performs a search on YouTube using the given text input
    public void search(String searchText) {
        testElements.searchBox.sendKeys(searchText);
        testElements.searchBox.sendKeys(Keys.ENTER);
    }

    // Clicks the Share button on the video page
    public void clickOnShareButton() {
        testElements.shareButton.click();
    }

    // Clicks the Embed option in the share modal
    public void clickOnEmbedButton() {
        testElements.embedButton.click();
    }

    // Retrieves the embed code text from the embed modal
    public String getEmbedText() {
        return testElements.embededTextArea.getAttribute("value");
    }

    // Strips the dynamic '?si=...' query parameter from the embed URL for consistent test comparison
    public String stripDynamicExpression(String embedText) {
        return embedText.replaceAll("\\?si=.*?\"","\"");
    }

    // Compares the expected and actual YouTube embed codes after removing dynamic query parameters
    // such as '?si=...', to ensure consistent and reliable test validation
    public void compareEmbedText(String expectedEmbedText, String actualEmbedText) {
        Assert.assertEquals(stripDynamicExpression(expectedEmbedText), stripDynamicExpression(actualEmbedText));
    }

    public boolean isVideoPresent(String expectedTitle) {
        int maxScrolls = 10;

        for (int i = 0; i < maxScrolls; i++) {
            for (WebElement video : testElements.videoTitles) {
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
            for (WebElement video : testElements.videoTitles) {
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
            wait.until(ExpectedConditions.elementToBeClickable(testElements.moreButton));
            testElements.moreButton.click();
        } catch (TimeoutException | ElementClickInterceptedException e) {
            // If "more" button doesn't appear or can't be clicked normally
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", testElements.moreButton);
            } catch (Exception ex) {
                // Description might already be expanded
                System.out.println("Note: Description may already be expanded or more button not available");
            }
        }

    }

    public String getVideoUploadDate() {
        // Explicit wait for the upload date to become visible on the page
        wait.until(ExpectedConditions.visibilityOf(testElements.uploadDate));
        return testElements.uploadDate.getText();
    }

    // Closes the share/embed modal by clicking the close button
    public void closeModal() {
        testElements.modalCloseButton.click();
    }

    // Scrolls the comments section into view and centers the "Sort by" button using JavaScript
    public void scrollCommentsIntoView() {
        // Scroll browser window so that comments sort button is interactable
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", testElements.commentsSortByButton);
    }

    public void sortCommentsByNewestFirst() {
        // Get the first timestamp before sorting
        String topTimestamp = testElements.publishedTimeList.getFirst().getText();

        testElements.commentsSortByButton.click();
        // Wait for potential overlay to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("tp-yt-paper-item-body.style-scope.yt-dropdown-menu")
        ));

        // Click newest first button using JavaScript to bypass overlay interception
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", testElements.newestFirstButton);

        // Wait until the first timestamp has changed
        wait.until(driver -> {
            List<WebElement> updatedTimeStamps = testElements.publishedTimeList;
            return (!updatedTimeStamps.isEmpty() && !updatedTimeStamps.getFirst().getText().equals(topTimestamp))
                    || updatedTimeStamps.size() == 1;
        });
    }

    // Converts a timestamp string like "2 days ago" into an integer representing total minutes
    // Used for comparing the chronological order of comment timestamps
    private int timestampToMinutes(String timestamp) {
        // Extract numeric part and time unit (e.g., "2", "days")
        int numPart = Integer.parseInt(timestamp.split(" ")[0]);
        String unitPart = timestamp.split(" ")[1];

        // Handle each possible time unit by converting to minutes
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

    // Verifies that the list of comment timestamps is sorted in chronological order
    // Fails the test if any comment appears earlier than a more recent one
    public void verifyCommentsAreChronologicallyOrdered() {
        List<WebElement> timestamp = testElements.publishedTimeList;

        // Loop through timestamps and compare each pair (i, i+1)
        for (int i = 0; i < timestamp.size() - 1; i++) {
            System.out.println("============================================================");

            System.out.println("Timestamp above: " + timestamp.get(i).getText() + " at index " + i);
            // Convert each timestamp to minutes for accurate comparison
            int timestampAbove = timestampToMinutes(timestamp.get(i).getText());

            System.out.println("Timestamp below: " + timestamp.get(i + 1).getText() + " at index " + (i+1));
            int timestampBelow = timestampToMinutes(timestamp.get(i + 1).getText());

            // Fail the test if the order is incorrect
            if (timestampAbove > timestampBelow) {
                Assert.fail("Timestamps aren't ordered in chronological order.");
            }
            Assert.assertTrue(true);
        }
    }

    private void dismissPromo() {
        try {
            if (testElements.promoOverlay != null && testElements.promoOverlay.isDisplayed()) {
                System.out.println("Promo overlay detected, attempting to dismiss.");
                testElements.promoDismissButton.click();
            }
        } catch (NoSuchElementException | ElementNotInteractableException e) {
            System.out.println("Promo overlay not interactable or not present.");
        }
    }

    public void clickChannelIconAndVerifyProfilePage() throws InterruptedException {
        dismissPromo();
        testElements.channelIcon.click();
    }

    public void verifyOnPosterProfilePage() {
        // Assert that the URL follows the typical YouTube channel URL pattern
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Not on a YouTube channel page. Current URL: " + currentUrl,
            currentUrl.matches("https://www.youtube.com/@.+"));
    }

    
}
