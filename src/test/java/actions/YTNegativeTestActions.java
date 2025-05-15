package actions;
 
import elements.YTNegativeTestElements;
import steps.CommonSteps;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
 
public class YTNegativeTestActions {
 
    private final YTNegativeTestElements el;
    private final WebDriverWait wait;
    private final CommonActions commonActions;
 
    public YTNegativeTestActions(CommonSteps commonSteps) {
        this.commonActions = new CommonActions(commonSteps);
        this.el            = new YTNegativeTestElements(commonSteps.getDriver());
        this.wait          = new WebDriverWait(commonSteps.getDriver(), Duration.ofSeconds(10));
    }
 
    public void openYoutubeHome() {
        //opens youtube homepage
        commonActions.goToUrl("https://www.youtube.com");
    }
 
    public void clickSignIn() {
        //clicks sign in button on the homepage
        wait.until(ExpectedConditions.elementToBeClickable(el.signInButton)).click();
    }
 
    public void typeEmail(String email) {
        // types a test email into the sign in form
        wait.until(ExpectedConditions.elementToBeClickable(el.emailField)).clear();
        el.emailField.sendKeys(email);
    }
 
    public void clickNext() {
        // clicks the next button
        el.nextButton.click();
    }
 
    public String fetchErrorMessage() {
        //reads the error message shown by google
        return wait.until(ExpectedConditions.visibilityOf(el.errorMessage))
                   .getText().trim().toLowerCase();
    }
}
