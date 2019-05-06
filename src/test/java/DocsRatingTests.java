import Pages.DocRatingsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class DocsRatingTests extends BeforeTests {

    private DocRatingsPage docRatingsPage;

    @Parameters({"browser"})
    DocsRatingTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openDocsRatingPage(String browser) {
        openBrowsers(browser);
        homePage = returnHomePage();
        assertNotNull(homePage);
        docRatingsPage = homePage.returnDocsRatingListPage();
        assertNotNull(docRatingsPage);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void checkRating() {
        Assert.assertTrue(docRatingsPage.ifRatingsSorted());
    }

    @Test
    public void sendADocRequest() {
        Assert.assertTrue(docRatingsPage.docsAreVisible());
        docRatingsPage.makeADocRequest();
        Assert.assertTrue((docRatingsPage.ifRequestIsSent()));
    }

}