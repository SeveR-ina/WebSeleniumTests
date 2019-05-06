package Pages.ChatsPages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PatientChatsPage extends BasePage {

    public PatientChatsPage(WebDriver driver) {
        super(driver);
    }

    public PatientChatPage returnPatientChatPage(String status) {
        if (chatWithStatusExists(status)) {
            openChat(chatNumber);
            return PageFactory.initElements(driver, PatientChatPage.class);
        }
        return null;
    }
}
