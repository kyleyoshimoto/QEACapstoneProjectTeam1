package steps;

import actions.CommonActions;
import actions.YTPositiveTestActions;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;

import java.time.Duration;

public class YTPositiveTestSteps {

    CommonActions commonActions;
    YTPositiveTestActions testActions;

    public YTPositiveTestSteps(CommonActions commonActions, YTPositiveTestActions testActions) {
        this.commonActions = commonActions;
        this.testActions = testActions;
        WebDriver driver = commonActions.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        testActions.setWait(wait);
    }

    @Given("I am on the YouTube home page")
    public void i_am_on_the_youtube_home_page() {
        commonActions.goToUrl("https://www.youtube.com/");
    }

    @When("I search for Cucumber Tests")
    public void i_search_for_cucumber_tests() {
        testActions.search("Cucumber Tests");
    }

    @Then("I should find a link for Introduction to Cucumber")
    public void i_should_find_a_link_for_introduction_to_cucumber() {
        // Verify that the expected video is found within the Search Results
        Assert.assertTrue("Expected video 'Introduction to Cucumber' was not found in the results.", testActions.isVideoPresent("Introduction to Cucumber"));
    }

    @Given("I am on the Cucumber Tests search results page")
    public void i_am_on_the_cucumber_tests_search_results_page() {
        // First go to YouTube.com home page to execute a search
        commonActions.goToUrl("https://www.youtube.com/");
        // Execute search for "Cucumber Tests"
        testActions.search("Cucumber Tests");
    }

    @When("I click on the link for the video")
    public void i_click_on_the_link_for_the_video() {
        testActions.clickOnSpecificVideo("Introduction to Cucumber");
    }

    @Then("I should see brought to the video page where it shows the date posted as May 14, 2017")
    public void i_should_see_video_page_with_publish_date() {
        // Click on the "Show more" button to expand the description
        testActions.expandVideoDescription();

        // Verify the upload date
        String actualUploadDate = testActions.getVideoUploadDate();
        Assert.assertTrue("Upload date does not match expected date",
                actualUploadDate.contains("May 14, 2017"));
    }

    @Given("I am on the Introduction to Cucumber video page")
    public void i_am_on_the_introduction_to_cucumber_video_page() {
        commonActions.goToUrl("https://www.youtube.com/watch?v=lC0jzd8sGIA&t=461s");
    }

    @When("I click on the share button")
    public void i_click_on_the_share_button() {
        testActions.clickOnShareButton();
    }
    @When("I click on the Embed button")
    public void i_click_on_the_embed_button() {
        testActions.clickOnEmbedButton();
    }
    @Then("I should see a window with the following HTML code")
    public void i_should_see_a_window_with_the_following_html_code(io.cucumber.datatable.DataTable dataTable) {
        testActions.compareEmbedText(dataTable.cell(0,0), testActions.getEmbedText());
    }

    @Given("I am on the embed share modal page")
    public void i_am_on_the_embed_share_modal_page() {
        commonActions.goToUrl("https://www.youtube.com/watch?v=lC0jzd8sGIA&t=461s");
        testActions.clickOnShareButton();
        testActions.clickOnEmbedButton();
    }

    @When("I close the modal to get back to the main video page")
    public void i_close_the_modal_to_get_back_to_the_main_video_page() {
        testActions.closeModal();
    }

    @When("sort the comments by Newest First")
    public void sort_the_comments_by_newest_first() {
        testActions.scrollCommentsIntoView();
        testActions.sortCommentsByNewestFirst();
    }

    @Then("I should see the most recent comment posted")
    public void i_should_see_the_most_recent_comment_posted() {
        testActions.verifyCommentsAreChronologicallyOrdered();
    }

    
    // P5 test
    @When("I click on the channel icon")
    public void i_click_on_the_channel_icon() {
        testActions.clickChannelIconAndVerifyProfilePage();
    }

    @Then("I should be taken to the poster's profile page")
    public void i_should_be_taken_to_the_posters_profile_page() {
        testActions.verifyOnPosterProfilePage();
    }
}