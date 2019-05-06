import DataBaseActions.DBUpdateNumber;
import Pages.SignUpPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.testng.Assert.assertNotNull;

public class SignUpTests extends BeforeTests {
    private SignUpPage signUpPage;
    private String WARNING_PATIENT_EXISTS, PATIENT_PASS,
            MOBILE_LOGIN, WARNING_CORRECT_NUMBER;
    private String FAKE_MOBILE_NUMBER;
    private String SUPPORT_EMAIL, SURNAME, MIDDLE_NAME, NAME, SUPPORT_SPECIALITY,
            SPECIALITY_PLACEHOLDER, WARNING_DOC_EXISTS;

    @Parameters({"browser"})
    SignUpTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openHomePage(String browser) {
        openBrowsers(browser);
        homePage = returnHomePage();
        assertNotNull(homePage);
        signUpPage = homePage.returnSignUpPage();
        assertNotNull(signUpPage);
        getProperties();
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void ifPatientAlreadyExists() {
        signUpPage.typeMobilePhone(MOBILE_LOGIN);
        signUpPage.submit();
        Assert.assertTrue(ifWarningContains(WARNING_PATIENT_EXISTS));
    }

    @Test
    public void ifDocAlreadyExists() {
        signUpPage.openDocTab();
        signUpPage.typeMobilePhone(MOBILE_LOGIN);
        signUpPage.typeEmail(SUPPORT_EMAIL);
        signUpPage.typePassword(PATIENT_PASS);
        signUpPage.typeDocFIO(SURNAME, NAME, MIDDLE_NAME);
        selectSignUpFilters(SPECIALITY_PLACEHOLDER, SUPPORT_SPECIALITY);
        signUpPage.submit();
        Assert.assertTrue(ifWarningContains(WARNING_DOC_EXISTS));
    }

    @Test
    public void checkPatientNumberWarning() {
        signUpPage.typeMobilePhone(FAKE_MOBILE_NUMBER);
        signUpPage.showPatientNumberWarning();
        Assert.assertTrue(ifHelpTextContains(WARNING_CORRECT_NUMBER));
    }

    @Test
    public void checkDocNumberWarning() {
        signUpPage.openDocTab();
        signUpPage.typeMobilePhone(FAKE_MOBILE_NUMBER);
        signUpPage.showDocNumberWarning();
        Assert.assertTrue(ifHelpTextContains(WARNING_CORRECT_NUMBER));
    }

    @Test(enabled = false) //in process
    public void successPatientSignUp() throws SQLException {
        updateMobileNumberIfUserExists();
    }

    @Test(enabled = false) //in process
    public void successDocSignUp() throws SQLException {
        updateMobileNumberIfUserExists();
    }

    private void updateMobileNumberIfUserExists() throws SQLException {
        DBUpdateNumber.main();
    }

    private boolean ifWarningContains(CharSequence charSequence) {
        String textOfWarning = signUpPage.getTextOfWarning();
        return textOfWarning.contains(charSequence);
    }

    private boolean ifHelpTextContains(CharSequence charSequence) {
        String helpText = signUpPage.gethelpText();
        return helpText.contains(charSequence);
    }

    private void selectSignUpFilters(String placeholder, String option) {
        WebElement select = signUpPage.returnSelect(placeholder);
        assertNotNull(select);
        selectSignUpOption(option, select);
    }

    private void selectSignUpOption(String optionName, WebElement select) {
        signUpPage.selectOption(optionName, select);
        Assert.assertTrue(signUpPage.optionSelected(optionName, select));
    }

/*    private String returnSmsCode() throws SQLException {
        DBReturnSmsCode dbAction = new DBReturnSmsCode();
        return dbAction.returnSmsCode();
    }

    private void endSignUp() throws SQLException {
        signUpPage.submit();
        String smsCode = returnSmsCode();
        Assert.assertNotNull(smsCode);
        signUpPage.typeCode(smsCode);
        signUpPage.submit();
    }

    private void tryLoginNewUser() {
        SignInPage signInPage = PageFactory.initElements(driver, SignInPage.class); //?????
        signInPage.typeToLoginAndPass(signUpPage.randomEmail, PATIENT_PASS);
        signInPage.submit();
        HomePage homePage = signInPage.returnHomePage();
        Assert.assertNotNull(homePage);
        Assert.assertTrue(homePage.ifUserSignedIn());
    }*/

    private void getProperties() {
        MOBILE_LOGIN = testProperties.getProperty("mobileLogin");
        WARNING_PATIENT_EXISTS = testProperties.getProperty("warningPatientExists");
        FAKE_MOBILE_NUMBER = testProperties.getProperty("expirationMonth");
        WARNING_CORRECT_NUMBER = testProperties.getProperty("mobileNumberHelpText");

        //PATIENT_LOGIN = testProperties.getProperty("correctPatientLogin");
        PATIENT_PASS = testProperties.getProperty("oneToFivePass");

        WARNING_DOC_EXISTS = testProperties.getProperty("warningDocExists");
        SUPPORT_EMAIL = testProperties.getProperty("supportDocLogin");
        NAME = testProperties.getProperty("name");
        MIDDLE_NAME = testProperties.getProperty("middleName");
        SURNAME = testProperties.getProperty("surname");
        SPECIALITY_PLACEHOLDER = testProperties.getProperty("specialityPlaceholder");
        SUPPORT_SPECIALITY = testProperties.getProperty("supportSpeciality");
    }
}
