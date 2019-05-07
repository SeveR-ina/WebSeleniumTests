import Pages.AdminPages.AdminChatsPage;
import Pages.AdminPages.AdminSignInPage;
import Pages.SignInPages.SignInPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class AdminTests extends BeforeTests {
    private String TATNEFT_ADMIN, ALL_ONES_PASS;

    @Parameters({"browser"})
    AdminTests(String browser) throws IOException {
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
        AdminSignInPage adminSignInPage = returnAdminSignInPage();
        assertNotNull(adminSignInPage);

        signInOnAdminPanel(adminSignInPage, TATNEFT_ADMIN, ALL_ONES_PASS);
        AdminChatsPage adminChatsPage = adminSignInPage.returnAdminChatsPage();
        assertNotNull(adminChatsPage);

        Assert.assertTrue(adminChatsPage.adminChatsAreVisible());
    }

    private void signIn(String login, String pass) {
        SignInPage signInPage = homePage.returnSignInPage();
        assertNotNull(signInPage);
        signIn(signInPage, login, pass);
        homePage = returnHomePage();
        assertNotNull(homePage);
    }

    private AdminSignInPage returnAdminSignInPage() {
        Assert.assertTrue(homePage.adminPanelLinkIsVisible());
        return homePage.returnAdminSignInPage();
    }

    private void signInOnAdminPanel(AdminSignInPage adminSignInPage, String login, String pass) {
        assertNotNull(adminSignInPage);
        adminSignInPage.signIn(login, pass);
    }

    private void getProperties() {
        TATNEFT_ADMIN = testProperties.getProperty("tatneftAdmin");
        ALL_ONES_PASS = testProperties.getProperty("allOnesPass");
    }
}
