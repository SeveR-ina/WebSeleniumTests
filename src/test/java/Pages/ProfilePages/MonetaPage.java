package Pages.ProfilePages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MonetaPage extends BasePage {
    @FindBy(className = "total_sum")
    private WebElement totalSum;
    @FindBy(className = "warning")
    private WebElement warning;
    @FindBy(id = "additionalParameters_cardNumber")
    private WebElement cardNumberField;
    @FindBy(id = "cardExpirationMonth")
    private WebElement expirationMonthField;
    @FindBy(id = "cardExpirationYear")
    private WebElement expirationYearField;
    @FindBy(id = "additionalParameters_cardCVV2")
    private WebElement cvvField;
    @FindBy(name = "_do_next")
    private WebElement submitButton;
    @FindBy(className = "link_return")
    private WebElement returnLink;
    @FindBy(id = "content")
    private WebElement errorAlert;

    public MonetaPage(WebDriver driver) {
        super(driver);
    }

    public ProfilePatientPage returnProfilePatientPage() {
        submitButton.click();
        return PageFactory.initElements(driver, ProfilePatientPage.class);
    }

    public void typeCardNumberAndCvv(String cardNumber, String cvv) {
        sendKeys(cardNumberField, cardNumber);
        sendKeys(cvvField, cvv);
    }

    public boolean totalSumIsRight(String sum) {
        if (monetaPageIsVisible()) {
            return totalSum.getText().contains(sum);
        }
        return false;
    }

    public boolean itsTestMode(String testModeWarning) {
        return warning.getText().equals(testModeWarning);
    }

    public void selectFilterOnMoneta(boolean monthSelector, String optionName) {
        Select select;
        if (monthSelector) {
            select = new Select(expirationMonthField);
        } else {
            select = new Select(expirationYearField);
        }
        select.selectByVisibleText(optionName);
    }

    public ProfilePatientPage returnProfilePageWithReturnLink() {
        if (monetaPageIsVisible()) {
            returnLink.click();
            return PageFactory.initElements(driver, ProfilePatientPage.class);
        }
        return null;
    }

    public boolean monetaPageIsVisible() {
        waitForVisibilityOf(returnLink, 10);
        return returnLink.isDisplayed();
    }

    public String getAlertText() {
        return errorAlert.getText();
    }

}
