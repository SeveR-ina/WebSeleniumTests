import Pages.DocPage;
import Pages.QuestionForPage;
import Pages.QuestionToDocPage;
import Pages.SignInPages.SignInPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class CorporationTests extends BeforeTests {
    private String FIRST_PAID_DOC, TATNEFT_LOGIN, ONE_TO_FIVE_PASS, FREE_PRICE;
    private QuestionToDocPage questionToDocPage;

    @Parameters({"browser"})
    CorporationTests(String browser) throws IOException {
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
        signIn(signInPage, TATNEFT_LOGIN, ONE_TO_FIVE_PASS);
        homePage = returnHomePage();
        assertNotNull(homePage);
        questionToDocPage = homePage.returnQuestionToDocPage();
        assertNotNull(questionToDocPage);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void checkFreePriceForCorporationUsers() {
        //4 checks: 1 - price of tatneft doc, 2 - paid doc on question to page, 3 - on doc page, 4 - question for page
        Assert.assertTrue(questionToDocPage.isFirstDocPriceFree(FREE_PRICE));

        questionToDocPage.clickOnCheckBox();
        Assert.assertTrue(questionToDocPage.isPaidDocsListOpened());
        Assert.assertTrue(questionToDocPage.isDocPriceFree(FIRST_PAID_DOC, FREE_PRICE));

        DocPage docPage = questionToDocPage.returnDocPage(FIRST_PAID_DOC);
        assertNotNull(docPage);
        Assert.assertTrue(docPage.isDocPriceFree(FREE_PRICE));

        QuestionForPage questionForPage = docPage.returnQuestionForPage();
        assertNotNull(questionForPage);
        Assert.assertTrue(questionForPage.isActualPriceEquils(FREE_PRICE));
    }


    @Test(enabled = false)
    //The Check Box is switched off by default and its not switchable - test are ignored for undefined time
    public void checkPaidDocsCheckBox() {
        questionToDocPage.clickOnCheckBox(); //switch on
        assertNotNull(questionToDocPage.returnDocFioElement(FIRST_PAID_DOC));
        questionToDocPage.clickOnCheckBox(); //switch off
        WebElement doc = questionToDocPage.returnDocFioElement(FIRST_PAID_DOC);
        Assert.assertNull(doc);
    }

    private void getProperties() {
        TATNEFT_LOGIN = testProperties.getProperty("tatneftLogin");
        ONE_TO_FIVE_PASS = testProperties.getProperty("tatPass");
        FIRST_PAID_DOC = testProperties.getProperty("firstPaidDoc");
        FREE_PRICE = testProperties.getProperty("freePrice");
    }
}
