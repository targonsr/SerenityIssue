package custom;

import java.util.Timer;
import lombok.Getter;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.TimerTask;
import pages.LoginPage;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getPages;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class TestBase {

    private boolean driverNotPrepared = true;
    private PageBase page;

    @Managed(uniqueSession = true)
    WebDriver driver;

    @Getter
    private WebDriverWait wait = new WebDriverWait(getDriver(), 5);

    @Getter
    private final EnvironmentVariables environmentVariables = SystemEnvironmentVariables
            .createEnvironmentVariables();
    @Getter
    private final EnvironmentSpecificConfiguration environment = EnvironmentSpecificConfiguration
            .from(environmentVariables);

    @Before
    public void setupDriver() {
        if (driverNotPrepared) {
            if (environmentVariables.optionalProperty("webdriver.remote.url")
                    .isPresent()) {
                Dimension dimension = new Dimension(1920, 1080);
                getDriver().manage()
                        .window()
                        .setPosition(new Point(0, 0));
                getDriver().manage()
                        .window()
                        .setSize(dimension);
            }
            getDriver().manage()
                    .deleteAllCookies();
            getDriver().manage()
                    .window()
                    .fullscreen();
            driverNotPrepared = false;
        }
    }

    /**
     * Method necessary because of multiple redirections back and forth and JS executions in meantime
     */
    public <T extends PageObject> void dummyWaitForPage(int intervalInMilis, T page) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    page.open();
                    this.cancel();
                } catch (JavascriptException ignore) {
                    sleep(intervalInMilis);
                    if (getPages().isCurrentPageAt(page.getClass()) || getPages().isCurrentPageAt(LoginPage.class)) {
                        this.cancel();
                    }
                }
            }
        }, 0, intervalInMilis);
    }

    public void sleep(int timeoutInSeconds) {
        try {
            Thread.sleep(timeoutInSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public <T extends WebElement> void moveToElement(T element) {
        Actions action = new Actions(getDriver());
        action.moveToElement(element)
                .perform();
    }

    public void logout() {
        moveToElement(page.getUserDropdown());
        page.waitFor(elementToBeClickable((WebElement) page.getUserDropdown()
                .then("#logout-link")));
        page.getUserDropdown()
                .then("#logout-link")
                .click();
    }
}
