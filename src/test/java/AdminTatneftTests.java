import Pages.CPPages.CPChatsPage;
import Pages.CPPages.CPSignInPage;
import Pages.SignInPages.SignInPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class AdminTatneftTests extends BeforeTests {
    private String TATNEFT_ADMIN, ALL_ONES_PASS;

    @Parameters({"browser"})
    AdminTatneftTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openAskDocPage(String browser) {
        openBrowsers(browser);
        getProperties();
        homePage = returnHomePage();
        assertNotNull(homePage);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void chiefDocHasAccessToAdminPanel() {
        signIn(TATNEFT_ADMIN, ALL_ONES_PASS); //sign in on the main site
        CPSignInPage cpSignInPage = returnCPSignInPage();
        assertNotNull(cpSignInPage);

        signInOnCP(cpSignInPage, TATNEFT_ADMIN, ALL_ONES_PASS);
        CPChatsPage cpChatsPage = cpSignInPage.returnCPChatsPage();
        assertNotNull(cpChatsPage);

        Assert.assertTrue(cpChatsPage.cpChatsAreVisible());
    }

    private void signIn(String login, String pass) {
        SignInPage signInPage = homePage.returnSignInPage();
        assertNotNull(signInPage);
        signIn(signInPage, login, pass);
        homePage = returnHomePage();
        assertNotNull(homePage);
    }

    private CPSignInPage returnCPSignInPage() {
        Assert.assertTrue(homePage.adminPanelLinkIsVisible());
        return homePage.returnCPSignInPage();
    }

    private void signInOnCP(CPSignInPage cpSignInPage, String login, String pass) {
        assertNotNull(cpSignInPage);
        cpSignInPage.signIn(login, pass);
    }

    private void getProperties() {
        TATNEFT_ADMIN = testProperties.getProperty("tatneftAdmin");
        ALL_ONES_PASS = testProperties.getProperty("allOnesPass");
    }
}
