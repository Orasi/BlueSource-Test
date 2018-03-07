package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.EditRoleRateForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class CurrentRateForRoleShowsOnProjectShowPage extends WebBaseTest{
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
		testStart("Current_Rate_for_role_shows_on_project_show_page");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test
	public void currentRateForRoleShowsOnProjectShowPage(){
		TestReporter.logScenario("START");

		//Test Variables
		String roleRate;
		String strAccount = "Account1";
		String strProject = "Project2";
		String strRole = "Role3";

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		EditRoleRateForm rateForm = new EditRoleRateForm(getDriver());

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Verifying login page os loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts page");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(),"Verifying Accounts page is loaded");

		TestReporter.assertTrue(accounts.verifyAccountLink(strAccount),"Verifying [" + strAccount + "] link");

		TestReporter.logStep("Clicking [" + strAccount + "] link");
		accounts.clickAccountLink(strAccount);

		TestReporter.assertTrue(accounts.verifyAccountPageIsLoaded(strAccount),"Verifying [" + strAccount + "] page is loaded");

		TestReporter.assertTrue(accounts.verifyProjectLinkValid(strProject),"Verifying [" + strProject + "] link");

		TestReporter.logStep("Clicking [" + strProject + "] link");
		accounts.clickProjectLink(strProject);

		TestReporter.assertTrue(accounts.verifyProjectPageIsLoaded(strAccount,strProject),"Verifying [" + strProject + "] page is loaded");

		TestReporter.logStep("Getting Role Rate from Project page");
		roleRate = accounts.getRoleRateFromProjectPage(strRole);

		TestReporter.assertNotEquals(roleRate,"","Checking that the Role Rate is valid");

		TestReporter.assertTrue(accounts.verifyRoleLink(strRole),"Verifying");

		TestReporter.logStep("Clicking [" + strRole + "] link");
		accounts.clickRoleLink(strRole);

		TestReporter.assertTrue(accounts.verifyRolePageIsLoaded(strAccount,strProject,strRole),"Verifying [" + strRole + "] page is loaded");

		TestReporter.assertTrue(accounts.verifyRoleRate(roleRate), "Verifying that the role rate from project page is correct.");

		TestReporter.logStep("Clicking Edit Role Rate element");
		accounts.clickEditRoleRate(roleRate);

		TestReporter.assertTrue(rateForm.verifyEditRateFormIsLoaded(),"Verifying Edit Rate Form is loaded");

		TestReporter.logStep("Entering test rate");
		rateForm.testRate();

		TestReporter.logStep("Entering test comments");
		rateForm.testComments();

		TestReporter.logStep("Submitting Edit Rate Form");
		rateForm.clickSubmit();

		TestReporter.assertFalse(accounts.verifyRoleRate(roleRate), "Check that the role rate updated");

		TestReporter.logStep("END");
	}
}
