import Pages.CPPages.CPChatsPage;
import Pages.CPPages.CPSignInPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class CPChatsTests extends BeforeTests {
    private String ADMIN_LOGIN, ADMIN_PASS, SAMPLE_DOC, CP_URL, TAT_NAME,
            CORPORATION_OPTION_ZERO, FILTER_CORP;
    private String TATNEFT_ADMIN, TAT_ADM_PASS;
    private CPChatsPage cpChatsPage;
    private CPSignInPage cpSignInPage;

    @Parameters({"browser"})
    CPChatsTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openCPChatsPage(String browser) {
        getProperties();
        openNewWindow(browser, CP_URL);
        cpSignInPage = PageFactory.initElements(driver, CPSignInPage.class);
        assertNotNull(cpSignInPage);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void tatAdminHasAccess() {
        signInOnCP(cpSignInPage, TATNEFT_ADMIN, TAT_ADM_PASS);
    }

    @Test
    public void findChatByDoc() {
        signInOnCP(cpSignInPage, ADMIN_LOGIN, ADMIN_PASS);
        cpChatsPage.searchChatByDoc(SAMPLE_DOC);
        WebElement chatLine = cpChatsPage.returnDocElementWithText(SAMPLE_DOC); //TODO: element is not attached to the page document
        assertNotNull(chatLine);
    }

    @Test
    public void findChatByPatient() {
        signInOnCP(cpSignInPage, ADMIN_LOGIN, ADMIN_PASS);
        cpChatsPage.searchChatByPatient(TAT_NAME);
        WebElement chatLine = cpChatsPage.returnPatientElementWithText(TAT_NAME);
        assertNotNull(chatLine);
    }

    @Test(enabled = false)
    public void findPaidChats() {
        signInOnCP(cpSignInPage, ADMIN_LOGIN, ADMIN_PASS);
        cpChatsPage.addFilter(FILTER_CORP);
        cpChatsPage.pickTheCorporation(CORPORATION_OPTION_ZERO);
    }

    private void signInOnCP(CPSignInPage cpSignInPage, String login, String pass) {
        assertNotNull(cpSignInPage);
        cpSignInPage.signIn(login, pass);
        cpChatsPage = cpSignInPage.returnCPChatsPage();
        assertNotNull(cpChatsPage);
        Assert.assertTrue(cpChatsPage.cpChatsAreVisible());
    }

    private void getProperties() {
        TATNEFT_ADMIN = testProperties.getProperty("tatneftAdmin");
        TAT_ADM_PASS = testProperties.getProperty("allOnesPass");

        CP_URL = testProperties.getProperty("cpSignInPageURL");

        ADMIN_LOGIN = testProperties.getProperty("cpLogIn");
        ADMIN_PASS = testProperties.getProperty("cpPass");

        SAMPLE_DOC = testProperties.getProperty("sampleDoc");
        TAT_NAME = testProperties.getProperty("tatName");
        FILTER_CORP = testProperties.getProperty("cpFilterCorp");
        CORPORATION_OPTION_ZERO = testProperties.getProperty("corporationNo"); //changeable
    }
}
