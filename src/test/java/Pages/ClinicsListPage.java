package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ClinicsListPage extends BasePage {

    @FindBy(className = "__hospital-search__input")
    private WebElement searchField;
    @FindBy(className = "hospital")
    private WebElement clinic;

    public ClinicsListPage(WebDriver driver) {
        super(driver);
    }

    public WebElement returnFoundClinic() {
        return clinic;
    }

    public void typeToSearchField(String searchQuery) {
        sendKeys(searchField, searchQuery);
    }

}
