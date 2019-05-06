package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SignUpPage extends BasePage {

    @FindBy(css = "a[href='/signup/doctor']")
    private WebElement docTab;
    @FindBy(css = "input[type='tel']")
    private WebElement mobileField;
    @FindBy(css = "button[type='submit']")
    private WebElement submitBtn;
    @FindBy(className = "signup-step-description")
    private WebElement signUpDescription;
    @FindBy(className = "form-field__help-text")
    private WebElement helpText;
    @FindBy(css = ".__alert--danger")
    private WebElement alert;
    @FindBy(css = "input[type='email']")
    private WebElement emailField;
    @FindBy(css = "input[type='password']")
    private WebElement docPassField;
    @FindBy(className = "__input-repassword")
    private WebElement docRepeatPassField;
    @FindBy(className = "__input-lastName")
    private WebElement docSurnameField;
    @FindBy(xpath = "(//input[@type='text'])[2]")
    private WebElement docNameField;
    @FindBy(className = "__input-middleName")
    private WebElement docMiddleNameField;
    @FindBy(className = "Select-control")
    private List<WebElement> selectorList;
    @FindBy(className = "Select-menu-outer")
    private List<WebElement> selectOptionsList;
    /*@FindBy(xpath = "(//input[@class='mg-form-input'])")
    private WebElement codeField;*/

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void typeMobilePhone(String mobileNumber) {
        waitForVisibilityOf(mobileField, returnRandomSeconds());
        sendKeys(mobileField, mobileNumber);
    }

    public void submit() {
        submitBtn.click();
        //pressEnterKeyOn(mobileField);
    }

    public void showDocNumberWarning() {
        emailField.click();
    }

    public void showPatientNumberWarning() {
        signUpDescription.click();
    }

    public String gethelpText() {
        waitForVisibilityOf(helpText, returnRandomSeconds());
        return helpText.getText();
    }

    public String getTextOfWarning() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", alert);
        return alert.getText();
    }

    public void openDocTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", docTab);
        docTab.click();
    }

    public void typePassword(String pass) {
        sendKeys(docPassField, pass);
        sendKeys(docRepeatPassField, pass);
    }

    public void typeDocFIO(String surname, String name, String middleName) {
        sendKeys(docSurnameField, surname);
        sendKeys(docNameField, name);
        sendKeys(docMiddleNameField, middleName);
    }

    public void typeEmail(String email) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailField);
        sendKeys(emailField, email);
    }

    public WebElement returnSelect(String placeholder) {
        return returnSelector(placeholder, selectorList);
    }

    public void selectOption(String optionName, WebElement select) {
        selectOption(optionName, select, selectOptionsList);
    }

    /*public void typeCode(String smsCode) {
        waitForVisibilityOf(codeField);
        sendKeys(codeField, smsCode);
    }*/

}
