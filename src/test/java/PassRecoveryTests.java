import Pages.PassRecoveryPage;
import Pages.SignInPages.SignInPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class PassRecoveryTests extends BeforeTests {
    private PassRecoveryPage passRecoveryPage;
    private String EMPTY_LOGIN_ALERT_TEXT, NOT_EXISTING_LOGIN,
            NOT_EXISTING_USER_ALERT_TEXT, NOT_CORRECT_LOGIN, NOT_CORRECT_LOGIN_ALERT;

    @Parameters({"browser"})
    PassRecoveryTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openPassRecoveryPage(String browser) {
        openBrowsers(browser);
        homePage = returnHomePage();
        assertNotNull(homePage);
        SignInPage signInPage = homePage.returnSignInPage();
        assertNotNull(signInPage);
        passRecoveryPage = signInPage.returnPassRecoveryPage();
        assertNotNull(passRecoveryPage);
        getProperties();
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void trySendEmptyLogin() {
        passRecoveryPage.submit();
        checkIfAlertTextIsOk(EMPTY_LOGIN_ALERT_TEXT);
    }

    @Test
    public void trySendNotExistingLogin() {
        passRecoveryPage.sendLogin(NOT_EXISTING_LOGIN);
        passRecoveryPage.submit();
        checkIfAlertTextIsOk(NOT_EXISTING_USER_ALERT_TEXT);
    }

    @Test
    public void trySendNotCorrectLogin() {
        passRecoveryPage.sendLogin(NOT_CORRECT_LOGIN);
        passRecoveryPage.submit();
        checkIfAlertTextIsOk(NOT_CORRECT_LOGIN_ALERT);
    }

    private void checkIfAlertTextIsOk(String rightErrorText) {
        String textErrorOnPage = passRecoveryPage.getErrorText();
        Assert.assertEquals(rightErrorText, textErrorOnPage);
    }

    private void getProperties() {
        EMPTY_LOGIN_ALERT_TEXT = testProperties.getProperty("loginEmptyAlert");
        NOT_EXISTING_USER_ALERT_TEXT = testProperties.getProperty("notExistingUserAlert");
        NOT_EXISTING_LOGIN = testProperties.getProperty("notExistingLogin");
        NOT_CORRECT_LOGIN = testProperties.getProperty("notCorrectLogin");
        NOT_CORRECT_LOGIN_ALERT = testProperties.getProperty("notCorrectLoginAlert");
    }
}
