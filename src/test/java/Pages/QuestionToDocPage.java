package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class QuestionToDocPage extends BasePage {
    @FindBy(className = "search-filter")
    private WebElement searchField;
    @FindBy(className = "checkbox__control")
    private WebElement checkBoxPaidDocs;
    @FindBy(className = "doctor__fio")
    private List<WebElement> docFioList;
    @FindBy(className = "doctor__item")
    private List<WebElement> docItems;
    @FindBy(css = "a[class='button button_warn']")
    private WebElement askButton;
    @FindBy(className = "doctor__fio")
    public List<WebElement> doctorFio;
    @FindBy(css = "a[class='button button_warn']")
    private List<WebElement> askDocButtons;
    private By doctorsDiv = By.className("doctors");

    public QuestionToDocPage(WebDriver driver) {
        super(driver);
    }

    boolean isSearchFieldVisible() {
        waitForVisibilityOf(searchField, returnRandomSeconds());
        return searchField.isDisplayed();
    }

    public void clickOnCheckBox() {
        checkBoxPaidDocs.click();
    }

    public boolean isPaidDocsListOpened() {
        waitForVisibilityOfElementLocated(doctorsDiv);
        return docFioList.get(0).isDisplayed();
    }

    public WebElement returnDocFioElement(String docFIO) {
        WebElement doc = null;
        for (WebElement doctor : docFioList) {
            if (doctor.getText().contains(docFIO)) {
                doc = doctor;
                break;
            }
        }
        return doc;
    }

    public WebElement returnDoc() {
        return doctorFio.get(0);
    }

    public DocPage returnDocPage(String docFIO) {
        WebElement doc = returnDocFioElement(docFIO);
        doc.click();
        return PageFactory.initElements(driver, DocPage.class);
    }

    public QuestionForPage returnQuestionForPage() {
        waitForVisibilityOf(askButton, 8);
        askButton.click();
        return PageFactory.initElements(driver, QuestionForPage.class);
    }

    public boolean isFirstDocPriceFree(String price) {
        waitForVisibilityOfElementLocated(doctorsDiv);
        WebElement firstButton = askDocButtons.get(0);
        return firstButton.getText().contains(price);
    }

    private WebElement returnDocItem(String docFIO) {
        WebElement doc = null;
        waitForVisibilityOf(docItems.get(0), returnRandomSeconds());
        for (WebElement docItem : docItems) {
            if (docItem.getText().contains(docFIO)) {
                doc = docItem;
                break;
            }
        }
        return doc;
    }

    private WebElement returnDocAskButton(String docFIO) {
        WebElement doc = returnDocItem(docFIO);
        WebElement askButton = null;
        if (doc != null) {
            askButton = doc.findElement(By.cssSelector("a[class='button button_warn']"));
        }
        return askButton;
    }

    public boolean isDocPriceFree(String docFIO, String price) {
        boolean isDocPriceFree = false;
        WebElement button = returnDocAskButton(docFIO);
        if (button != null) {
            isDocPriceFree = button.getText().contains(price);
        }
        return isDocPriceFree;
    }


}
