import Pages.HomePage;
import Pages.QuestionToDocPage;
import Pages.SignInPages.SignInPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

abstract class BeforeTests {
    WebDriver driver;
    SignInPage signInPage;
    HomePage homePage;
    Properties testProperties = new Properties();

    BeforeTests(String browser) throws IOException {
        loadPropertiesFromFile();
        setCapabilities(browser);
    }

    void openBrowsers(String browser) {
        openBrowser(browser);
        if (browser.equals("IE")) {
            driver.get("javascript:document.getElementById('overridelink').click();"); //for certificate problem
        }
    }

    HomePage returnHomePage() {
        return PageFactory.initElements(driver, HomePage.class);
    }

    private void loadPropertiesFromFile() throws IOException {
        testProperties.load(new FileInputStream("./src/test/resources/test.properties"));
    }

    private void setCapabilities(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browser", browser);
    }

    private void openBrowser(String browser) {
        openAllBrowsers(browser);
        driver.get(testProperties.getProperty("siteURL"));
        maximizeWindow();
    }

    void openNewWindow(String browser, String url) {
        openAllBrowsers(browser);
        driver.get(url);
        maximizeWindow();
    }

    private void openAllBrowsers(String browser) {
        switch (browser) {
            case "Chrome":
                openChrome();
                break;
            case "FireFox":
                openFireFox();
                break;
            case "IE":
                openIE();
                break;
            default:
                break;
        }
    }

    private void maximizeWindow() {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    private void openChrome() {
        try {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(testProperties.getProperty("chromeDir")))
                    .usingAnyFreePort()
                    .build();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("incognito");
            options.merge(capabilities);
            driver = new ChromeDriver(service, options);
            System.out.println("Chrome Launched Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openIE() {
        try {
            System.setProperty("webdriver.ie.driver", testProperties.getProperty("ieDir"));
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                    true);
            capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
            capabilities.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
            InternetExplorerOptions opt = new InternetExplorerOptions();
            opt.merge(capabilities);
            driver = new InternetExplorerDriver(opt);
            System.out.println("IE Launched Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFireFox() {
        try {
            System.setProperty("webdriver.gecko.driver", testProperties.getProperty("geckoDir"));
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.privatebrowsing.autostart", true);
            profile.setPreference("dom.webnotifications.enabled", true);
            FirefoxOptions options = new FirefoxOptions();
            options = options.setProfile(profile);
            options.setCapability("marionette", true);
            driver = new FirefoxDriver(options);
            System.out.println("Firefox Launched Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HomePage signInAndReturnHomePage(SignInPage signInPage, String email, String pass) {
        signIn(signInPage, email, pass);
        return signInPage.returnHomePage();
    }

    void signIn(SignInPage signInPage, String login, String pass) {
        signInPage.typeToLoginAndPass(login, pass);
        signInPage.submit();
    }

    void checkIfUserSignedIn(HomePage homePage) {
        Assert.assertTrue(homePage.ifUserSignedIn()); //chats link is visible
    }

    void quitBrowser() {
        driver.quit();
    }

    QuestionToDocPage findDoc(HomePage homePage, String searchQuery) {
        homePage.typeToSearchField(searchQuery);
        return homePage.returnDocsPageWithFoundDoc();
    }
}
