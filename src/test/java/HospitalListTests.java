import Pages.ClinicsPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class HospitalListTests extends BeforeTests {
    private ClinicsPage clinicsPage;
    private String CLINIC;

    @Parameters({"browser"})
    HospitalListTests(String browser) throws IOException {
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
        clinicsPage.typeToSearchField(CLINIC);
        assertNotNull(clinicsPage.returnFoundClinic(CLINIC));
    }

    private void getProperties() {
        CLINIC = testProperties.getProperty("sampleClinic");
    }
}
