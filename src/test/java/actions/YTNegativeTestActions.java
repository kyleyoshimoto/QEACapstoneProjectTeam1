package actions;

import elements.YTNegativeTestElements;
import steps.CommonSteps;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class YTNegativeTestActions {

    /** Element locators for this flow (buttons, fields, etc.). */
    private final YTNegativeTestElements testElements;

    /** Explicit-wait helper shared by every action in this class. */
    private final WebDriverWait wait;

    /** Thin wrapper around common helper methods (navigation, etc.). */
    private final CommonActions commonActions;

    /**
     * Constructor wires up helpers using the WebDriver from the
     * higher-level Cucumber step class.
     *
     * @param commonSteps glue code that exposes the shared driver
     */
    public YTNegativeTestActions(CommonSteps commonSteps) {
        this.commonActions = new CommonActions(commonSteps);
        this.testElements  = new YTNegativeTestElements(commonSteps.getDriver());
        this.wait          = new WebDriverWait(commonSteps.getDriver(), Duration.ofSeconds(10));
    }

    /** Navigate to the YouTube home page. */
    public void openYoutubeHome() {
        commonActions.goToUrl("https://www.youtube.com");
    }

    /** Click the “Sign in” button on the YouTube toolbar. */
    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(testElements.signInButton))
            .click();
    }

    /**
     * Enter an email string (supplied by the Cucumber Examples table)
     * into the Google sign-in form.
     *
     * @param email the address to test
     */
    public void typeEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(testElements.emailField))
            .clear();                        // wipe any autofilled text
        testElements.emailField.sendKeys(email);
    }

    /** Advance to the next step in the Google sign-in flow. */
    public void clickNext() {
        testElements.nextButton.click();
    }

    /**
     * Capture and normalize the red error banner that appears
     * when Google rejects the supplied email.
     *
     * @return lowercase, trimmed error message text
     */
    public String fetchErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(testElements.errorMessage))
                   .getText()
                   .trim()
                   .toLowerCase();
    }
}
