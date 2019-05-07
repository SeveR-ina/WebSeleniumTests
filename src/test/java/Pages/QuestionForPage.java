package Pages;

import Pages.ChatsPages.PatientChatPage;
import Pages.SignInPages.SignInPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class QuestionForPage extends BasePage {
    @FindBy(className = "__text-illness")
    private WebElement healthComplaintsField;
    @FindBy(className = "__text-anamnesis")
    private WebElement anamnesisField;
    @FindBy(className = "__text-question")
    private WebElement questionField;
    @FindBy(className = "__button-send")
    private WebElement askButton;
    @FindBy(xpath = "(//div[@class='form-field'])[5]")
    private WebElement divAskBtn;
    @FindBy(xpath = "(//div[@class='question__price'])")
    private WebElement chatPrice;
    @FindBy(xpath = "(//label[@class='files__add __multiple'])")
    private WebElement addFilesButton;
    private Robot robot;
    private By askBtnBy = By.className("__button-send");

    public QuestionForPage(WebDriver driver) {
        super(driver);
    }

    public void typeToField(String field, String text) {
        waitForVisibilityOf(healthComplaintsField, returnRandomSeconds());
        if (field.equalsIgnoreCase("healthComplaints")) {
            sendKeys(healthComplaintsField, text);
        } else if (field.equalsIgnoreCase("anamnesis")) {
            sendKeys(anamnesisField, text);
        } else if (field.equalsIgnoreCase("question")) {
            sendKeys(questionField, text);
        } else if (field.equalsIgnoreCase("all")) {
            sendKeys(healthComplaintsField, text);
            sendKeys(anamnesisField, text);
            sendKeys(questionField, text);
        }
    }

    public String getChatPrice() {
        waitForVisibilityOf(chatPrice, returnRandomSeconds());
        return chatPrice.getText(); //Цена: 499₽ бесплатно
    }

    public boolean isActualPriceEquils(String priceForCheck) {
        return getChatPrice().contains(priceForCheck);
    }

    public boolean buttonIsDisabled() {
        waitForVisibilityOf(askButton, 10);
        return divAskBtn.getAttribute("innerHTML").contains("disabled");
    }

    public void clickOnAddFileBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addFilesButton);
        addFilesButton.click();
    }

    private Robot returnRobot() {
        robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        assert robot != null;
        return robot;
    }

    public void copyPasteString(String string) {
        StringSelection ss = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        robot = returnRobot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public Object pressAskButton(boolean userSignedIn) {
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", askButton);
        waitForVisibilityOf(askButton, returnRandomSeconds());
        askButton.click();
        if (userSignedIn) {
            waitForInvisibilityOfElementLocated(askBtnBy, returnRandomSeconds());
            return PageFactory.initElements(driver, PatientChatPage.class);
        }
        return PageFactory.initElements(driver, SignInPage.class);
    }

}
