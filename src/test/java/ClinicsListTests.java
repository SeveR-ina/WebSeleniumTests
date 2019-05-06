import Pages.ClinicsListPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class ClinicsListTests extends BeforeTests {
    private ClinicsListPage clinicsListPage;
    private String CLINIC;

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
        clinicsListPage = homePage.returnClinicsList();
        assertNotNull(clinicsListPage);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Parameters({"browser"})
    @Test
    public void findClinic() {
        clinicsListPage.typeToSearchField(CLINIC);
        assertNotNull(clinicsListPage.returnFoundClinic());
    }

    private void getProperties() {
        CLINIC = testProperties.getProperty("clinic");
    }
}
