import Pages.*;
import Pages.SignInPages.SignInPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class DocPageTests extends BeforeTests {
    private DocPage docPage;
    private String SUPPORT_FIO, PATIENT_EMAIL, ONE_TO_FIVE_NUMBERS, SAMPLE_DOC;

    @Parameters({"browser"})
    DocPageTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openHomePage(String browser) {
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
    public void addDocReview() {
        SignInPage signInPage = homePage.returnSignInPage();
        assertNotNull(signInPage);
        signIn(signInPage, PATIENT_EMAIL, ONE_TO_FIVE_NUMBERS);
        homePage = returnHomePage();
        assertNotNull(homePage);
        openDocPage(SUPPORT_FIO);
        docPage.scrollToReviewForm();
        docPage.typeReviewText(ONE_TO_FIVE_NUMBERS);
        assertTrue(docPage.isSuccessAlertVisible()); //submit and get success alert
    }

    @Test
    public void checkShowTheSameDocs() {
        openDocPage(SAMPLE_DOC);
        QuestionToDocPage questionToDocPage = docPage.returnQuestionToPage();//открывается страница задания вопроса + слэш undefined
        assertNotNull(questionToDocPage);
    }

    private void openDocPage(String doc) {
        QuestionToDocPage questionToDocPage = findDoc(homePage, doc); //open doc rating list with doc
        assertNotNull(questionToDocPage);
        docPage = questionToDocPage.returnDocPage(doc);
        assertNotNull(docPage);
    }

    private void getProperties() {
        SUPPORT_FIO = testProperties.getProperty("supportFIO");
        SAMPLE_DOC = testProperties.getProperty("sampleDoc");
        PATIENT_EMAIL = testProperties.getProperty("correctPatientLogin");
        ONE_TO_FIVE_NUMBERS = testProperties.getProperty("oneToFivePass");
    }

}
