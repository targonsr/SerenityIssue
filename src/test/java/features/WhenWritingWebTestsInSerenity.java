package features;

import custom.TestBase;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Pending;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

@RunWith(SerenityRunner.class)
public class WhenWritingWebTestsInSerenity extends TestBase {

    @Test
    @Pending
    public void shouldInstantiateAWebDriverInstanceForAWebTest() {
        getDriver().get("http://www.google.com");

        getDriver().findElement(By.name("q")).sendKeys("firefly", Keys.ENTER);

        new WebDriverWait(getDriver(),5).until(titleContains("firefly - Google"));

        assertThat(getDriver().getTitle()).contains("firefly - Google");
    }
}
