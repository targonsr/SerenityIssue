package pages;

import lombok.Getter;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;

@Getter
@At(urls = {"https://testlogin.equinix.com/.*"})
public class LoginPage extends PageObject {

    @FindBy(css = "div.login-form #username", timeoutInSeconds = "5")
    private WebElementFacade emailInput;
    @FindBy(css = "div.login-form #password")
    private WebElementFacade passInput;
    @FindBy(css = "#loginButton")
    private WebElementFacade loginButton;

    @FindBy(css = "div.ping-error", timeoutInSeconds = "2")
    private WebElementFacade pingError;

    /**
     * For Demonstration purposes only - useless in this case
     */

    @WhenPageOpens
    public void waitUntilLoginAppears() {
        element(emailInput).waitUntilVisible();
    }
}
