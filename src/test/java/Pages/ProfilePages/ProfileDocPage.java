package Pages.ProfilePages;

import Pages.BasePage;
import Pages.HomePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProfileDocPage extends BasePage {
    @FindBy(xpath = "//a[contains(@class,'v-menu__link') and contains(., 'врача')]")
    private WebElement docCard;
    @FindBy(xpath = "(//span[contains(text(), '×')])[1]")
    private WebElement clearSelectOne;
    @FindBy(xpath = "(//div[@class='form-field'])[4]/input")
    private WebElement educationFieldToClear;
    @FindBy(xpath = "(//div[@class='form-field'])[6]/textarea")
    private WebElement specializationFieldToClear;
    @FindBy(xpath = "(//div[@class='form-field'])[7]/input")
    private WebElement proceduresFieldToClear;
    @FindBy(xpath = "(//div[contains(@class, 'form-field error')])[2]/input")
    private WebElement educationField;
    @FindBy(xpath = "(//div[contains(@class, 'form-field error')])[2]/textarea")
    private WebElement specializationField;
    @FindBy(xpath = "(//div[contains(@class, 'form-field error')])[2]/input")
    private WebElement proceduresField;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;
    @FindBy(className = "alert")
    private WebElement alert;
    @FindBy(xpath = "//span[contains(@class,'nav-menu__item-title') and contains(., 'главная')]")
    private WebElement homeMenuItem;
    @FindBy(className = "Select-control")
    private List<WebElement> selectorList;
    @FindBy(className = "Select-menu-outer")
    private List<WebElement> selectOptionsList;

    public ProfileDocPage(WebDriver driver) {
        super(driver);
    }

    public void openDocCardForm() {
        docCard.click();
        waitForVisibilityOf(educationFieldToClear, returnRandomSeconds());
    }

    public void updateTextFields(String education, String specialization, String healMethods) {
        sendKeys(educationField, education);
        sendKeys(specializationField, specialization);
        sendKeys(proceduresField, healMethods);
    }

    public void clearSelects() {
        clearSelectOne.click();
        clearSelectOne.click();
    }

    public WebElement returnSelect(String placeholder) {
        return returnSelector(placeholder, selectorList);
    }

    public void saveButtonClick() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);
        saveButton.click();
        waitForVisibilityOf(alert, returnRandomSeconds());
    }

    public String getAlertText() {
        return alert.getText();
    }

    public HomePage returnHomePage() {
        return returnHomePage(homeMenuItem);
    }

    public void clearTextFields() { //just clear doesnt work
        clearField(educationFieldToClear);
        clearField(specializationFieldToClear);
        clearField(proceduresFieldToClear);
    }

    private void clearField(WebElement webElement) {
        webElement.sendKeys(Keys.CONTROL + "a");
        webElement.sendKeys(Keys.DELETE);
    }

    public void selectOption(String optionName, WebElement select) {
        selectOption(optionName, select, selectOptionsList);
    }
}
