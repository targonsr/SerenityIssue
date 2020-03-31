package features.login;

import custom.TestBase;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.CommonSteps;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "testData/loginData.csv")
@Narrative(text = {"Verification of logging functionality for IBX SmartView - Parametrized!!!"})
public class Login3Test extends TestBase {

    private String login;
    private String password;
    private String valid;

    @Steps
    CommonSteps user;

    @Before
    public void setUp() {
        user.openApp();
        user.waitForLoginPageOrElseLogout(getEnvironment().getProperty("login.page"));
    }

    @Test
    @Title("I should be able to log in the App - Parametrized!!!")
    public void loginTest() {
        //Given
        user.atLoginPage();

        //When
        user.submitLoginFormWithCredentials(login, password);

        //Then
        if (Boolean.parseBoolean(valid)) {
            user.isOnUrl(getEnvironment().getProperty("home.page"));
        } else {
            user.pingErrorShouldAppear();
        }
    }
}
