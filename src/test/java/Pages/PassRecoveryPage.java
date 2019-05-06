package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PassRecoveryPage extends BasePage {
    @FindBy(css = "input[placeholder='Введите телефон или e-mail']")
    private WebElement loginField;
    @FindBy(css = ".__alert--danger")
    private WebElement errorAlert;

    public PassRecoveryPage(WebDriver driver) {
        super(driver);
    }

    public void submit() {
        waitForVisibilityOf(loginField, returnRandomSeconds());
        pressEnterKeyOn(loginField);
    }

    public String getErrorText() {
        return errorAlert.getText();
    }

    public void sendLogin(String login){
        sendKeys(loginField, login);
    }

}
