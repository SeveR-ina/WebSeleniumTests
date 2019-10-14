package Pages.CPPages;

import Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CPChatsPage extends BasePage {
    @FindBy(css = "div[data-source='showTestConsultations']")
    private WebElement showTestChatsCheckBox;
    @FindBy(xpath = "//*[contains(text(), 'Справочная портала MedGreat')]")
    private WebElement supportDocLabel;
    @FindBy(xpath = "//input[@name='doctor']")
    private WebElement filterDocField;
    @FindBy(xpath = "//input[@name='user']")
    private WebElement filterPatientField;
    @FindBy(className = "add-filter")
    private WebElement addFilterButton;
    @FindBy(xpath = "//span[@data-key='corporation']")
    private WebElement filterItemCorp;
    @FindBy(xpath = "//div[@data-source='corporation']/div/div/div/div/button")
    private WebElement corporationFilter;
    @FindBy(xpath = "//div[@role='presentation']")
    private WebElement filterMenu;
    @FindBy(className = "column-status")
    private List<WebElement> chatStatuses;
    @FindBy(tagName = "tr")
    private List<WebElement> chatLines;
    private By firstLineBy = By.tagName("tr");


    public CPChatsPage(WebDriver driver) {
        super(driver);
    }

    public boolean cpChatsAreVisible() {
        waitForVisibilityOf(addFilterButton, returnRandomSeconds());
        return addFilterButton.isDisplayed();
    }

    public void showTestChats() {
        waitForVisibilityOf(showTestChatsCheckBox, returnRandomSeconds());
        showTestChatsCheckBox.click();
        waitForVisibilityOf(supportDocLabel, returnRandomSeconds());
    }

    public WebElement returnChatLine(String docFIO, String status) {
        waitFirstLine();
        int i = 5;
        int j = 4;
        int number = 0;
        for (WebElement tr : chatLines) {
            number++;
            if (tr.findElement(By.xpath("(//td)[" + i + "]")).getText().equalsIgnoreCase(status)) {
                if (tr.findElement(By.xpath("(//td)[" + j + "]")).getText().contains(docFIO)) {
                    break;
                }
                j = j + 8;
            }
            i = i + 8;
        }
        return chatLines.get(number);
    }

    public WebElement returnDocElementWithText(String docFIO) {
        waitFirstLine();
        return driver.findElement(By.xpath("//td[@class='column-doctorFullName']/span[contains(text(), '" + docFIO + "')]"));
    }

    public WebElement returnPatientElementWithText(String patientFIO) {
        waitFirstLine();
        return driver.findElement(By.xpath("//td[@class='column-userFullName']/span[contains(text(), '" + patientFIO + "')]"));
    }

    public void openCPChatPage(WebElement chatLine) {
        WebElement showChatBtn = chatLine.findElement(By.className("column-undefined"));
        showChatBtn.click();
    }

    public CPChatPage returnCPChatPage() {
        return PageFactory.initElements(driver, CPChatPage.class);
    }

    public void searchChatByDoc(String docFIO) {
        sendKeys(filterDocField, docFIO);
    }

    public void searchChatByPatient(String patientFIO) {
        sendKeys(filterPatientField, patientFIO);
    }

    private void waitFirstLine() {
        waitForVisibilityOfElementLocated(firstLineBy);
        //waitForVisibilityOf(chatLines.get(1));
        //((JavascriptExecutor) driver).executeScript("arguments[0].click()", chatStatuses.get(1));
    }

    public void addFilter(String filterName) {
        addFilterButton.click();
        waitForVisibilityOf(filterItemCorp, returnRandomSeconds());
        WebElement filterItem = returnFilterItem(filterName);
        if (filterItem != null) {
            filterItem.click();
        }
    }

    private WebElement returnFilterItem(String filterItem) {
        if (filterItem.equalsIgnoreCase("Корпорация")) {
            return filterItemCorp;
        } else {
            return null;
        }
    }

    public void pickTheCorporation(String optionName) {
        waitForVisibilityOf(corporationFilter, returnRandomSeconds());
        corporationFilter.click();
        WebElement option = returnOption(optionName);
        if (option.isDisplayed()) {
            option.click();
        }
    }

    private WebElement returnOption(String option) {
        waitForVisibilityOf(filterMenu, returnRandomSeconds());
        return driver.findElement(By.xpath("//div[contains(.,'" + option + "')]"));
    }


    /*public String returnChatStatus() { //TODO: искать нужный чат по айди и проверять статус
        //  waitForVisibilityOf(chatStatus, returnRandomSeconds());
        // return chatStatus.getText();
        return null;
    }*/

}
