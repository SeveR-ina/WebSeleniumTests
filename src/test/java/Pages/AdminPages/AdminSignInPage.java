package Pages.AdminPages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminSignInPage extends BasePage {
    @FindBy(name = "username")
    private WebElement loginField;
    @FindBy(name = "password")
    private WebElement passField;
    /*@FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;*/

    public AdminSignInPage(WebDriver driver) {
        super(driver);
    }

    public void signIn(String email, String pass) {
        typeToLoginAndPass(email, pass);
        submit();
    }

    public AdminChatsPage returnAdminChatsPage(){
        return PageFactory.initElements(driver, AdminChatsPage.class);
    }

    private void typeToLoginAndPass(String login, String pass) {
        typeToEmailPass(loginField, login, passField, pass);
    }

    private void submit() {
        pressEnterKeyOn(passField);
    }
}
