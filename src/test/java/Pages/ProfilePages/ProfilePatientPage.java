package Pages.ProfilePages;

import Pages.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;

public class ProfilePatientPage extends BasePage {
    @FindBy(className = "balance__input")
    private WebElement paymentField;
    @FindBy(className = "balance__link")
    private WebElement payButton;
    @FindBy(className = "balance__value")
    private WebElement balanceSum;
    @FindBy(xpath = "(//div[@class='form-field'])[4]/input")
    private WebElement forthInput;
    @FindBy(xpath = "(//div[@class='form-field'])[3]/input")
    private WebElement thirdInput;
    @FindBy(xpath = "(//div[@class='form-field'])[2]/input")
    private WebElement secondInput;
    @FindBy(css = ".form-field.error > input")
    private WebElement passRepeatInput;
    @FindBy(xpath = "(//div[@class='radio__control'])[1]")
    private WebElement male1;
    @FindBy(className = "__input-gender__male")
    private WebElement male;
    @FindBy(xpath = "(//div[@class='radio__control'])[2]")
    private WebElement female1;
    @FindBy(className = "__input-gender__female")
    private WebElement female;
    @FindBy(className = "alert")
    private WebElement alert;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;
    @FindBy(xpath = "//a[contains(@class,'v-menu__link') and contains(., 'Пароль')]")
    private WebElement passMenuItem;
    @FindBy(xpath = "//a[contains(@class,'v-menu__link') and contains(., 'данные')]")
    private WebElement personalDataMenuItem;
    @FindBy(xpath = "//label[contains(.,'Текущий пароль')]")
    private WebElement labelPassNow;
    @FindBy(className = "profile__fio")
    private WebElement fioHeader;
    @FindBy(xpath = "//a[contains(text(), 'Выход')]")
    private WebElement signOutLink;
    private boolean finalGenderIsMale;
    private String middleName;

    public ProfilePatientPage(WebDriver driver) {
        super(driver);
    }

    public MonetaPage returnMonetaPage(String sum) {
        sendKeys(paymentField, sum);
        payButton.click();
        return PageFactory.initElements(driver, MonetaPage.class);
    }

    public int getCurrentBalance() {
        waitForVisibilityOf(balanceSum, returnRandomSeconds());
        String sum = balanceSum.getText();
        return Integer.parseInt(sum);
    }

    public void changeGender() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", male1);
        waitForVisibilityOf(male1, returnRandomSeconds());
        String str = male.getAttribute("checked");
        if (str == null) {
            male1.click();
            finalGenderIsMale = true;
        } else {
            female1.click();
            finalGenderIsMale = false;
        }
    }

    public boolean returnFinalGender() {
        return finalGenderIsMale;
    }

    public void signOut() {
        signOut(signOutLink);
    }

    public void changeMiddleName() {
        middleName = generateRandomInt();
        sendKeys(forthInput, middleName);
    }

    public String returnMiddleName() {
        return middleName;
    }

    public void saveButtonClick() {
        saveButton.click();
        waitForVisibilityOf(alert, returnRandomSeconds());
    }

    public void typeToPassFields(String passNow, String newPass, String passRepeat) {
        sendKeys(secondInput, passNow);
        sendKeys(thirdInput, newPass);
        sendKeys(passRepeatInput, passRepeat);
    }

    public String getAlertText() {
        return alert.getText();
    }

    public boolean fioHeaderContainsMiddleName(String middleName) {
        return fioHeader.getText().contains(middleName);
    }

    public boolean checkGenderUpdated(boolean finalGenderIsMale) {
        if (finalGenderIsMale) {
            return male.isSelected();
        }
        return female.isSelected();
    }

    public void openPassFormAndBack() {
        openPassForm();
        personalDataMenuItem.click();
        waitForVisibilityOf(fioHeader, returnRandomSeconds());
    }

    private String generateRandomInt() {
        int randomInt = 0;
        Random r = new Random();
        for (int i = 1000; i <= 100000; ++i) {
            randomInt = r.nextInt(100000);
        }
        return Integer.toString(randomInt);
    }

    public void clearFIO() { //just clear doesnt work
        clearField(secondInput);
        clearField(forthInput);
        clearField(thirdInput);
    }

    public void openPassForm() {
        passMenuItem.click();
        waitForVisibilityOf(labelPassNow, returnRandomSeconds());
    }

    private void clearField(WebElement webElement) {
        webElement.sendKeys(Keys.CONTROL + "a");
        webElement.sendKeys(Keys.DELETE);
    }

}
