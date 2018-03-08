package com.bluesource.accounts;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.EditProjectForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class ProjectPublishCommentAsNote extends WebBaseTest {
	@BeforeMethod
	@Parameters({"runLocation", "browserUnderTest", "browserVersion",
			"operatingSystem", "environment"})
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
	public void close(ITestContext testResults) {
		endTest("TestAlert", testResults);
	}

	@Test
	public void projectPublishCommentAsNote() {
		//Test Variables
		String strAccount = "Account1";
		String strProject = "Project3";

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		EditProjectForm editProjectForm = new EditProjectForm(getDriver());

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.logStep("Clicking [" + strAccount + "] account link");
		accounts.clickAccountLink(strAccount);

		TestReporter.logStep("Clicking [" + strProject + "] project link");
		accounts.clickProjectLink(strProject);

		TestReporter.logStep("Clicking 'Edit Project'");
		accounts.clickEditProject();

		TestReporter.logStep("Changing Notification Threshold");
		editProjectForm.testEditNotificationThreshold();

		TestReporter.logStep("Entering test comments");
		editProjectForm.testProjectComments();

		TestReporter.logStep("Clicking 'Update Project'");
		editProjectForm.clickUpdateProject();

		TestReporter.logStep("Clicking 'Edit Project'");
		accounts.clickEditProject();

		TestReporter.logStep("Clicking 'Change Log' tab");
		editProjectForm.clickChangeLogTab();

		TestReporter.assertEquals("test test test",editProjectForm.getChangeLogNoteText(),"Verifying Change Log is accurate");
	}
}