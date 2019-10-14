package Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ClinicPage extends BasePage {
    @FindBy(xpath = "//div[contains(@class,'tabs__item') and contains(.,'Врачи')]")
    private WebElement docTab;
    @FindBy(className = "search-filter")
    private WebElement docSearchFilter;
    @FindBy(className = "doctor__item")
    private List<WebElement> docsList;

    public ClinicPage(WebDriver driver) {
        super(driver);
    }

    public void openDocTab() {
        waitForVisibilityOf(docTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", docTab);
        docTab.click();
        waitForVisibilityOf(docSearchFilter, returnRandomSeconds());
    }

    public WebElement returnDoc(String docFIO) throws InterruptedException {
        sendKeys(docSearchFilter, docFIO);
        Thread.sleep(1500);
        WebElement doc = null;
        for (WebElement docItem : docsList) {
            if (docItem.getText().contains(docFIO)) {
                doc = docItem;
                break;
            }
        }
        return doc;
    }


}
