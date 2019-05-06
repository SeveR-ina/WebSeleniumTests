import Pages.QuestionToDocPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class HomeTests extends BeforeTests {
    private String SUPPORT_FIO, NOT_EXISTED_DOC;
    private QuestionToDocPage questionToDocPage;

    @Parameters({"browser"})
    HomeTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openHomePage(String browser) {
        openBrowsers(browser);
        homePage = returnHomePage();
        assertNotNull(homePage);
        getProperties();
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void findDoc() {
        questionToDocPage = findDoc(homePage, SUPPORT_FIO);
        assertNotNull(questionToDocPage);
        assertNotNull(questionToDocPage.returnDoc());
    }

    @Test
    public void findNoDoc() {
        questionToDocPage = findDoc(homePage, NOT_EXISTED_DOC);
        assertNotNull(questionToDocPage);
        Assert.assertTrue(questionToDocPage.doctorFio.isEmpty());
    }

    private void getProperties() {
        SUPPORT_FIO = testProperties.getProperty("supportFIO");
        NOT_EXISTED_DOC = testProperties.getProperty("surname");
    }
}
