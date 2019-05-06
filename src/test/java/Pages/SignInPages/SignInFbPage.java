package Pages.SignInPages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInFbPage extends BasePage {
    @FindBy(className = "_2iem")
    private WebElement cancelSignIn;

    public SignInFbPage(WebDriver driver) {
        super(driver);
    }

    public SignInPage returnSignInPage() {
        cancelSignIn.click();
        return PageFactory.initElements(driver, SignInPage.class);
    }

}
