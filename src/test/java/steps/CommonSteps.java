package steps;

import custom.TestBase;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.TimeoutException;
import pages.HomePage;
import pages.LoginPage;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getPages;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

public class CommonSteps extends TestBase {

    private String actor;
    private HomePage homePage;
    private LoginPage loginPage;

    @Step("#actor opens the App")
    public void openApp() {
        dummyWaitForPage(5000, homePage);
    }

    @Step
    public void waitForLoginPageOrElseLogout(String url) {
        try {
            getWait().until(urlContains(url));
        } catch (TimeoutException ignore) {
            logout();
        }
    }

    @Step("#actor is on {0} page")
    public void atGivenPage(String name, Class page) {
        getPages().currentPageAt(page);
    }

    @Step("#actor is on Login page")
    public void atLoginPage() {
        getWait().until(urlContains(getEnvironment().getProperty("login.page")));
    }

    @Step("#actor submit the login form with {0} username and {1} password")
    public void submitLoginFormWithCredentials(String username, String password) {
        loginPage.getEmailInput()
                .type(username);
        loginPage.getPassInput()
                .type(password);
        loginPage.getLoginButton()
                .click();
    }

    /**
     * Not described for demonstration purposes
     */
    @Step
    public void isOnUrl(String url) {
        assertThat(getDriver().getCurrentUrl()).startsWith(url);
    }

    @Step
    public void pingErrorShouldAppear() {
        loginPage.getPingError()
                .shouldBeVisible();
    }

    public void logout() {
        isOnUrl(getEnvironment().getProperty("home.page"));
        super.logout();
        getWait().until(urlContains(getEnvironment().getProperty("login.page")));
        dummyWaitForPage(8000, homePage); // fix for logout bug
    }
}
