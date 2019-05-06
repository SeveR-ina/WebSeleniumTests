package Pages.SignInPages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInVkPage extends BasePage {
    @FindBy(name = "email")
    private WebElement loginField;
    @FindBy(name = "pass")
    private WebElement passField;
    @FindBy(id = "install_allow")
    private WebElement submitButton;

    public SignInVkPage(WebDriver driver) {
        super(driver);
    }

    public void signIn(String login, String pass) {
        typeEmailPass(loginField, login, passField, pass);
        submitButton.click();
    }


}
