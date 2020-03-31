package custom;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class PageBase extends PageObject {

    public WebElementFacade getUserDropdown() {
        withTimeoutOf(Duration.ofSeconds(10)).waitForElement()
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.login-menu.ant-dropdown")));
        return findAll(By.cssSelector("span.ant-dropdown-trigger")).stream()
                .filter(element -> element.then(By.cssSelector("div.login-menu.ant-dropdown")).isPresent())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User dropdown not found on Home Page"));
    }
}
