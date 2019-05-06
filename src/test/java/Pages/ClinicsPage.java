package Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ClinicsPage extends BasePage {

    @FindBy(className = "__hospital-search__input")
    private WebElement searchField;
    @FindBy(className = "hospital__name")
    private List<WebElement> clinicsList;

    public ClinicsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement returnFoundClinic(String clinicName) {
        WebElement clinic = null;
        if (clinicsList.size() > 0) {
            for (WebElement clinicItem : clinicsList) {
                if (clinicItem.getText().contains(clinicName)) {
                    clinic = clinicItem;
                    break;
                }
            }
        }
        return clinic;
    }

    public ClinicPage returnClinicPage(String clinicName) {
        returnFoundClinic(clinicName).click();
        return PageFactory.initElements(driver, ClinicPage.class);
    }

    public void typeToSearchField(String clinicName) throws InterruptedException {
        sendKeys(searchField, clinicName);
        Thread.sleep(1500);
    }

}
