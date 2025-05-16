package steps;
 
import actions.YTNegativeTestActions;
import io.cucumber.java.en.*;
import org.junit.Assert;
 
public class YTNegativeTestSteps {
 
    private final YTNegativeTestActions testActions;
 
    public YTNegativeTestSteps(CommonSteps commonSteps) {
        this.testActions = new YTNegativeTestActions(commonSteps);
    }
 
 
    @Given("I am on a YouTube Video Page in the Chrome Browser")
    public void openYoutube() {
        testActions.openYoutubeHome();
    }
 
    @And("I am not signed in")
    public void clickSignIn() {
        testActions.clickSignIn();
    }
 
    @When("I sign in with email {string}")
    public void enterBadEmail(String email) {
        testActions.typeEmail(email);
        testActions.clickNext();
    }
 
    @Then("I should see an error message")
    public void verifyError() {
        String actual   = testActions.fetchErrorMessage();
        String expected = "enter a valid email or phone number";
        //checks if the actual error says the expected error
        Assert.assertEquals("Unexpected error text!", expected, actual);
    }
}
