package Pages.SignInPages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInOkPage extends BasePage {
    @FindBy(name = "fr.email")
    private WebElement loginField;
    @FindBy(name = "fr.password")
    private WebElement passField;
    @FindBy(css = "input[type='submit']")
    private WebElement submitButton;

    public SignInOkPage(WebDriver driver) {
        super(driver);
    }

    public void signIn(String login, String pass) {
        typeEmailPass(loginField, login, passField, pass);
        submitButton.click();
    }
}
