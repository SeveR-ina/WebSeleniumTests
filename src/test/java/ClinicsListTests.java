import Pages.ClinicsPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class ClinicsListTests extends BeforeTests {
    private ClinicsPage clinicsPage;
    private String SAMPLE_CLINIC;

    @Parameters({"browser"})
    ClinicsListTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openClinicsList(String browser) {
        openBrowsers(browser);
        getProperties();
        homePage = returnHomePage();
        assertNotNull(homePage);
        clinicsPage = homePage.returnClinicsList();
        assertNotNull(clinicsPage);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void findClinic() throws InterruptedException {
        clinicsPage.typeToSearchField(SAMPLE_CLINIC);
        assertNotNull(clinicsPage.returnFoundClinic(SAMPLE_CLINIC));
    }

    private void getProperties() {
        SAMPLE_CLINIC = testProperties.getProperty("sampleClinic");
    }
}
