package actions;

import elements.YTPositiveTestElements;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    public void sortCommentsByNewestFirst() {
        ytPositiveTestElements.commentsSortByButton.click();
        ytPositiveTestElements.newestFirstButton.click();
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
