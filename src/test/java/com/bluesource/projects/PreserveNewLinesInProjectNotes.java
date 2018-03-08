package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.ProjectNoteForm;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class PreserveNewLinesInProjectNotes extends WebBaseTest {
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
		testStart("Preserve New Lines in Project Notes");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test
	public void preserveNewLinesInProjectNotes(){
		//Test Variables
		String strAccount = "Account1";
		String strProject = "Project1";
		String strNote = "test\ntest test";
		int numOfNotes = 0;

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		ProjectNoteForm projectNoteForm = new ProjectNoteForm(getDriver());

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts page");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(),"Verifying Accounts page is loaded");

		TestReporter.assertTrue(accounts.verifyAccountLink(strAccount),"Verifying [" + strAccount + "] link");

		TestReporter.logStep("Clicking " + strAccount + " account link");
		accounts.clickAccountLink(strAccount);

		TestReporter.assertTrue(accounts.verifyAccountPageIsLoaded(strAccount),"Verifying [" + strAccount + "] page is loaded");

		TestReporter.assertTrue(accounts.verifyProjectLinkValid(strProject),"Verifying [" + strProject + "] link");

		TestReporter.logStep("Clicking " + strProject + " project link");
		accounts.clickProjectLink(strProject);

		TestReporter.assertTrue(accounts.verifyProjectPageIsLoaded(strAccount,strProject),"Verifying [" + strProject + "] page is loaded");

		TestReporter.logStep("Checking if the project already has notes");
		if (accounts.verifyNoteCreated()){
			numOfNotes = accounts.getNumberOfNotes();
			TestReporter.logStep("The project has " + numOfNotes + " notes");
		} else {
			TestReporter.logStep("The project has no notes");
		}

		TestReporter.logStep("Clicking the Note button");
		accounts.clickCreateNote();

		TestReporter.assertTrue(projectNoteForm.verifyFormIsLoaded(),"Verifying Create Note Form loaded");

		TestReporter.logStep("Clicking the Add Note Button");
		projectNoteForm.clickAddNote();

		TestReporter.logStep("Setting note text");
		projectNoteForm.setNoteText(strNote);

		TestReporter.logStep("Clicking the create note button");
		projectNoteForm.clickCreateNote();

		TestReporter.assertTrue(accounts.verifyNoteCreated(),"Verifying note creation badge exists");

		if (numOfNotes > 0)
			TestReporter.assertTrue(numOfNotes < accounts.getNumberOfNotes(),"Verifying note creation badge incremented");

		TestReporter.logStep("Clicking the Note button");
		accounts.clickCreateNote();

		TestReporter.assertTrue(projectNoteForm.verifyFormIsLoaded(),"Verifying Create Note Form loaded");

		TestReporter.assertEquals("test\ntest test",projectNoteForm.getNoteText(),"Verifying that the stored note is equal to the original");
	}
}
