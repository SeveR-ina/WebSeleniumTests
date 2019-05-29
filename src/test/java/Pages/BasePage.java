package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

abstract public class BasePage {
    @FindBy(className = "chats-item__sub-title")
    private List<WebElement> chatList;
    @FindBy(css = ".badge")
    private List<WebElement> statuses;

    protected int chatNumber;
    public WebDriver driver;
    private WebElement selector;
    private final static Long DEFAULT_TIMEOUT_IN_SECONDS = 10L;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected void waitForVisibilityOf(WebElement webElement, long timeOutInSeconds) {
        new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
    }

    protected void waitForVisibilityOf(WebElement webElement) {
        waitForVisibilityOf(webElement, DEFAULT_TIMEOUT_IN_SECONDS);
    }

    void waitForVisibilityOfElementLocated(By locator) {
        new WebDriverWait(driver, (long) 10).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    void waitForInvisibilityOfElementLocated(By locator, long timeOutInSeconds) {
        new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    private void waitRandomTime() {
        driver.manage().timeouts().implicitlyWait(returnRandomSeconds(), TimeUnit.SECONDS);
    }

    public void sendKeys(WebElement field, String text) {
        field.click();
        field.clear();
        field.sendKeys(text);
    }

    protected void pressEnterKeyOn(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        waitForVisibilityOf(element);
        element.sendKeys(Keys.ENTER);
        waitRandomTime();
    }

    private boolean isAlertPresent() {
        boolean foundAlert;
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            foundAlert = true;
        } catch (TimeoutException eTO) {
            foundAlert = false;
        }
        return foundAlert;
    }

    public void acceptAlertIfExists() {
        if (isAlertPresent()) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

    protected WebElement returnSelector(String placeholder, List<WebElement> selectorList) {
        for (WebElement select : selectorList) {
            if (select.getText().contains(placeholder)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", select);
                selector = select;
            }
        }
        return selector;
    }

    private List<WebElement> setOptionList(String optionName) {
        return driver.findElements(By.xpath("//*[contains(text(),'" + optionName + "')]"));
    }

    private void clickOption(String optionName, WebElement select, List<WebElement> selectOptionsList) {
        List<WebElement> options = setOptionList(optionName);
        do {
            openSelector(select, selectOptionsList);
            if (options.size() > 0) {
                System.out.println("options.size() > 0 : " + (options.size() > 0));
                break;
            }
        } while (options.size() == 0);
        options.get(0).click();
    }

    public boolean optionSelected(String optionName, WebElement select) {
        return select.getText().contains(optionName);
    }

    private void openSelector(WebElement selector, List<WebElement> selectOptionsList) {
        waitRandomTime();
        if ((selectOptionsList.size() == 0)) {
            selector.click();
        }
    }

    protected void selectOption(String optionName, WebElement select, List<WebElement> selectOptionsList) {
        openSelector(select, selectOptionsList);
        System.out.println("choosing " + optionName);
        clickOption(optionName, select, selectOptionsList);
    }

    protected int returnRandomSeconds() {
        Random rand = new Random();
        return rand.nextInt((4) + 1) + 2;
    }

    protected Boolean chatWithStatusExists(String chatStatus) {
        boolean chatExists = false;
        waitForVisibilityOf(chatList.get(0), returnRandomSeconds());
        for (WebElement status : statuses) {
            if (status.getText().equalsIgnoreCase(chatStatus)) {
                chatNumber = getNumberOfChat(status);
                chatExists = true;
                break;
            }
        }
        return chatExists;
    }

    private int getNumberOfChat(WebElement status) {
        return statuses.indexOf(status);
    }

    protected void openChat(int number) {
        WebElement chat = chatList.get(number);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chat);
        chat.click();
    }

    protected void typeEmailPass(WebElement loginField, String login, WebElement passField, String pass) {
        waitForVisibilityOf(loginField, 10);
        sendKeys(loginField, login);
        sendKeys(passField, pass);
    }

    protected HomePage returnHomePage(WebElement element) {
        element.click();
        return PageFactory.initElements(driver, HomePage.class);
    }

    protected void signOut(WebElement element) {
        element.click();
    }

    public HomePage returnHomePage() {
        return PageFactory.initElements(driver, HomePage.class);
    }
}
