package DemoTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.services.annotations.AppTest;
import com.automation.seletest.core.services.annotations.AppTest.AssertionType;
import com.automation.seletest.core.spring.SeletestWebTestBase;
import com.automation.seletest.pagecomponents.pageObjects.GooglePage;

@AppTest
public class GoogleTest extends SeletestWebTestBase{

    @Autowired
    GooglePage googlePage;

    @AppTest(assertion=AssertionType.SOFT)
    @Test
    public void googleSearch(){
        googlePage.
                openPage(GooglePage.class).
                typeSearch("https://github.com/GiannisPapadakis").
                buttonSearch();

        SessionControl.verifyController().elementPresent("//*[contains(text(),'Giannis Papadakis')]");
    }

    @AppTest(assertion=AssertionType.HARD)
    @Test
    public void googleSearch2(){
        googlePage.
                openPage(GooglePage.class).
                typeSearch("Nothing").
                buttonSearch();

        SessionControl.verifyController().elementPresent("//text");
    }

}
