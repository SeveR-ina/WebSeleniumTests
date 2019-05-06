import Pages.ProfilePages.MonetaPage;
import Pages.ProfilePages.ProfilePatientPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class MonetaTests extends BeforeTests {
    private String PATIENT_LOGIN;
    private String PATIENT_PASS;
    private String PAYMENT_SUM;
    private String MONETA_ALERT;
    private MonetaPage monetaPage;
    private ProfilePatientPage profilePatientPage;
    private String url;

    @Parameters({"browser"})
    MonetaTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeClass
    public void openMonetaPage(String browser) throws InterruptedException {
        openBrowsers(browser);
        homePage = returnHomePage();
        assertNotNull(homePage);
        signInPage = homePage.returnSignInPage();
        assertNotNull(signInPage);
        getProperties();
        homePage = signInAndReturnHomePage(signInPage, PATIENT_LOGIN, PATIENT_PASS);
        profilePatientPage = homePage.returnProfilePatientPage();
        assertNotNull(profilePatientPage);
        monetaPage = profilePatientPage.returnMonetaPage(PAYMENT_SUM);
        assertNotNull(monetaPage);
        Assert.assertTrue(monetaPage.monetaPageIsVisible());
        url = driver.getCurrentUrl();
        assertNotNull(url);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test (priority = 1)
    public void checkReturnFromMoneta() {
        profilePatientPage = monetaPage.returnProfilePageWithReturnLink();
        assertNotNull(profilePatientPage);
    }

    @Parameters({"browser"})
    @Test (priority = 2)
    public void checkWrongMonetaSession(String browser) {
        openNewWindow(browser, url);
        MonetaPage monetaPage2 = PageFactory.initElements(driver, MonetaPage.class);
        assertNotNull(monetaPage2);
        Assert.assertEquals(MONETA_ALERT, monetaPage2.getAlertText());
    }

    private void getProperties() {
        PATIENT_LOGIN = testProperties.getProperty("correctPatientLogin");
        PATIENT_PASS = testProperties.getProperty("oneToFivePass");
        PAYMENT_SUM = testProperties.getProperty("paymentSum");
        MONETA_ALERT = testProperties.getProperty("monetaAlert");
    }
}
