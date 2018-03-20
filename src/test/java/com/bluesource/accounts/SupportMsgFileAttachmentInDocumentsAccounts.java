package com.bluesource.accounts;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.AddDocumentForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class SupportMsgFileAttachmentInDocumentsAccounts extends WebBaseTest {
    @BeforeMethod
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
            "operatingSystem", "environment" })
    public void setup(@Optional String runLocation, String browserUnderTest,
                      String browserVersion, String operatingSystem, String environment) {
        setApplicationUnderTest("BLUESOURCE");
        setBrowserUnderTest(browserUnderTest);
        setBrowserVersion(browserVersion);
        setOperatingSystem(operatingSystem);
        setRunLocation(runLocation);
        setEnvironment(environment);
        setThreadDriver(true);
        testStart("");
    }

    @AfterMethod
    public void close(ITestContext testResults){
        endTest("TestAlert", testResults);
    }

    @Test
    public void supportMsgFileAttachmentInDocumentsAccounts(){
        //Test variables
        String strAccount = "Account1";
        String strProject = "Project1";
        String strMsgFilePath = "C:\\Users\\david.grayson\\Documents\\test msg.msg";
        String fileName = strMsgFilePath.split("\\\\")[strMsgFilePath.split("\\\\").length-1];
        int budget = 10000;
        double hours = 10000.00;
        String documentType = "SOW";
        String documentName;

        //Page Models
        LoginPage loginPage = new LoginPage(getDriver());
        Header header = new Header(getDriver());
        Accounts accounts = new Accounts(getDriver());
        AddDocumentForm addDocumentForm = new AddDocumentForm(getDriver());

        TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource login page is loaded");

        TestReporter.logStep("Logging in as Admin");
        loginPage.AdminLogin();

        TestReporter.logStep("Navigating to Accounts");
        header.navigateAccounts();

        TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(), "Verifying Accounts page is loaded");
        TestReporter.assertTrue(accounts.verifyAccountLink(strAccount), "Verifying account ["+strAccount+"] link");

        TestReporter.logStep("Clicking [" + strAccount + "] link");
        accounts.clickAccountLink(strAccount);

        TestReporter.assertTrue(accounts.verifyAccountPageIsLoaded(strAccount),"Verifying account ["+strAccount+"] page is loaded");
        TestReporter.assertTrue(accounts.verifyProjectLinkValid(strProject),"Verifying Project ["+strProject+"] link");

        TestReporter.logStep("Clicking [" + strProject + "] link");
        accounts.clickProjectLink(strProject);

        TestReporter.assertTrue(accounts.verifyProjectPageIsLoaded(strAccount,strProject),"Verifying Project ["+strProject+"] page is loaded");

        TestReporter.logStep("Clicking Add Document button");
        accounts.clickAddDocument();

        TestReporter.assertTrue(addDocumentForm.verifyFormLoaded(),"Verifying Add Document form loaded");

        TestReporter.logStep("Setting file path to ["+strMsgFilePath+"]");
        addDocumentForm.setFile(strMsgFilePath);

        TestReporter.logStep("Selecting ["+documentType+"] as Document type");
		addDocumentForm.selectDocumentType(documentType);

		TestReporter.logStep("Getting document name");
		documentName = addDocumentForm.getDocumentName();

		TestReporter.logStep("Setting Total Budget field to ["+budget+"]");
        addDocumentForm.setTotalBudget(budget);

        TestReporter.logStep("Setting Total Hours field to ["+hours+"]");
        addDocumentForm.setTotalHours(hours);

        TestReporter.logStep("Clicking Signed checkbox");
        addDocumentForm.clickSignedCheckbox();

        TestReporter.logStep("Clicking Create Document button");
        addDocumentForm.clickCreateDocument();

        TestReporter.assertTrue(accounts.verifyDocumentExists(documentName),"Verifying Document was created");
    }
}
