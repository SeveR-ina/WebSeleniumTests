package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DocRatingsPage extends BasePage {

    @FindBy(className = "doctor__rating")
    private List<WebElement> listOfRatings;
    @FindBy(className = "notifications__item--success")
    private WebElement notificationSuccess;
    @FindBy(xpath = "//button[@title='Данный доктор не проводит онлайн консультации. Вы можете оставить заявку на подключение его к сервису MedGreat.']")
    private List<WebElement> docRequestButtons;
    @FindBy(className = "doctors")
    private WebElement doctorsDiv;
    @FindBy(className = "doctor__fio")
    public List<WebElement> doctorFio;
    @FindBy(className = "Select-control")
    private List<WebElement> selectorList;
    @FindBy(className = "Select-menu-outer")
    private List<WebElement> selectOptionsList;
    private By optionMenuBy = By.className("Select-menu-outer");
    private By docDivBy = By.className("doctors");

    public DocRatingsPage(WebDriver driver) {
        super(driver);
    }

    public boolean ifRatingsSorted() {
        String ratingOne, ratingTwo;
        Double intRatingOne, intRatingTwo;
        ratingOne = listOfRatings.get(0).getText();
        ratingTwo = listOfRatings.get(1).getText();
        System.out.println("Rating 1 = " + ratingOne + "; Rating 2 = " + ratingTwo);
        intRatingOne = Double.parseDouble(ratingOne);
        intRatingTwo = Double.parseDouble(ratingTwo.substring(0, ratingOne.length() - 1));
        return (intRatingTwo <= intRatingOne);
    }

    public void makeADocRequest() {
        WebElement requestButtonOne = docRequestButtons.get(0);
        requestButtonOne.click();
    }

    public boolean ifRequestIsSent() {
        waitForVisibilityOf(notificationSuccess);
        return notificationSuccess.isDisplayed();
    }

    public boolean docsAreVisible() {
        waitForVisibilityOf(doctorsDiv, returnRandomSeconds());
        return doctorsDiv.isDisplayed();
    }

    public DocPage returnDocPage(String fio) {
        waitForInvisibilityOfElementLocated(optionMenuBy, returnRandomSeconds());
        for (WebElement doctor : doctorFio) {
            if (doctor.getText().equalsIgnoreCase(fio)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", doctor);
                doctor.click();
                waitForInvisibilityOfElementLocated(docDivBy, returnRandomSeconds());
                return PageFactory.initElements(driver, DocPage.class);
            }
        }
        return null;
    }

    public WebElement returnSelectlabel(int index) {
        return driver.findElements(By.className("Select-value-label")).get(index);
    }

    public void selectOption(String optionName, WebElement select) {
        selectOption(optionName, select, selectOptionsList);
    }

    public WebElement returnSelect(String placeholder) {
        return returnSelector(placeholder, selectorList);
    }

}
