import Pages.HomePage;
import Pages.SignInPages.SignInFbPage;
import Pages.SignInPages.SignInPage;
import Pages.SignInPages.SignInOkPage;
import Pages.SignInPages.SignInVkPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class SignInTests extends BeforeTests {
    private SignInPage signInPage;
    private String PATIENT_PASS, PATIENT_EMAIL, SUPPORT_PAS, SUPPORT_LOGIN,
            VK_LOGIN, VK_PASS, OK_LOGIN, OK_PASS,
            INCORECT_LOGIN_ERROR, CANCEL_FB_SIGNIN_ERROR, MOBILE_LOGIN;

    @Parameters({"browser"})
    SignInTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openSignInPage(String browser) {
        openBrowsers(browser);
        homePage = returnHomePage();
        assertNotNull(homePage);
        signInPage = homePage.returnSignInPage();
        assertNotNull(signInPage);
        getProperties();
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test(enabled = false)
    public void successPatientSignInAndOut() {
        HomePage homePage = signInAndReturnHomePage(signInPage, PATIENT_EMAIL, PATIENT_PASS);
        assertNotNull(homePage);
        checkIfUserSignedIn(homePage); //init elements + chats link is visible

        homePage.signOut();
        Assert.assertTrue(homePage.ifUserSignedOut());
    }

    @Test
    public void successPatientWithMobile() {
        HomePage homePage = signInAndReturnHomePage(signInPage, MOBILE_LOGIN, PATIENT_PASS);
        assertNotNull(homePage);
        checkIfUserSignedIn(homePage); //init elements + chats link is visible
    }

    @Test
    public void successDocSignIn() {
        HomePage homePage = signInAndReturnHomePage(signInPage, SUPPORT_LOGIN, SUPPORT_PAS);
        assertNotNull(homePage);
        checkIfUserSignedIn(homePage);
    }

    @Test
    public void failLoginAnyUser() {
        signInPage.submit();
        checkIfAlertTextIsOk(INCORECT_LOGIN_ERROR);
    }

    @Test
    public void failFBLogin() {
        SignInFbPage signInFbPage = signInPage.returnFbSignInPage();
        signInPage = signInFbPage.returnSignInPage();
        checkIfAlertTextIsOk(CANCEL_FB_SIGNIN_ERROR);
    }

    @Test(enabled = false) //Нет тестовой учетки
    public void vkSignIn() {
        SignInVkPage signInVkPage = signInPage.returnVkSignInPage();
        signInVkPage.signIn(VK_LOGIN, VK_PASS);
        HomePage homePage = signInVkPage.returnHomePage();
        assertNotNull(homePage);
        checkIfUserSignedIn(homePage);
    }

    @Test(enabled = false) //Нет тестовой учетки
    public void okSignIn() {
        SignInOkPage signInOkPage = signInPage.returnOkSignInPage();
        signInOkPage.signIn(OK_LOGIN, OK_PASS);
        HomePage homePage = signInOkPage.returnHomePage();
        assertNotNull(homePage);
        checkIfUserSignedIn(homePage);
    }

    private void checkIfAlertTextIsOk(String rightErrorText) {
        String textErrorOnPage = signInPage.getErrorText();
        Assert.assertEquals(rightErrorText, textErrorOnPage);
    }

    private void getProperties() {
        PATIENT_EMAIL = testProperties.getProperty("correctPatientLogin");
        MOBILE_LOGIN = testProperties.getProperty("mobileLogin");
        PATIENT_PASS = testProperties.getProperty("oneToFivePass");
        SUPPORT_LOGIN = testProperties.getProperty("supportDocLogin");
        SUPPORT_PAS = testProperties.getProperty("supportPas");
        VK_LOGIN = testProperties.getProperty("vkLogin");
        VK_PASS = testProperties.getProperty("vkPass");
        OK_LOGIN = testProperties.getProperty("okLogin");
        OK_PASS = testProperties.getProperty("okPass");
        INCORECT_LOGIN_ERROR = testProperties.getProperty("incorrectLoginError");
        CANCEL_FB_SIGNIN_ERROR = testProperties.getProperty("cancelFbSignInError");
    }
}
