import Pages.ProfilePages.ProfilePatientPage;
import Pages.SignInPages.SignInPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class ProfileParallelTests extends BeforeTests {
    private String PATIENT_LOGIN, PATIENT_PASS,
            PAID_DOC_PASS, NO_FIO_WARNING,
            SAME_PASS_WARNING, CURRENT_PASS_INVALID_ALERT,
            PROFILE_URL;
    private ProfilePatientPage profilePatientPage;

    @Parameters({"browser"})
    ProfileParallelTests(String browser) throws IOException {
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

    @Test
    public void tryClearFIO() throws InterruptedException {
        setProfilePatientPage();
        profilePatientPage.clearFIO();
        profilePatientPage.saveButtonClick();
        Assert.assertTrue(warningContainsText(NO_FIO_WARNING));
    }

    @Test
    public void checkIfPassTheSame() throws InterruptedException {
        setProfilePatientPage();
        profilePatientPage.openPassForm();
        profilePatientPage.typeToPassFields(PATIENT_PASS, PATIENT_PASS, PATIENT_PASS);
        profilePatientPage.saveButtonClick();
        Assert.assertEquals(SAME_PASS_WARNING, profilePatientPage.getAlertText());
    }

    @Test
    public void checkIfCurrentPassInvalid() throws InterruptedException {
        setProfilePatientPage();
        profilePatientPage.openPassForm();
        profilePatientPage.typeToPassFields(PAID_DOC_PASS, PAID_DOC_PASS, PAID_DOC_PASS);
        profilePatientPage.saveButtonClick();
        Assert.assertEquals(CURRENT_PASS_INVALID_ALERT, profilePatientPage.getAlertText());
    }

    @Test
    public void checkAccessToProfile() {
        driver.get(PROFILE_URL);
        signInPage = PageFactory.initElements(driver, SignInPage.class);
        assertNotNull(signInPage);
        Assert.assertTrue(signInPage.signInPageIsVisible());
    }


    private void setProfilePatientPage() throws InterruptedException {
        homePage = signInAndReturnHomePage(signInPage, PATIENT_LOGIN, PATIENT_PASS);
        profilePatientPage = homePage.returnProfilePatientPage();
        assertNotNull(profilePatientPage);
    }

    private boolean warningContainsText(String text) {
        return profilePatientPage.getAlertText().contains(text);
    }

    private void getProperties() {
        PATIENT_LOGIN = testProperties.getProperty("correctPatientLogin");
        PATIENT_PASS = testProperties.getProperty("oneToFivePass");
        PAID_DOC_PASS = testProperties.getProperty("allOnesPass");
        NO_FIO_WARNING = testProperties.getProperty("noFIOWarning");
        SAME_PASS_WARNING = testProperties.getProperty("newPassTheSameAlert");
        CURRENT_PASS_INVALID_ALERT = testProperties.getProperty("currentPassInvalidAlert");
        PROFILE_URL = testProperties.getProperty("profileURL");
    }

}
