package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.EditProjectForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class Add_QSF_Number_to_all_Sub_Projects extends WebBaseTest {
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
		testStart("Add QSF to all Sub Projects");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test
	public void add_QSF_Number_to_all_Sub_Projects(){
		TestReporter.logStep(getTestName()+  " START");
		//Test variables
		String strAccount = "AccountOnlySubs1";
		String strProject = "Project1";
		String strSOW;

		//Page models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		EditProjectForm editProjectForm = new EditProjectForm(getDriver());

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(),"Verifying Accounts page is loaded");

		TestReporter.assertTrue(accounts.verifyAccountLink(strAccount),"Verifying [" + strAccount + "] link");

		TestReporter.logStep("Clicking " + strAccount + " link");
		accounts.clickAccountLink(strAccount);

		TestReporter.assertTrue(accounts.verifyAccountPageIsLoaded(strAccount),"Verifying [" + strAccount + "] page is loaded");

		TestReporter.assertTrue(accounts.verifyProjectLinkValid(strProject),"Verifying [" + strProject + "] link");

		TestReporter.logStep("Clicking " + strProject + " link");
		accounts.clickProjectLink(strProject);

		TestReporter.assertTrue(accounts.verifyProjectPageIsLoaded(strAccount,strProject),"Verifying [" + strProject + "] page is loaded");

		TestReporter.logStep("Opening edit project form");
		accounts.editProject();

		TestReporter.assertTrue(editProjectForm.verifyFormLoaded(),"Verifying Edit Project Form loaded");

		TestReporter.logStep("Editing Projects SOW Number");
		editProjectForm.testEditSOWNumber();

		TestReporter.logStep("Setting test comments");
		editProjectForm.testSetComments();

		TestReporter.logStep("Clicking edit project form submit button");
		editProjectForm.clickButtonSubmit();

		TestReporter.logStep("Getting edited project SOW number");
		strSOW = accounts.getProjectSOW();

		for (String sow:accounts.getAllSubProjectSOWs()){
			TestReporter.assertEquals(strSOW,sow,"Verifying sub project SOW's");
		}
	}
}
