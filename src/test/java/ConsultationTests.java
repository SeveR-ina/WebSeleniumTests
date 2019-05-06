import Pages.*;
import Pages.CPPages.CPChatPage;
import Pages.CPPages.CPChatsPage;
import Pages.CPPages.CPSignInPage;
import Pages.ChatsPages.DocChatPage;
import Pages.ChatsPages.DocChatsPage;
import Pages.ChatsPages.PatientChatPage;
import Pages.ChatsPages.PatientChatsPage;
import Pages.SignInPages.SignInPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ConsultationTests extends BeforeTests {
    private HomePage homePage;
    private QuestionForPage questionPage;
    private DocChatPage docChatPage;
    private PatientChatPage patientChatPage;
    private String SUPPORT_FIO, PATIENT_LOGIN, PATIENT_PASS,
            SUPPORT_PRICE, SUPPORT_LOGIN, SUPPORT_PASS;
    private String CHAT_URL, NEW_CHAT_STATUS, IN_PROGRESS_CHAT_STATUS, CANCELED_CHAT_STATUS,
            COMPLETED_CHAT_STATUS, REPORTED_CHAT_STATUS;
    private String CP_SIGNIN_PAGE_URL, ADMIN_LOGIN, ADMIN_PASS, CP_REPORTED_CHAT_STATUS;
    private boolean justOpenChat = false;
    private String closeChatText = "";

    @Parameters({"browser"})
    ConsultationTests(String browser) throws IOException {
        super(browser);
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void openAskDocPage(String browser) {
        openBrowsers(browser);
        getProperties();
        homePage = returnHomePage();
        assertNotNull(homePage);
    }

    @AfterMethod
    public void closeApp() {
        this.quitBrowser();
    }

    @Test
    public void askFreeQuestion() {
        openQuestionPage(SUPPORT_FIO);
        Assert.assertEquals(questionPage.getChatPrice(), SUPPORT_PRICE);
        questionPage.typeToField("healthComplaints", PATIENT_PASS);
        questionPage.acceptAlertIfExists();
        SignInPage signInPage = (SignInPage) questionPage.pressAskButton(false);
        assertNotNull(signInPage);
        QuestionForPage questionPageTwo = signInAndOpenQuestionPage(signInPage);
        assertNotNull(questionPageTwo);
        questionPage.acceptAlertIfExists();
        patientChatPage = (PatientChatPage) questionPageTwo.pressAskButton(true);
        assertNotNull(patientChatPage);
        Assert.assertTrue(patientChatPage.messageIsVisible());
    }

    @Test
    public void answerTheQuestion() {
        preAnswer();
        signInOnHomePage(SUPPORT_LOGIN, SUPPORT_PASS);
        DocChatsPage docChatsPage = homePage.returnDocChats();
        assertNotNull(docChatsPage);
        docChatPage = docChatsPage.returnDocChatPage(NEW_CHAT_STATUS);
        assertNotNull(docChatPage);
        docChatPage.answer(SUPPORT_PRICE);
        Assert.assertTrue(docChatPage.sentMessageContains(SUPPORT_PRICE));
    }

    @Test
    public void cancelChat() {
        preCancel();
        patientChatPage.cancelChat(closeChatText);
        patientChatPage.acceptAlertIfExists();
        Assert.assertTrue(patientChatPage.chatFieldDisabled());
        Assert.assertEquals(patientChatPage.getChatStatus(CHAT_URL), CANCELED_CHAT_STATUS);
        patientChatPage.acceptAlertIfExists();
    }

    @Test
    public void completeChat() {
        openInProgressChat();
        patientChatPage.completeChat();
        patientChatPage.acceptAlertIfExists();
        Assert.assertTrue(patientChatPage.chatFieldDisabled());
        Assert.assertEquals(patientChatPage.getChatStatus(CHAT_URL), COMPLETED_CHAT_STATUS);
    }

    @Test
    public void reportOnChat() {
        openInProgressChat();
        patientChatPage.reportOnChat(closeChatText);
        patientChatPage.acceptAlertIfExists();
        Assert.assertTrue(patientChatPage.chatFieldDisabled());
        Assert.assertEquals(patientChatPage.getChatStatus(CHAT_URL), REPORTED_CHAT_STATUS);
    }

    @Test
    public void checkSentStatusByPatient() {
        askFreeQuestion();
        Assert.assertTrue(patientChatPage.statusIsSent());
    }

    @Test
    public void checkReadStatus() {
        openInProgressChat();
        assertTrue(patientChatPage.patientMessageIsRead());
    }

    @Test(enabled = false)
    public void checkRefundStatus() {
        preRefund();
        //SIGN IN ON CP
        CPSignInPage cpSignInPage = returnCPSignInPage();
        assertNotNull(cpSignInPage);
        signInOnCP(cpSignInPage, ADMIN_LOGIN, ADMIN_PASS);
        CPChatsPage cpChatsPage = cpSignInPage.returnCPChatsPage();
        assertNotNull(cpChatsPage);
        //OPEN FIRST ARBITRAGE CHAT
        cpChatsPage.showTestChats();
        //cpChatsPage.openChatWithDocAndStatus(SUPPORT_FIO, CP_REPORTED_CHAT_STATUS);
        CPChatPage cpChatPage = cpChatsPage.returnCPChatPage();
        assertNotNull(cpChatPage);
        Assert.assertEquals(cpChatPage.returnChatStatus(), CP_REPORTED_CHAT_STATUS);
        //DO A REFUND
        cpChatPage.refundChat(true, SUPPORT_PRICE);
        Assert.assertEquals(cpChatPage.returnChatStatus(), CP_REPORTED_CHAT_STATUS);
        Assert.assertTrue(cpChatPage.returnCommentText().contains(SUPPORT_PRICE));
    }

    private void preRefund() {
        if (justOpenChat) {
            signInOnHomePage(PATIENT_LOGIN, PATIENT_PASS);
            openPatientChat(CANCELED_CHAT_STATUS);
        } else {
            reportOnChat();
            closeChatText = PATIENT_PASS;
        }
    }

    private void openInProgressChat() {
        if (!justOpenChat) {
            answerTheQuestion();
            signOutDoc();
        }
        signInOnHomePage(PATIENT_LOGIN, PATIENT_PASS);
        openPatientChat(IN_PROGRESS_CHAT_STATUS);
        closeChatText = PATIENT_PASS;
    }

    private void preAnswer() {
        if (!justOpenChat) {
            askFreeQuestion();
            signOutPatient();
        }
    }

    private CPSignInPage returnCPSignInPage() {
        driver.get(CP_SIGNIN_PAGE_URL);
        return PageFactory.initElements(driver, CPSignInPage.class);
    }

    private void signOutDoc() {
        homePage = docChatPage.returnHomePageFromChats();
        homePage.signOut();
        homePage = homePage.returnHomePage();
    }

    private void signOutPatient() {
        homePage = patientChatPage.returnHomePageFromChats();
        homePage.signOut();
        homePage = homePage.returnHomePage();
    }

    private void signInOnCP(CPSignInPage cpSignInPage, String login, String pass) {
        cpSignInPage.signIn(login, pass);
    }

    private void preCancel() {
        if (justOpenChat) {
            signInOnHomePage(PATIENT_LOGIN, PATIENT_PASS);
            openPatientChat(NEW_CHAT_STATUS);
        } else { //DEFAULT - FALSE, FOR DEBUGGING - TRUE
            askFreeQuestion();
            closeChatText = PATIENT_PASS;
        }
    }

    private void openQuestionPage(String docFIO) {
        QuestionToDocPage questionToDocPage = findDoc(docFIO);
        assertNotNull(questionToDocPage);
        questionPage = questionToDocPage.returnQuestionForPage();
        assertNotNull(questionPage);
    }

    private QuestionForPage signInAndOpenQuestionPage(SignInPage signInPage) {
        signIn(signInPage, PATIENT_LOGIN, PATIENT_PASS);
        return signInPage.returnQuestionPage();
    }

    private void signInOnHomePage(String login, String pass) {
        signInPage = homePage.returnSignInPage();
        assertNotNull(signInPage);
        homePage = signInAndReturnHomePage(signInPage, login, pass);
        assertNotNull(homePage);
    }

    private void openPatientChat(String status) {
        PatientChatsPage patientChatsPage = homePage.returnPatientChats();
        assertNotNull(patientChatsPage);
        patientChatPage = patientChatsPage.returnPatientChatPage(status);
    }

    private QuestionToDocPage findDoc(String docFIO) {
        homePage.typeToSearchField(docFIO);
        return homePage.returnDocsPageWithFoundDoc();
    }

    private void getProperties() {
        PATIENT_LOGIN = testProperties.getProperty("correctPatientLogin");
        PATIENT_PASS = testProperties.getProperty("oneToFivePass");
        SUPPORT_FIO = testProperties.getProperty("supportFIO");
        SUPPORT_LOGIN = testProperties.getProperty("supportDocLogin");
        SUPPORT_PASS = testProperties.getProperty("supportPas");
        SUPPORT_PRICE = testProperties.getProperty("freePrice");
        //***
        CHAT_URL = testProperties.getProperty("chatURL");
        NEW_CHAT_STATUS = testProperties.getProperty("newChatStatus");
        IN_PROGRESS_CHAT_STATUS = testProperties.getProperty("inProgressChatStatus");
        CANCELED_CHAT_STATUS = testProperties.getProperty("cancelChatStatus");
        COMPLETED_CHAT_STATUS = testProperties.getProperty("completedChatStatus");
        REPORTED_CHAT_STATUS = testProperties.getProperty("reportedChatStatus");
        //***
        CP_SIGNIN_PAGE_URL = testProperties.getProperty("cpSignInPageURL");
        ADMIN_LOGIN = testProperties.getProperty("cpLogIn");
        ADMIN_PASS = testProperties.getProperty("cpPass");
        CP_REPORTED_CHAT_STATUS = testProperties.getProperty("reportedChatCP");
    }

}
