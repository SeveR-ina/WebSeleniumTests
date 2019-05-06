package Pages.CPPages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CPSignInPage extends BasePage {
    @FindBy(name = "username")
    private WebElement loginField;
    @FindBy(name = "password")
    private WebElement passField;

    public CPSignInPage(WebDriver driver) {
        super(driver);
    }

    public void signIn(String email, String pass) {
        typeLoginAndPass(email, pass);
        submit();
    }

    public CPChatsPage returnCPChatsPage() {
        return PageFactory.initElements(driver, CPChatsPage.class);
    }

    private void typeLoginAndPass(String login, String pass) {
        typeEmailPass(loginField, login, passField, pass);
    }

    private void submit() {
        pressEnterKeyOn(passField);
    }
}
