package Pages.SignInPages;

import Pages.BasePage;
import Pages.PassRecoveryPage;
import Pages.QuestionForPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage extends BasePage {
    @FindBy(className = "__input-login")
    private WebElement loginField;
    @FindBy(name = "password")
    private WebElement passField;
    @FindBy(className = "__alert--danger")
    private WebElement errorAlert;
    @FindBy(className = "oauth-vk")
    private WebElement vkIcon;
    @FindBy(className = "oauth-ok")
    private WebElement okIcon;
    @FindBy(className = "oauth-fb")
    private WebElement fbIcon;
    @FindBy(css = "a[href='/password/forgot']")
    private WebElement passRecoveryPageLink;

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public QuestionForPage returnQuestionPage() {
        return PageFactory.initElements(driver, QuestionForPage.class);
    }

    public void typeToLoginAndPass(String login, String pass) {
        typeEmailPass(loginField, login, passField, pass);
    }

    public void submit() {
        pressEnterKeyOn(passField);
    }

    public String getErrorText() {
        return errorAlert.getText();
    }

    public SignInVkPage returnVkSignInPage() {
        vkIcon.click();
        return PageFactory.initElements(driver, SignInVkPage.class);
    }

    public SignInOkPage returnOkSignInPage() {
        okIcon.click();
        return PageFactory.initElements(driver, SignInOkPage.class);
    }

    public SignInFbPage returnFbSignInPage() {
        fbIcon.click();
        return PageFactory.initElements(driver, SignInFbPage.class);
    }

    public PassRecoveryPage returnPassRecoveryPage() {
        passRecoveryPageLink.click();
        return PageFactory.initElements(driver, PassRecoveryPage.class);
    }

    public boolean signInPageIsVisible() {
        waitForVisibilityOf(loginField, returnRandomSeconds());
        return loginField.isDisplayed();
    }
}
