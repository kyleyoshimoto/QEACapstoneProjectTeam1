package steps;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class YoutubeHomeSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver(); // Fixed: assigning to class field
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSearchForCucumberTests() {
        // Step 1: Open YouTube
        driver.get("https://www.youtube.com");

        // Step 2: Wait until the search box is visible, then enter search query and press ENTER
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
        searchBox.sendKeys("Cucumber Tests");
        searchBox.sendKeys(Keys.RETURN);

        // Step 3: Capture the initial list of video titles on the search results page
        List<WebElement> videoTitles = driver.findElements(By.cssSelector("a#video-title"));

        boolean found = false;  // Flag to track if the desired video is found
        int maxScrolls = 10;    // Maximum number of scrolls to perform

        for (int i = 0; i < maxScrolls; i++) {
            // Step 4: Iterate through the current list of video titles and check for a match
            for (WebElement video : videoTitles) {
                if (video.getText().toLowerCase().contains("introduction to cucumber")) {
                    found = true;  // Set the flag to true if the video is found
                    break;
                }
            }

            if (found) break;  // Exit the loop if the desired video is found

            // Step 5: Pause briefly to allow new results to load (simulate waiting for results)
            try {
                Thread.sleep(1000);  // Wait 1 sec
            } catch (InterruptedException e) {
                e.printStackTrace();  // Handle any interruption during sleep
            }

            // Step 6: Refresh the list of video titles after a pause (new results may have loaded)
            videoTitles = driver.findElements(By.cssSelector("a#video-title"));
        }

        // Step 7: Assert that the desired video was found in the search results
        Assert.assertTrue(found, "Expected video 'Introduction to Cucumber' was not found in the results.");


    }

    @Test
    public void testClickOnVideoLink() {
        // Step 1: Open YouTube
        driver.get("https://www.youtube.com");

        // Step 2: Wait until the search box is visible, then enter search query and press ENTER
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
        searchBox.sendKeys("Introduction to Cucumber");
        searchBox.sendKeys(Keys.RETURN);

        // Step 3: Wait for search results to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ytd-video-renderer")));

        // Step 4: Capture the initial list of video titles on the search results page
        List<WebElement> videoRenderers = driver.findElements(By.cssSelector("ytd-video-renderer"));

        WebElement targetVideo = null;
        int maxScrolls = 10;
        int scrollCount = 0;

        // Step 5: Search for the target video, scrolling if necessary
        while (targetVideo == null && scrollCount < maxScrolls) {
            for (WebElement renderer : videoRenderers) {
                WebElement titleElement = renderer.findElement(By.cssSelector("#video-title"));
                String title = titleElement.getText().toLowerCase();

                if (title.contains("introduction to cucumber")) {
                    targetVideo = titleElement;
                    break;
                }
            }

            if (targetVideo == null) {
                // Scroll down to load more results
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0, 1000)");
                scrollCount++;

                // Wait for new content to load
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Refresh the list of video renderers
                videoRenderers = driver.findElements(By.cssSelector("ytd-video-renderer"));
            }
        }

        // Step 6: Assert that the desired video was found
        Assert.assertNotNull(targetVideo, "Expected video 'Introduction to Cucumber' was not found in the results.");

        // Step 7: Click on the video to navigate to its page
        targetVideo.click();

        // Step 8: Wait for the video page to load and for the player to be present
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#movie_player")));

        // Step 9: Wait for the publish date to appear
        WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//yt-formatted-string[@id='info']/span[contains(@class, 'bold')][last()]")));

        String actualDate = dateElement.getText().trim();
        System.out.println("Publish date found: " + actualDate); //test failing here as publish date shows up as "Publish date found: 7 years ago"

        // Step 10: Assert the publish date is exactly "May 14, 2017"
        String expectedDate = "May 14, 2017";
        Assert.assertEquals(actualDate, expectedDate, "Unexpected video publish date.");
    }


}

