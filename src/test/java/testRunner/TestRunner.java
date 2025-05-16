package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features", // path to your YoutubePositiveTesting.feature
        glue = {"steps"},
        plugin = {"pretty"},
        tags = ""
)
public class TestRunner {
}