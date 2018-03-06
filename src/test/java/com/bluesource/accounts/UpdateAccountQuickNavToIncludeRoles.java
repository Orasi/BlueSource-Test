package com.bluesource.accounts;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class UpdateAccountQuickNavToIncludeRoles extends WebBaseTest {
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
	public void updateAccountQuickNavToIncludeRoles() {
		//Test Variables
		String strAccount = "Account1";
		String strProject = "Project1";
		String strRole = "Role1";
		String strSubProject = "SubProject1";
		String strSubProjectRole = "Role2";

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.logStep("Opening Quick Nav menu");
		accounts.clickQuickNav();

		TestReporter.logStep("Clicking [" + strAccount + "] link in Quick Nav menu");
		accounts.clickQuickNavAccount(strAccount);

		TestReporter.assertTrue(accounts.verifyAccountPage(strAccount),"Verifying Account page");

		TestReporter.logStep("Opening Quick Nav menu");
		accounts.clickQuickNav();

		TestReporter.logStep("Expanding [" + strAccount + "] in Quick Nav");
		accounts.expandQuickNavAccount(strAccount);

		TestReporter.logStep("Expanding [" + strProject + "] in Quick Nav");
		accounts.expandQuickNavProject(strAccount, strProject);

		TestReporter.logStep("Clicking [" + strProject + "] in Quick Nav");
		accounts.clickQuickNavProject(strAccount, strProject);

		TestReporter.logStep("Opening Quick Nav menu");
		accounts.clickQuickNav();

		TestReporter.logStep("Expanding [" + strAccount + "] in Quick Nav");
		accounts.expandQuickNavAccount(strAccount);

		TestReporter.logStep("Expanding [" + strProject + "] in Quick Nav");
		accounts.expandQuickNavProject(strAccount, strProject);

		TestReporter.assertTrue(accounts.verifyQuickNavProjectExpansion(strAccount, strProject),
				"Verifying that the Quick Nav project expansion is showing all children");

		TestReporter.logStep("Clicking [" + strRole + "] in Quick Nav");
		accounts.clickQuickNavRole(strAccount, strProject, strRole);

		TestReporter.assertTrue(accounts.verifyRolePage(strAccount, strProject, strRole),"Verifying Role page");

		TestReporter.logStep("Opening Quick Nav menu");
		accounts.clickQuickNav();

		TestReporter.logStep("Expanding [" + strAccount + "] in Quick Nav");
		accounts.expandQuickNavAccount(strAccount);

		TestReporter.logStep("Expanding [" + strProject + "] in Quick Nav");
		accounts.expandQuickNavProject(strAccount, strProject);

		TestReporter.logStep("Expanding [" + strSubProject + "] in Quick Nav");
		accounts.expandQuickNavSubProject(strAccount, strProject, strSubProject);

		TestReporter.logStep("Clicking [" + strSubProject + "] in Quick Nav");
		accounts.clickQuickNavSubProject(strAccount, strProject, strSubProject);

		TestReporter.assertTrue(accounts.verifySubProjectPage(strAccount, strProject, strSubProject),"Verifying SubProject page");


		accounts.clickQuickNavSubProjectRole(strAccount,strProject,strSubProject,strSubProjectRole);

		double random = (Math.random()*100)%16;
		int selector = (int) Math.round(random);

		TestReporter.logStep("Clicking a random Account link from Quick Nav menu");
		accounts.clickRandomQuickNavAccountLink(selector);
	}
}