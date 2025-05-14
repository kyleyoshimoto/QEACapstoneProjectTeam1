package Steps;

import actions.CommonActions;
import actions.YTPositiveTestActions;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Assert;

import java.util.List;

public class YTPositiveTestSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    CommonActions commonActions;

    public YTPositiveTestSteps(CommonActions commonActions, YTPositiveTestActions) {}

    @Given("I am on the YouTube home page")
    public void i_am_on_the_youtube_home_page() {
        driver.get("https://www.youtube.com");
    }

    @When("I search for Cucumber Tests")
    public void i_search_for_cucumber_tests() {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
        searchBox.sendKeys("Cucumber Tests");
        searchBox.sendKeys(Keys.RETURN);
    }

    @Then("I should find a link for Introduction to Cucumber")
    public void i_should_find_a_link_for_introduction_to_cucumber() {
        boolean found = false;
        int maxScrolls = 10;

        for (int i = 0; i < maxScrolls; i++) {
            List<WebElement> videoTitles = driver.findElements(By.cssSelector("a#video-title"));

            for (WebElement video : videoTitles) {
                if (video.getText().toLowerCase().contains("introduction to cucumber")) {
                    found = true;
                    break;
                }
            }

            if (found) break;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assert.assertTrue("Expected video 'Introduction to Cucumber' was not found in the results.", found);
    }


    @Given("I am on the Cucumber Tests search results page")
    public void i_am_on_the_cucumber_tests_search_results_page() {
        driver.get("https://www.youtube.com");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
        searchBox.sendKeys("Introduction to Cucumber");
        searchBox.sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ytd-video-renderer")));
    }

    @When("I click on the link for the video")
    public void i_click_on_the_link_for_the_video() {
        List<WebElement> videoRenderers = driver.findElements(By.cssSelector("ytd-video-renderer"));
        WebElement targetVideo = null; // #info > span:nth-child(3)
        int maxScrolls = 10;
        int scrollCount = 0;

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
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
                videoRenderers = driver.findElements(By.cssSelector("ytd-video-renderer"));
            }
        }
        targetVideo.click();
    }

    @Then("I should see brought to the video page where it shows the date posted as May 14, 2017")
    public void i_should_see_video_page_with_publish_date() {
        // Wait for video player to confirm we’re on the video page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#movie_player")));

        // Click the expand button if it's present
        try {
            WebElement showMore = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#expand")));
            showMore.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Expand button not found, may already be expanded");
        }

        // Try fetching the date
        WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("span.date.style-scope.ytd-video-primary-info-renderer")));

        String actualDate = dateElement.getText().trim();
        System.out.println("Found video publish date: " + actualDate);

        String expectedDate = "May 14, 2017";
        Assert.assertEquals(actualDate, expectedDate, "Unexpected video publish date.");
    }

    @Given("I am on the Cucumber Tests video page")
    public void i_am_on_the_cucumber_tests_video_page() {
        CommonActions.goToUrl("https://www.youtube.com/watch?v=lC0jzd8sGIA&t=461s");
        driver.get("https://www.youtube.com/watch?v=lC0jzd8sGIA&t=461s");
    }
    @When("I click on the share button")
    public void i_click_on_the_share_button() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("I click on the Embed button")
    public void i_click_on_the_embed_button() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("I should get a window with the following HTML code")
    public void i_should_get_a_window_with_the_following_html_code() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }








//    @Test
//public void testClickOnVideoLink() {
//    // Step 1: Open YouTube
//    driver.get("https://www.youtube.com");
//
//    // Step 2: Wait until the search box is visible, then enter search query and press ENTER
//    WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
//    searchBox.sendKeys("Introduction to Cucumber");
//    searchBox.sendKeys(Keys.RETURN);
//
//    // Step 3: Wait for search results to load
//    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ytd-video-renderer")));
//
//    // Step 4: Capture the initial list of video titles on the search results page
//    List<WebElement> videoRenderers = driver.findElements(By.cssSelector("ytd-video-renderer"));
//
//    WebElement targetVideo = null;
//    int maxScrolls = 10, scrollCount = 0;
//
//    // Step 5: Search for the target video, scrolling if necessary
//    while (targetVideo == null && scrollCount < maxScrolls) {
//        for (WebElement renderer : videoRenderers) {
//            WebElement titleElement = renderer.findElement(By.cssSelector("#video-title"));
//            if (titleElement.getText().toLowerCase().contains("introduction to cucumber")) {
//                targetVideo = titleElement;
//                break;
//            }
//        }
//        if (targetVideo == null) {
//            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000)");
//            scrollCount++;
//            try { Thread.sleep(1000); } catch (InterruptedException ignored){}
//            videoRenderers = driver.findElements(By.cssSelector("ytd-video-renderer"));
//        }
//    }
//
//    // Step 6: Assert that the desired video was found
//    Assert.assertNotNull(targetVideo, "Expected video not found");
//
//    // Step 7: Click on the video to navigate to its page
//    targetVideo.click();
//
//    // Step 8: Wait for the video page to load and for the player to be present
//    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#movie_player")));
//
//    //Expand the description so YouTube will show the full publish date
//    WebElement showMore = wait.until(ExpectedConditions.elementToBeClickable(
//        By.cssSelector("#expand")));
//    showMore.click();
//
//    // Step 9: Wait for the publish date to appear (now as full text)
//    WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
//        By.xpath("//yt-formatted-string[@id='info']/span[contains(@class, 'bold')][last()]")));
//
//    String actualDate = dateElement.getText().trim();
//    System.out.println("Publish date found: " + actualDate);
//
//    // Step 10: Assert the publish date is exactly "May 14, 2017"
//    String expectedDate = "May 14, 2017";
//    Assert.assertEquals(actualDate, expectedDate, "Unexpected video publish date.");
//}


//@Test
//public void testShareEmbedCode() {
//    // Step 1: Open the target video directly
//    driver.get("https://www.youtube.com/watch?v=lC0jzd8sGIA");
//
//    // Step 2: Wait until the Share button is clickable, then click it
//    WebElement shareBtn = wait.until(
//            ExpectedConditions.elementToBeClickable(By.cssSelector("#top-level-buttons-computed > yt-button-view-model > button-view-model")));
//    shareBtn.click();
//
//    // Step 3: Wait for the share dialog and click the Embed option
//    WebElement embedBtn = wait.until(
//            ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"target\"]/yt-icon")));
//    embedBtn.click();
//
//    // Step 4: Wait for the textarea (or equivalent) that contains the iframe code
//    WebElement codeBox;
//    try {
//        // Newer UI: textarea with id embed-code (value attribute holds the code)
//        codeBox = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#textarea")));
//    } catch (TimeoutException e) {
//        // Fallback: any element whose text *is* the embed string (older UI)
//        codeBox = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.embed-code, pre")));
//    }
//
//    String actualIframe =
//        (codeBox.getTagName().equals("textarea")
//            ? codeBox.getAttribute("value")
//            : codeBox.getText())
//        .trim();
//
//
//    actualIframe = actualIframe //replace all characters that might cause a conflict in comparison
//        .replaceAll(
//            "(src=\\\"https://www\\.youtube\\.com/embed/lC0jzd8sGIA)[^\\\"]*\\\"",
//            "$1\\\""
//        )
//        .replaceAll("\\sweb-share", "")
//        .replaceAll("\\sreferrerpolicy=\"[^\"]*\"", "")
//        .replaceAll(";\"", "\"");
//
//    String expectedIframe =
//        "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/lC0jzd8sGIA\" "
//      + "title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; "
//      + "clipboard-write; encrypted-media; gyroscope; picture-in-picture\" "
//      + "allowfullscreen></iframe>";
//
//    // Step 5: Check if actual Iframe is the expected Iframe
//    Assert.assertEquals(actualIframe, expectedIframe, "Unexpected iframe embed code.");
//}

}

