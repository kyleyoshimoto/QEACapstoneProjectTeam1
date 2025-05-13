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
    int maxScrolls = 10, scrollCount = 0;
 
    // Step 5: Search for the target video, scrolling if necessary
    while (targetVideo == null && scrollCount < maxScrolls) {
        for (WebElement renderer : videoRenderers) {
            WebElement titleElement = renderer.findElement(By.cssSelector("#video-title"));
            if (titleElement.getText().toLowerCase().contains("introduction to cucumber")) {
                targetVideo = titleElement;
                break;
            }
        }
        if (targetVideo == null) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000)");
            scrollCount++;
            try { Thread.sleep(1000); } catch (InterruptedException ignored){}
            videoRenderers = driver.findElements(By.cssSelector("ytd-video-renderer"));
        }
    }
 
    // Step 6: Assert that the desired video was found
    Assert.assertNotNull(targetVideo, "Expected video not found");
 
    // Step 7: Click on the video to navigate to its page
    targetVideo.click();
 
    // Step 8: Wait for the video page to load and for the player to be present
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#movie_player")));
 
    //Expand the description so YouTube will show the full publish date
    WebElement showMore = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#expand")));
    showMore.click();
 
    // Step 9: Wait for the publish date to appear (now as full text)
    WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//yt-formatted-string[@id='info']/span[contains(@class, 'bold')][last()]")));
 
    String actualDate = dateElement.getText().trim();
    System.out.println("Publish date found: " + actualDate);
 
    // Step 10: Assert the publish date is exactly "May 14, 2017"
    String expectedDate = "May 14, 2017";
    Assert.assertEquals(actualDate, expectedDate, "Unexpected video publish date.");
}


@Test
public void testShareEmbedCode() {
    // Step 1: Open the target video directly
    driver.get("https://www.youtube.com/watch?v=lC0jzd8sGIA");

    // Step 2: Wait until the Share button is clickable, then click it
    WebElement shareBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("#top-level-buttons-computed > yt-button-view-model > button-view-model")));
    shareBtn.click();

    // Step 3: Wait for the share dialog and click the Embed option
    WebElement embedBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"target\"]/yt-icon")));
    embedBtn.click();

    // Step 4: Wait for the textarea (or equivalent) that contains the iframe code
    WebElement codeBox;
    try {
        // Newer UI: textarea with id embed-code (value attribute holds the code)
        codeBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#textarea")));
    } catch (TimeoutException e) {
        // Fallback: any element whose text *is* the embed string (older UI)
        codeBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.embed-code, pre")));
    }

    String actualIframe =
        (codeBox.getTagName().equals("textarea")
            ? codeBox.getAttribute("value")
            : codeBox.getText())
        .trim();
 
    
    actualIframe = actualIframe //replace all characters that might cause a conflict in comparison
        .replaceAll(
            "(src=\\\"https://www\\.youtube\\.com/embed/lC0jzd8sGIA)[^\\\"]*\\\"",
            "$1\\\""
        )
        .replaceAll("\\sweb-share", "")
        .replaceAll("\\sreferrerpolicy=\"[^\"]*\"", "")
        .replaceAll(";\"", "\""); 
 
    String expectedIframe =
        "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/lC0jzd8sGIA\" "
      + "title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; "
      + "clipboard-write; encrypted-media; gyroscope; picture-in-picture\" "
      + "allowfullscreen></iframe>";
    
    // Step 5: Check if actual Iframe is the expected Iframe
    Assert.assertEquals(actualIframe, expectedIframe, "Unexpected iframe embed code.");
}

}

