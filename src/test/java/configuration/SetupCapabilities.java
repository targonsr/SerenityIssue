package configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.core.webdriver.enhancers.BeforeAWebdriverScenario;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.SupportedWebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SetupCapabilities implements BeforeAWebdriverScenario {
    @Override
    public DesiredCapabilities apply(EnvironmentVariables environmentVariables, SupportedWebDriver supportedWebDriver
            , TestOutcome testOutcome, DesiredCapabilities desiredCapabilities) {
        switch (supportedWebDriver) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.NONE);
                options.addArguments("start-maximized");
                options.addArguments("enable-automation");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-browser-side-navigation");
                options.addArguments("--disable-gpu");
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                break;
        }
        return desiredCapabilities;
    }
}
