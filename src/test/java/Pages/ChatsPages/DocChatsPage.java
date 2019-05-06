package Pages.ChatsPages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DocChatsPage extends BasePage {


    public DocChatsPage(WebDriver driver) {
        super(driver);
    }

    public DocChatPage returnDocChatPage(String chatStatus) {
        if (chatWithStatusExists(chatStatus)) {
            openChat(chatNumber);
            return PageFactory.initElements(driver, DocChatPage.class);
        }
        return null;
    }

}
