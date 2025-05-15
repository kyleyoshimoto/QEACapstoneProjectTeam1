package steps;
 
import actions.YTNegativeTestActions;
import io.cucumber.java.en.*;
import org.junit.Assert;
 
public class YTNegativeTestSteps {
 
    private final YTNegativeTestActions yt;
 
    public YTNegativeTestSteps(CommonSteps commonSteps) {
        this.yt = new YTNegativeTestActions(commonSteps);
    }
 
 
    @Given("I am on a YouTube Video Page in the Chrome Browser")
    public void openYoutube() {
        yt.openYoutubeHome();
    }
 
    @And("I am not signed in")
    public void clickSignIn() {
        yt.clickSignIn();
    }
 
    @When("I sign in with email {string}")
    public void enterBadEmail(String email) {
        yt.typeEmail(email);
        yt.clickNext();
    }
 
    @Then("I should see an error message")
    public void verifyError() {
        String actual   = yt.fetchErrorMessage();
        String expected = "enter a valid email or phone number";
        //checks if the actual error says the expected error
        Assert.assertEquals("Unexpected error text!", expected, actual);
    }
}
