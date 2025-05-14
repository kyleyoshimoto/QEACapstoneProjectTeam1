package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features", // path to your YoutubePositiveTesting.feature
        glue = {"Steps"},
        plugin = {"pretty"}
        // tags = "@P2"
)
public class TestRunner {
}