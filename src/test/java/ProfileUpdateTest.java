import Pages.DocPage;
import Pages.HomePage;
import Pages.ProfilePages.MonetaPage;
import Pages.ProfilePages.ProfileDocPage;
import Pages.ProfilePages.ProfilePatientPage;
import Pages.QuestionToDocPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class ProfileUpdateTest extends BeforeTests {
    private String PATIENT_LOGIN, PATIENT_PASS,
            PAID_DOC_PASS, PASS_UPDATED_ALERT, PROFILE_UPDATE_ALERT;
    private MonetaPage monetaPage;
    private ProfileDocPage profileDocPage;
    private ProfilePatientPage profilePatientPage;
    private QuestionToDocPage questionToDocPage;
    private DocPage docPage;
    private String TARIFF_FOR_SELECT, TARIFF_DEFAULT, CLINIC_DEFAULT, HEALTH_METHODS, EDUCATION_DEFAULT, SELECT_PLACEHOLDER;
    private String PAID_DOC_LOGIN;
    private String PAYMENT_SUM, CARD_NUMBER, EXPIRATION_MONTH, EXPIRATION_YEAR, CVV, WARNING_TEST_MODE;
    private String TARIFF, DISCOUNT_TARIFF, DISCOUNT_PERCENTAGE,
            YEAR_OPTION, CLINIC, SUPPORT_SPECIALITY, FIO_DOC, DOCS_SPECIALITY_OPTION;

    @Parameters({"browser"})
    ProfileUpdateTest(String browser) throws IOException {
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
    public void updateProfile() throws InterruptedException {
        setProfilePage("patient");
        updateProfileData();
        checkIfProfileUpdated();
    }

    @Test(enabled = false)
    public void updatePass() throws InterruptedException {
        setProfilePage("patient");
        updatePass(PATIENT_PASS, PAID_DOC_PASS, PAID_DOC_PASS);
        profilePatientPage.signOut();
        HomePage homePage = profilePatientPage.returnHomePage();
        assertNotNull(homePage);
        signInPage = homePage.returnSignInPage();
        homePage = signInAndReturnHomePage(signInPage, PATIENT_LOGIN, PAID_DOC_PASS);
        profilePatientPage = homePage.returnProfilePatientPage();
        updatePass(PAID_DOC_PASS, PATIENT_PASS, PATIENT_PASS);
    }

    @Test(enabled = false)
    public void checkSuccessPayment() throws InterruptedException {//it works not in parallel, so test is here
        setProfilePage("patient");
        int startBalance = profilePatientPage.getCurrentBalance();
        monetaPage = profilePatientPage.returnMonetaPage(PAYMENT_SUM);
        assertNotNull(monetaPage);
        putUpMoney();
        profilePatientPage = monetaPage.returnProfilePatientPage();
        assertNotNull(profilePatientPage);
        Assert.assertTrue(startBalance < profilePatientPage.getCurrentBalance());
    }

    @Test(enabled = false)
    public void updateDocCard() throws InterruptedException {
        setProfilePage("doc");
        profileDocPage.openDocCardForm();
        changeTariff(TARIFF_DEFAULT, TARIFF_FOR_SELECT);
        changeTextFields(PAID_DOC_PASS, PATIENT_PASS, YEAR_OPTION);
        changeSelects(SELECT_PLACEHOLDER, CLINIC, SUPPORT_SPECIALITY);
        checkIfDocProfileUpdated();
        profileDocPage = docPage.returnProfileDoc();
        assertNotNull(profileDocPage);
        profileDocPage.openDocCardForm();
        changeTariff(TARIFF_FOR_SELECT, TARIFF_DEFAULT);
        changeTextFields(EDUCATION_DEFAULT, DOCS_SPECIALITY_OPTION, HEALTH_METHODS);
        changeSelects(SELECT_PLACEHOLDER, CLINIC_DEFAULT, DOCS_SPECIALITY_OPTION); //не может выбрать medgreat = нужно переписать на ввод текста
    }

    private void setProfilePage(String userRole) throws InterruptedException {
        if (userRole.equalsIgnoreCase("doc")) {
            homePage = signInAndReturnHomePage(signInPage, PAID_DOC_LOGIN, PAID_DOC_PASS);
            profileDocPage = homePage.returnDocProfilePage();
            assertNotNull(profileDocPage);
        } else if (userRole.equalsIgnoreCase("patient")) {
            homePage = signInAndReturnHomePage(signInPage, PATIENT_LOGIN, PATIENT_PASS);
            profilePatientPage = homePage.returnProfilePatientPage();
            assertNotNull(profilePatientPage);
        } else System.out.println("check user role");
    }

    private void changeTariff(String tariffOne, String tariffTwo) {
        profileDocPage.acceptAlertIfExists(); //don't clear this - driver doesn't see next select without this line
        selectDocProfileOption(tariffOne, tariffTwo); //tariff_default - open select with this option and change it to tariffForSelect
    }

    private void changeTextFields(String textOne, String textTwo, String textThree) {
        profileDocPage.clearSelects(); //clear speciality and clinic selects
        profileDocPage.clearTextFields();
        profileDocPage.updateTextFields(textOne, textTwo, textThree);
    }

    private void changeSelects(String selectPlaceholder, String clinic, String supportSpeciality) {
        selectDocProfileOption(selectPlaceholder, clinic);
        selectDocProfileOption(selectPlaceholder, supportSpeciality);
        profileDocPage.saveButtonClick();
    }

    private void selectDocProfileOption(String placeholder, String option) {
        WebElement select = profileDocPage.returnSelect(placeholder);
        assertNotNull(select);
        profileDocPage.selectOption(option, select);
        Assert.assertTrue(profileDocPage.optionSelected(option, select));
    }

    private void checkIfDocProfileUpdated() {
        Assert.assertEquals(profileDocPage.getAlertText(), PROFILE_UPDATE_ALERT);
        findDoc(FIO_DOC);
        docPage = returnViewDocProfile();
        assertNotNull(docPage);
        Assert.assertTrue(docPage.specialityOk(SUPPORT_SPECIALITY));
        Assert.assertEquals(docPage.getClinicName(), CLINIC);
        Assert.assertEquals(docPage.getPaidTariff(), TARIFF);
        Assert.assertEquals(docPage.getDiscountTariff(docPage.getPaidTariff(), DISCOUNT_PERCENTAGE), DISCOUNT_TARIFF); //TODO: вынести в новый тест
        Assert.assertTrue(docPage.docCardDetailOk(YEAR_OPTION, true));
        Assert.assertTrue(docPage.docCardDetailOk(PAID_DOC_PASS, false));
    }

    private void putUpMoney() {
        Assert.assertTrue(monetaPage.totalSumIsRight(PAYMENT_SUM));
        Assert.assertTrue(monetaPage.itsTestMode(WARNING_TEST_MODE));
        monetaPage.typeCardNumberAndCvv(CARD_NUMBER, CVV);
        monetaPage.selectFilterOnMoneta(true, EXPIRATION_MONTH);
        monetaPage.selectFilterOnMoneta(false, EXPIRATION_YEAR);
    }

    private void updatePass(String currentPass, String newPass, String passAgain) {
        profilePatientPage.openPassForm();
        profilePatientPage.typeToPassFields(currentPass, newPass, passAgain);
        profilePatientPage.saveButtonClick();
        Assert.assertEquals(PASS_UPDATED_ALERT, profilePatientPage.getAlertText());
    }

    private void updateProfileData() {
        profilePatientPage.changeGender();
        profilePatientPage.changeMiddleName();
        profilePatientPage.saveButtonClick();
    }

    private void checkIfProfileUpdated() {
        Assert.assertEquals(profilePatientPage.getAlertText(), PROFILE_UPDATE_ALERT);
        profilePatientPage.openPassFormAndBack();
        boolean finalGender = profilePatientPage.returnFinalGender();
        Assert.assertTrue(profilePatientPage.checkGenderUpdated(finalGender));
        Assert.assertTrue(profilePatientPage.fioHeaderContainsMiddleName(profilePatientPage.returnMiddleName()));
    }

    private void findDoc(String lastName) {
        homePage = profileDocPage.returnHomePage();
        questionToDocPage = findDoc(homePage, lastName);
        assertNotNull(questionToDocPage);
        assertNotNull(questionToDocPage.returnDoc());
    }

    private DocPage returnViewDocProfile() {
        return questionToDocPage.returnDocPage(FIO_DOC);
    }

    private void getProperties() {
        PATIENT_LOGIN = testProperties.getProperty("correctPatientLogin");
        PATIENT_PASS = testProperties.getProperty("oneToFivePass");
        PAID_DOC_PASS = testProperties.getProperty("paidDocPass");
        PASS_UPDATED_ALERT = testProperties.getProperty("passUpdatedAlert");
        PROFILE_UPDATE_ALERT = testProperties.getProperty("profileUpdateAlert");
        //***
        DOCS_SPECIALITY_OPTION = testProperties.getProperty("speciality");
        SUPPORT_SPECIALITY = testProperties.getProperty("supportSpeciality");
        PAID_DOC_LOGIN = testProperties.getProperty("paidDocLogin");
        TARIFF_DEFAULT = testProperties.getProperty("tariffDefault");
        TARIFF_FOR_SELECT = testProperties.getProperty("tariffForSelect");
        CLINIC_DEFAULT = testProperties.getProperty("clinicDefault");
        SELECT_PLACEHOLDER = testProperties.getProperty("selectPlaceholder");
        EDUCATION_DEFAULT = testProperties.getProperty("education"); //нужно чтобы исправлять на правильное значение после теста
        HEALTH_METHODS = testProperties.getProperty("healMethods");
        TARIFF = testProperties.getProperty("tariff");
        DISCOUNT_PERCENTAGE = testProperties.getProperty("discountPercentage");
        DISCOUNT_TARIFF = testProperties.getProperty("discountTariff");
        CLINIC = testProperties.getProperty("clinicToChange");
        PROFILE_UPDATE_ALERT = testProperties.getProperty("profileUpdateAlert");
        PAID_DOC_PASS = testProperties.getProperty("allOnesPass");
        YEAR_OPTION = testProperties.getProperty("year");
        FIO_DOC = testProperties.getProperty("paid_fio_doc");
        //***
        PAYMENT_SUM = testProperties.getProperty("paymentSum");
        WARNING_TEST_MODE = testProperties.getProperty("warningTestMode");
        CARD_NUMBER = testProperties.getProperty("cardNumber");
        EXPIRATION_MONTH = testProperties.getProperty("expirationMonth");
        EXPIRATION_YEAR = testProperties.getProperty("expirationYear");
        CVV = testProperties.getProperty("paymentSum");
    }
}
