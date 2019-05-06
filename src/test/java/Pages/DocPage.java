package Pages;

import Pages.ProfilePages.ProfileDocPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DocPage extends BasePage {
    @FindBy(css = ".__add-review .__text")
    private WebElement mainReviewTextField;
    @FindBy(css = ".__add-review .__agree")
    private WebElement agreeCheckBox;
    @FindBy(xpath = "//div[@id='addreview']/div[@class='create-review__block']/button")
    private WebElement addReviewButton;
    @FindBy(css = ".__add-review .__alert-success")
    private WebElement successAlert;
    @FindBy(className = "doctor-specialties")
    private WebElement speciality;
    @FindBy(className = "rating-details__value")
    private List<WebElement> ratingDetails;
    @FindBy(css = ".map__point > div")
    private WebElement clinicAdress;
    @FindBy(xpath = "//button[contains(@class,'button') and contains(., 'полную информацию')]")
    private WebElement showFullInfoButton;
    @FindBy(css = "a[class='button button_warn']")
    private WebElement buttonAskDoc;
    @FindBy(xpath = "//div[contains(@class,'rating-details__title') and text()='Публикации научных работ']")
    private WebElement titlePublications;
    @FindBy(xpath = "//div[@class='map__point']/a")
    private WebElement clinicName;
    @FindBy(css = "a[href='/profile/personal']")
    private WebElement profileLink;
    @FindBy(xpath = "//a[contains(text(),'Показать всех консультирующих докторов')]")
    private WebElement showAllDocsButton;
    @FindBy(className = "similar-doctors__sub-title-desktop")
    private WebElement similarDoctorsDiv;

    public DocPage(WebDriver driver) {
        super(driver);
    }

    public void scrollToReviewForm() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mainReviewTextField);
    }

    public void typeReviewText(String text) {
        sendKeys(mainReviewTextField, text);
    }

    public boolean isSuccessAlertVisible() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", agreeCheckBox);
        agreeCheckBox.click();
        addReviewButton.click();
        waitForVisibilityOf(successAlert);
        return successAlert.isDisplayed();
    }

    public String getDiscountTariff(String actualTariff, String percent) {
        Float percentFl = Float.valueOf(percent);
        String cutTariff = actualTariff.substring(0, 3);
        int actualTariffInt = Integer.parseInt(cutTariff);
        int discountTariffInt = (int) (actualTariffInt * (percentFl / 100.0f));
        return String.valueOf(discountTariffInt);
    }

    public String getPaidTariff() {
        String buttonText = buttonAskDoc.getText(); //Онлайн консультация  за 399 руб.
        StringBuilder stringBuffer = new StringBuilder(buttonText);
        stringBuffer.delete(0, 23);
        String out = stringBuffer.toString(); //399 руб.
        return out.replace(" руб.", "");
    }

    public boolean specialityOk(String specialityText) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", speciality);
        waitForVisibilityOf(speciality, returnRandomSeconds());
        return speciality.getText().contains(specialityText);
    }

    public boolean docCardDetailOk(String docCardDetail, boolean showFullInfo) {
        scrollToDetails();
        if (showFullInfo) {
            showFullInfoButton.click();
            waitForVisibilityOf(titlePublications, returnRandomSeconds());
        }
        return ifDocDetailContainsText(docCardDetail);
    }

    public String getClinicName() {
        return clinicName.getText();
    }

    public boolean cityOk(String city) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", clinicAdress);
        waitForVisibilityOf(clinicAdress, returnRandomSeconds());
        return clinicAdress.getText().contains(city);
    }

    private boolean ifDocDetailContainsText(String docCardDetail) {
        for (WebElement docDetail : ratingDetails) {
            if (docDetail.getText().contains(docCardDetail)) {
                return true;
            }
        }
        return false;
    }

    private void scrollToDetails() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ratingDetails.get(0));
        waitForVisibilityOf(speciality, returnRandomSeconds());
    }

    public ProfileDocPage returnProfileDoc() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", profileLink);
        profileLink.click();
        return PageFactory.initElements(driver, ProfileDocPage.class);
    }

    public QuestionToDocPage returnQuestionToPage() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", similarDoctorsDiv);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showAllDocsButton);
        //waitForInvisibilityOfElementLocated(similarDoctorsDivBy, returnRandomSeconds());
        QuestionToDocPage questionToDocPage = PageFactory.initElements(driver, QuestionToDocPage.class);
        if (!questionToDocPage.isSearchFieldVisible()) {
            return null;
        }
        return questionToDocPage;
    }

    public boolean isDocPriceFree(String price) {
        waitForVisibilityOf(buttonAskDoc, returnRandomSeconds());
        return buttonAskDoc.getText().contains(price);
    }

    public QuestionForPage returnQuestionForPage() {
        buttonAskDoc.click();
        return PageFactory.initElements(driver, QuestionForPage.class);
    }

}
