import Pages.HomePage;
import Pages.ProfilePages.ProfilePatientPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class UpdatePassTest extends BeforeTests {
    private String PATIENT_LOGIN, PATIENT_PASS,
            PAID_DOC_PASS, PASS_UPDATED_ALERT;
    private ProfilePatientPage profilePatientPage;

    @Parameters({"browser"})
    UpdatePassTest(String browser) throws IOException {
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
    public void updatePass() throws InterruptedException {
        setProfilePatientPage();
        updatePass(PATIENT_PASS, PAID_DOC_PASS, PAID_DOC_PASS);
        profilePatientPage.signOut();
        HomePage homePage = profilePatientPage.returnHomePage();
        assertNotNull(homePage);
        signInPage = homePage.returnSignInPage();
        homePage = signInAndReturnHomePage(signInPage, PATIENT_LOGIN, PAID_DOC_PASS);
        profilePatientPage = homePage.returnProfilePatientPage();
        updatePass(PAID_DOC_PASS, PATIENT_PASS, PATIENT_PASS);
    }

    private void setProfilePatientPage() throws InterruptedException {
        homePage = signInAndReturnHomePage(signInPage, PATIENT_LOGIN, PATIENT_PASS);
        profilePatientPage = homePage.returnProfilePatientPage();
        assertNotNull(profilePatientPage);
    }

    private void updatePass(String currentPass, String newPass, String passAgain) {
        profilePatientPage.openPassForm();
        profilePatientPage.typeToPassFields(currentPass, newPass, passAgain);
        profilePatientPage.saveButtonClick();
        Assert.assertEquals(PASS_UPDATED_ALERT, profilePatientPage.getAlertText());
    }

    private void getProperties() {
        PATIENT_LOGIN = testProperties.getProperty("correctPatientLogin");
        PATIENT_PASS = testProperties.getProperty("oneToFivePass");
        PAID_DOC_PASS = testProperties.getProperty("allOnesPass");
        PASS_UPDATED_ALERT = testProperties.getProperty("passUpdatedAlert");
    }
}
