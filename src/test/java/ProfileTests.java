import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class ProfileTests extends BeforeTests {
    private String PATIENT_LOGIN, PATIENT_PASS,
            PAID_DOC_PASS, PROFILE_UPDATE_ALERT, NO_FIO_WARNING,
            SAME_PASS_WARNING, CURRENT_PASS_INVALID_ALERT;

    @Parameters({"browser"})
    ProfileTests(String browser) throws IOException {
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
    public void updateProfile() {
        setProfilePatientPage();
        updateProfileData();
        checkIfProfileUpdated();
    }

    @Test
    public void tryClearFIO() {
        setProfilePatientPage();
        profilePatientPage.clearFIO();
        profilePatientPage.saveButtonClick();
        Assert.assertTrue(warningContainsText(NO_FIO_WARNING));
    }

    @Test
    public void checkIfPassTheSame() {
        setProfilePatientPage();
        profilePatientPage.openPassForm();
        profilePatientPage.typeToPassFields(PATIENT_PASS, PATIENT_PASS, PATIENT_PASS);
        profilePatientPage.saveButtonClick();
        Assert.assertEquals(SAME_PASS_WARNING, profilePatientPage.getAlertText());
    }

    @Test
    public void checkIfCurrentPassInvalid() {
        setProfilePatientPage();
        profilePatientPage.openPassForm();
        profilePatientPage.typeToPassFields(PAID_DOC_PASS, PAID_DOC_PASS, PAID_DOC_PASS);
        profilePatientPage.saveButtonClick();
        Assert.assertEquals(CURRENT_PASS_INVALID_ALERT, profilePatientPage.getAlertText());
    }

    private void setProfilePatientPage() {
        profilePatientPage = returnPatientProfile(homePage, PATIENT_LOGIN, PATIENT_PASS);
        assertNotNull(profilePatientPage);
    }

    private void updateProfileData() {
        profilePatientPage.changeGender();
        profilePatientPage.changeMiddleName();
        profilePatientPage.saveButtonClick();
    }

    private void checkIfProfileUpdated() {
        Assert.assertEquals(profilePatientPage.getAlertText(), PROFILE_UPDATE_ALERT);
        Assert.assertTrue(profilePatientPage.profileUpdated());
    }

    private boolean warningContainsText(String text){
        return profilePatientPage.getAlertText().contains(text);
    }

    private void getProperties() {
        PATIENT_LOGIN = testProperties.getProperty("correctPatientLogin");
        PATIENT_PASS = testProperties.getProperty("oneToFivePass");
        PAID_DOC_PASS = testProperties.getProperty("allOnesPass");
        PROFILE_UPDATE_ALERT = testProperties.getProperty("profileUpdateAlert");
        NO_FIO_WARNING = testProperties.getProperty("noFIOWarning");
        SAME_PASS_WARNING = testProperties.getProperty("newPassTheSameAlert");
        CURRENT_PASS_INVALID_ALERT = testProperties.getProperty("currentPassInvalidAlert");
    }

}
