package Pages.ChatsPages;

import Pages.BasePage;
import Pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DocChatPage extends BasePage {

    @FindBy(className = "mg-chat-message__input")
    private WebElement inputMessage;
    @FindBy(className = "mg-chat-message__send")
    private WebElement sendIcon;
    @FindBy(className = "chat-messages__item")
    private List<WebElement> messageList;
    @FindBy(className = "chats-top-panel")
    private WebElement topPanel;

    public DocChatPage(WebDriver driver) {
        super(driver);
    }

    public void answer(String text) {
        waitForVisibilityOf(inputMessage, returnRandomSeconds());
        if (inputMessage.isDisplayed()) {
            Actions performAct = new Actions(driver);
            performAct.sendKeys(inputMessage, text).build().perform();
            Actions actions = new Actions(driver);
            actions.moveToElement(sendIcon).click().build().perform();
        }
    }

    public HomePage returnHomePageFromChats() {
        return returnHomePage(topPanel);
    }

    public boolean sentMessageContains(String text) {
        waitForVisibilityOf(messageList.get(1), 6);
        return messageList.get(1).getText().contains(text);
    }

}
