package actions;

import Steps.CommonSteps;
import org.openqa.selenium.WebDriver;

public class CommonActions {

    private WebDriver driver;
    CommonSteps commonSteps;

    public CommonActions(CommonSteps commonSteps) {
        this.driver = commonSteps.getDriver();
    }

    public void goToUrl(String url) {
        driver.get(url);
    }
}