package features.login;

import custom.TestBase;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.CommonSteps;

@RunWith(SerenityRunner.class)
@Narrative(text = {"Verification of logging functionality for IBX SmartView"})
public class LoginTest extends TestBase {

    @Steps
    CommonSteps user;

    @Before
    public void setUp() {
        user.openApp();
        user.waitForLoginPageOrElseLogout(getEnvironment().getProperty("login.page"));
    }

    @Test
    @Title("I should be able to log in the App")
    public void loginTest() {
        //Given
        user.atLoginPage();

        //When
        user.submitLoginFormWithCredentials(
                getEnvironment().getProperty("external.login"),
                ""
        );

        //Then
        user.pingErrorShouldAppear();
    }

}
