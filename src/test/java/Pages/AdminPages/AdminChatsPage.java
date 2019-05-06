package Pages.AdminPages;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminChatsPage extends BasePage {

    @FindBy (className = "logout")
    private WebElement logOutMenuItem;

    public AdminChatsPage(WebDriver driver) {
        super(driver);
    }

    public boolean adminChatsAreVisible(){
        waitForVisibilityOf(logOutMenuItem, returnRandomSeconds());
        return logOutMenuItem.isDisplayed();
    }

}
