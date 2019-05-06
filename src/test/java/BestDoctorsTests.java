import Pages.DocPage;
import Pages.DocRatingsPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class BestDoctorsTests extends BeforeTests {

    private DocRatingsPage docRatingsPage;
    private String SPECIALITY_PLACEHOLDER;
    private String DOCS_SPECIALITY_OPTION, DOCS_SENIORITY_OPTION, DOCS_CITY_OPTION, FIO_DOC;
    private String DOCS_SENIORITY_PLACEHOLDER;
    private String DOCS_CITY_PLACEHOLDER;

    @Parameters({"browser"})
    BestDoctorsTests(String browser) throws IOException {
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
        getProperties();
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

    @Test
    public void checkDocFilters() {
        docRatingsPage = homePage.returnDocsRatingListPage();
        assertNotNull(docRatingsPage);
        selectDocRatingFilters(SPECIALITY_PLACEHOLDER, DOCS_SPECIALITY_OPTION, 0);
        selectDocRatingFilters(DOCS_CITY_PLACEHOLDER, DOCS_CITY_OPTION, 1);
        selectDocRatingFilters(DOCS_SENIORITY_PLACEHOLDER, DOCS_SENIORITY_OPTION, 2);
        DocPage docPage = docRatingsPage.returnDocPage(FIO_DOC);
        assertNotNull(docPage);
        Assert.assertTrue(docPage.specialityOk(DOCS_SPECIALITY_OPTION));
        Assert.assertTrue(docPage.docCardDetailOk(DOCS_SENIORITY_OPTION, false));
        Assert.assertTrue(docPage.cityOk(DOCS_CITY_OPTION));
    }

    private void selectDocRatingFilters(String placeholder, String option, int index) {
        WebElement select = docRatingsPage.returnSelect(placeholder);
        assertNotNull(select);
        selectDocRatingOption(option, select, index);
    }

    private void selectDocRatingOption(String optionName, WebElement selector, int index) {
        docRatingsPage.selectOption(optionName, selector);
        selector = docRatingsPage.returnSelectlabel(index);
        Assert.assertTrue(docRatingsPage.optionSelected(optionName, selector));
    }

    private void getProperties() {
        DOCS_SPECIALITY_OPTION = testProperties.getProperty("speciality");
        DOCS_CITY_OPTION = testProperties.getProperty("city");
        DOCS_SENIORITY_OPTION = testProperties.getProperty("seniority");
        FIO_DOC = testProperties.getProperty("paid_fio_doc");
        SPECIALITY_PLACEHOLDER = testProperties.getProperty("specialityPlaceholder");
        DOCS_SENIORITY_PLACEHOLDER = testProperties.getProperty("seniorityPlaceholder");
        DOCS_CITY_PLACEHOLDER = testProperties.getProperty("cityPlaceholder");
        SPECIALITY_PLACEHOLDER = testProperties.getProperty("specialityPlaceholder");
    }

}