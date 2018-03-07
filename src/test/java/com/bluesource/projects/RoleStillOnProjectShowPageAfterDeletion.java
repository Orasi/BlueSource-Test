package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.EditRoleForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class RoleStillOnProjectShowPageAfterDeletion extends WebBaseTest {
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
		testStart("Role still on project show page after deletion");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test
	public void roleStillOnProjectShowPageAfterDeletion(){
		//Test Variables
		String strAccount = "New Future Account";
		String strProject = "Project1";
		String strRole = "Performance Tester L1";
		int numOfRole; //the amount of a particular role that are in a project

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		EditRoleForm editRoleForm = new EditRoleForm(getDriver());

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(),"Verifying Accounts page is loaded");

		TestReporter.assertTrue(accounts.verifyAccountLink(strAccount), "Verifying [" + strAccount + "] link");

		TestReporter.logStep("Clicking [" + strAccount + "] account link");
		accounts.clickAccountLink(strAccount);

		TestReporter.assertTrue(accounts.verifyAccountPageIsLoaded(strAccount), "Verifying [" + strAccount + "]  page is loaded");

		TestReporter.assertTrue(accounts.verifyProjectLinkValid(strProject),"Verifying [" + strProject + "] link");

		TestReporter.logStep("Clicking [" + strProject + "] project link");
		accounts.clickProjectLink(strProject);

		TestReporter.assertTrue(accounts.verifyProjectPageIsLoaded(strAccount,strProject),"Verifying [" + strProject + "] page is loaded");

		TestReporter.logStep("Getting the number of [" + strRole + "] roles in the project");
		numOfRole = accounts.getNumberOfRole(strRole);

		TestReporter.assertGreaterThanZero(numOfRole);

		TestReporter.assertTrue(accounts.verifyRoleLink(strRole),"Verifying [" + strRole + "] link");

		TestReporter.logStep("Clicking [" + strRole + "] role link");
		accounts.clickRoleLink(strRole);

		TestReporter.assertTrue(accounts.verifyRolePageIsLoaded(strAccount,strProject,strRole),"Verifying [" + strRole + "] page loaded");

		TestReporter.logStep("Clicking the Edit Role button");
		accounts.clickEditRole();

		TestReporter.logStep("Clicking Delete in the popup form");
		editRoleForm.clickDelete();

		TestReporter.assertTrue(accounts.wasRoleDeleted(numOfRole, strRole),"Verifying role has been deleted from Project page.");
	}
}
