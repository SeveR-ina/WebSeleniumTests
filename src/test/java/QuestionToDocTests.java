import org.testng.annotations.Parameters;

import java.io.IOException;

public class QuestionToDocTests extends BeforeTests {


    @Parameters({"browser"})
    QuestionToDocTests(String browser) throws IOException {
        super(browser);
    }



}
