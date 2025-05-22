package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features", // path to feature files
        glue = {"steps"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        tags = "@P5"
)
public class TestRunner {
}