package Pages.CPPages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CPChatPage extends BasePage {
    @FindBy(xpath = "//span[contains(text(), 'Отменить консультацию')]")
    private WebElement buttonCancelChat;
    @FindBy(xpath = "//span[contains(text(), 'Да')]")
    private WebElement buttonYesCancel;
    @FindBy(xpath = "//body/descendant::textarea[@rows='1']")
    private WebElement cpCommentInput;
    @FindBy(xpath = "(//span[contains(text(), 'Состояние')])/../../span")
    private WebElement chatStatus;
    @FindBy(xpath = "(//div[@class='aor-field aor-field-comment']/div/div)")
    private WebElement commentText;

    public CPChatPage(WebDriver driver) {
        super(driver);
    }

    private void openCancelWindow() {
        buttonCancelChat.click();
        waitForVisibilityOf(buttonYesCancel, returnRandomSeconds());
    }

    private void commentByCP(String reasonText) {
        sendKeys(cpCommentInput, reasonText);
    }

    public CPChatsPage refundChat(boolean cpCommentIsNeeded, String reasonText) {
        openCancelWindow();
        if (cpCommentIsNeeded) {
            commentByCP(reasonText);
        }
        buttonYesCancel.click();
        return PageFactory.initElements(driver, CPChatsPage.class);
    }

    public String returnChatStatus() {
        waitForVisibilityOf(chatStatus, returnRandomSeconds());
        return chatStatus.getText();
    }

    public String returnCommentText() {
        return commentText.getText();
    }

}
