import Pages.ClinicPage;
import Pages.ClinicsPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class HospitalTests extends BeforeTests {
    private ClinicPage clinicPage;
    private String CLINIC, DOC_FIO;

    @Parameters({"browser"})
    HospitalTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openClinicsList(String browser) throws InterruptedException {
        openBrowsers(browser);
        getProperties();
        homePage = returnHomePage();
        assertNotNull(homePage);

        ClinicsPage clinicsListPage = homePage.returnClinicsList();
        assertNotNull(clinicsListPage);

        clinicsListPage.typeToSearchField(CLINIC);
        clinicPage = clinicsListPage.returnClinicPage(CLINIC);
        assertNotNull(clinicPage);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void searchDoc() throws InterruptedException {
        clinicPage.openDocTab();
        assertNotNull(clinicPage.returnDoc(DOC_FIO));
    }

    private void getProperties() {
        CLINIC = testProperties.getProperty("sampleClinic");
        DOC_FIO = testProperties.getProperty("sampleDoc");
    }
}
