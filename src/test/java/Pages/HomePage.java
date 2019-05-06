package Pages;

import Pages.AdminPages.AdminSignInPage;
import Pages.ChatsPages.DocChatsPage;
import Pages.ChatsPages.PatientChatsPage;
import Pages.ProfilePages.ProfileDocPage;
import Pages.ProfilePages.ProfilePatientPage;
import Pages.SignInPages.SignInPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(css = ".__link-signup")
    private WebElement registerLink;
    @FindBy(css = ".__link-login")
    private WebElement signInLink;
    @FindBy(css = "a[href='/chat']")
    private WebElement chatsLink;
    @FindBy(css = "a[href='/doctors?isConsulting=false']")
    private WebElement docsLink;
    @FindBy(css = "a[href='/hospitals']")
    private WebElement clinicsLink;
    @FindBy(css = "a[href='/new-cp']")
    private WebElement cpLink;
    @FindBy(xpath = "//input[@type='text']")
    private WebElement searchField;
    @FindBy(xpath = "//a[contains(text(), 'Выход')]")
    private WebElement signOutLink;
    @FindBy(css = "a[href='/profile/personal']")
    private WebElement profileLink;
    @FindBy(css = "a[href='/doctors']")
    private WebElement questionToDocBanner;
    @FindBy(className = "best-doctors__title")
    private WebElement bestDocsBlock;
    @FindAll({@FindBy(className = "doctor-card__name")})
    private List<WebElement> docCards;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void signOut() {
        signOut(signOutLink);
    }

    public SignUpPage returnSignUpPage() {
        registerLink.click();
        return PageFactory.initElements(driver, SignUpPage.class);
    }

    public SignInPage returnSignInPage() {
        waitForVisibilityOf(signInLink, returnRandomSeconds());
        signInLink.click();
        return PageFactory.initElements(driver, SignInPage.class);
    }

    public DocRatingsPage returnDocsRatingListPage() {
        docsLink.click();
        return PageFactory.initElements(driver, DocRatingsPage.class);
    }

    public ClinicsPage returnClinicsList() {
        clinicsLink.click();
        return PageFactory.initElements(driver, ClinicsPage.class);
    }

    public DocChatsPage returnDocChats() {
        waitForVisibilityOf(chatsLink, returnRandomSeconds());
        chatsLink.click();
        return PageFactory.initElements(driver, DocChatsPage.class);
    }

    public PatientChatsPage returnPatientChats() {
        waitForVisibilityOf(chatsLink, returnRandomSeconds());
        chatsLink.click();
        return PageFactory.initElements(driver, PatientChatsPage.class);
    }

    public QuestionToDocPage returnDocsPageWithFoundDoc() {
        pressEnterKeyOn(searchField);
        return PageFactory.initElements(driver, QuestionToDocPage.class);
    }

    public ProfilePatientPage returnProfilePatientPage() throws InterruptedException {
        Thread.sleep(4000);  //wait until profile data is uploaded
        profileLink.click();
        return PageFactory.initElements(driver, ProfilePatientPage.class);
    }

    public ProfileDocPage returnDocProfilePage() throws InterruptedException {
        Thread.sleep(4000); //wait until profile data is uploaded
        profileLink.click();
        return PageFactory.initElements(driver, ProfileDocPage.class);
    }

    public QuestionToDocPage returnQuestionToDocPage() {
        questionToDocBanner.click();
        return PageFactory.initElements(driver, QuestionToDocPage.class);
    }

    public boolean adminPanelLinkIsVisible() {
        waitForVisibilityOf(cpLink, returnRandomSeconds());
        return cpLink.isDisplayed();
    }

    public AdminSignInPage returnAdminSignInPage() {
        cpLink.click();
        return PageFactory.initElements(driver, AdminSignInPage.class);
    }

    public boolean ifUserSignedIn() {
        waitForVisibilityOf(chatsLink, 10);
        return chatsLink.isDisplayed();
    }

    public boolean ifUserSignedOut() {
        waitForVisibilityOf(signInLink, 10);
        return signInLink.isDisplayed();
    }

    public void typeToSearchField(String searchQuery) {
        waitForVisibilityOf(searchField, 3);
        sendKeys(searchField, searchQuery);
    }

    public boolean tatneftDocsAreVisible(String docFIO) {
        boolean docIsVisible = false;
        waitForVisibilityOf(bestDocsBlock, 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bestDocsBlock);
        for (WebElement docCard : docCards) {
            if (docCard.getText().equalsIgnoreCase(docFIO)) {
                docIsVisible = true;
                break;
            }
        }
        return docIsVisible;
    }
}
