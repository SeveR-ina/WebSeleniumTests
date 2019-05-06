package Pages.ChatsPages;

import Pages.BasePage;
import Pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PatientChatPage extends BasePage {
    @FindBy(className = "chat-messages__item")
    private WebElement messageItem;
    @FindBy(className = "__message_status-Sent")
    private WebElement sentStatusIcon;
    @FindBy(className = "dropdown")
    private WebElement dropDownLabel;
    //@FindBy(tagName = "textarea")
    @FindBy(className = "modal__rating-comment")
    private WebElement textAreaField;
    @FindBy(xpath = "//div[@class='chat-modal__footer']/button[2]")
    private WebElement submitButton;
    @FindBy(className = "chat-modal__content")
    private WebElement window;
    @FindBy(className = "__message_status-Read")
    private WebElement statusRead;
    @FindBy(xpath = "//div[contains(@class,'dropdown-list__item ') and contains(., 'Завершить консультацию')]")
    private WebElement closeChatItem;
    @FindBy(className = "mg-chat-message__input")
    private List<WebElement> inputFields;
    private By statusBadgeBy = By.className("badge");
    @FindBy(className = "modal__rating-score")
    private List<WebElement> ratingHearts;
    @FindBy(className = "chats-top-panel")
    private WebElement topPanel;

    public PatientChatPage(WebDriver driver) {
        super(driver);
    }

    public boolean messageIsVisible() {
        waitForVisibilityOf(messageItem);
        return messageItem.isDisplayed();
    }

    public boolean statusIsSent() {
        waitForVisibilityOf(sentStatusIcon, returnRandomSeconds());
        return sentStatusIcon.isDisplayed();
    }

    public void cancelChat(String cancellationText) {
        openCloseChatWindow();
        if (!cancellationText.equals("")) {
            sendKeys(textAreaField, cancellationText); //TODO: is not clickable
        }
        closeChat();
    }

    public void completeChat() {
        openCloseChatWindow();
        clickOnNthHeart(0);
        closeChat();
    }

    public void reportOnChat(String reportText) {
        openCloseChatWindow();
        clickOnNthHeart(4);
        if (!reportText.equals("")) {
            sendKeys(textAreaField, reportText);
        }
        closeChat();
    }

    private void clickOnNthHeart(int index) {
        ratingHearts.get(index).click();
    }

    private void closeChat() {
        waitForVisibilityOf(submitButton, returnRandomSeconds());
        submitButton.click();
        waitForVisibilityOf(messageItem, returnRandomSeconds());
    }

    private void openCloseChatWindow() {
        dropDownLabel.click();
        waitForVisibilityOf(closeChatItem, 5);
        closeChatItem.click(); // close item
        waitForVisibilityOf(window, returnRandomSeconds());
    }

    public boolean chatFieldDisabled() {
        return inputFields.size() == 0;
    }

    private String[] getStringArray(String chatURL) {
        String currentURL = driver.getCurrentUrl();
        return currentURL.split(chatURL);
    }

    private String getChatId(String chatURL) {
        String[] strArr = getStringArray(chatURL);
        StringBuilder strBuilder = new StringBuilder();
        for (String aStrArr : strArr) {
            strBuilder.append(aStrArr);
        }
        return strBuilder.toString();
    }

    private WebElement returnOpenedChat(String chatURL) {
        return driver.findElement(By.cssSelector("a[href='/chat/" + getChatId(chatURL) + "']"));
    }

    public String getChatStatus(String chatURL) {//.findElement(chatItemBodyBy).findElement(chatItemSubTitleBy)
        return returnOpenedChat(chatURL).findElement(statusBadgeBy).getText();
    }

    public boolean patientMessageIsRead() {
        waitForVisibilityOf(statusRead, returnRandomSeconds());
        return statusRead.isDisplayed();
    }

    public HomePage returnHomePageFromChats() {
        return returnHomePage(topPanel);
    }

}
