import Pages.ChatsPages.PatientChatPage;
import Pages.QuestionForPage;
import Pages.QuestionToDocPage;
import Pages.SignInPages.SignInPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class MinZdravTests extends BeforeTests {
    private String MINZDRAV_LOGIN, MINZDRAV_PASS, MINZDRAV_DOC_FIO, FREE_PRICE;
    private QuestionForPage questionPage;

    @Parameters({"browser"})
    MinZdravTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openQuestionToDocPage(String browser) {
        openBrowsers(browser);
        homePage = returnHomePage();
        assertNotNull(homePage);
        SignInPage signInPage = homePage.returnSignInPage();
        assertNotNull(signInPage);
        getProperties();
        signIn(signInPage, MINZDRAV_LOGIN, MINZDRAV_PASS);
        homePage = returnHomePage();
        assertNotNull(homePage);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void askMinzdravDoc() {
        openQuestionPage(MINZDRAV_DOC_FIO);
        Assert.assertEquals(questionPage.getChatPrice(), FREE_PRICE);
        questionPage.typeToField("healthComplaints", MINZDRAV_DOC_FIO);
        questionPage.acceptAlertIfExists();
        PatientChatPage patientChatPage = (PatientChatPage) questionPage.pressAskButton(true);
        assertNotNull(patientChatPage);
        Assert.assertTrue(patientChatPage.messageIsVisible());
    }

    private void openQuestionPage(String docFIO) {
        QuestionToDocPage questionToDocPage = findDoc(docFIO);
        assertNotNull(questionToDocPage);
        questionPage = questionToDocPage.returnQuestionForPage();
        assertNotNull(questionPage);
    }

    private QuestionToDocPage findDoc(String docFIO) {
        homePage.typeToSearchField(docFIO);
        return homePage.returnDocsPageWithFoundDoc();
    }

    private void getProperties() {
        MINZDRAV_LOGIN = testProperties.getProperty("minzdravLogin");
        MINZDRAV_PASS = testProperties.getProperty("minzdravPass");
        MINZDRAV_DOC_FIO = testProperties.getProperty("minzdravDocFIO");
        FREE_PRICE = testProperties.getProperty("freePrice");
    }
}

