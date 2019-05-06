import Pages.HomePage;
import Pages.QuestionForPage;
import Pages.QuestionToDocPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class AskPageTest extends BeforeTests {
    private HomePage homePage;
    private QuestionForPage questionPage;
    private String SUPPORT_FIO, PATIENT_PASS, PATH_TO_FILE;

    @Parameters({"browser"})
    AskPageTest(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openAskDocPage(String browser) {
        openBrowsers(browser);
        getProperties();
        homePage = returnHomePage();
        assertNotNull(homePage);
        openQuestionPage(SUPPORT_FIO);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void checkAskButtonIsDisabled() {
        questionPage.typeToField("anamnesis", PATIENT_PASS);
        questionPage.clickOnAddFileBtn();
        questionPage.copyPasteString(PATH_TO_FILE);
        Assert.assertTrue(questionPage.buttonIsDisabled());
    }

    @Test
    public void checkAskButtonIsEnabled() {
        questionPage.typeToField("healthComplaints", PATIENT_PASS);
        Assert.assertFalse(questionPage.buttonIsDisabled());
        questionPage.typeToField("healthComplaints", "");
        questionPage.typeToField("question", PATIENT_PASS);
        Assert.assertFalse(questionPage.buttonIsDisabled());
    }

    private void openQuestionPage(String docFIO) {
        QuestionToDocPage questionToDocPage = findDoc(homePage, docFIO);
        assertNotNull(questionToDocPage);
        questionPage = questionToDocPage.returnQuestionForPage();
        assertNotNull(questionPage);
    }

    private void getProperties() {
        SUPPORT_FIO = testProperties.getProperty("supportFIO");
        PATIENT_PASS = testProperties.getProperty("oneToFivePass");
        PATH_TO_FILE = testProperties.getProperty("filePath");
    }


}
