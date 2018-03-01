package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.EditRoleRateForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class Current_Rate_for_role_shows_on_project_show_page extends WebBaseTest{
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
	public void current_Rate_for_role_shows_on_project_show_page(){
		//TestReporter.setDebugLevel(3);
		TestReporter.logScenario("START");

		String roleRate;
		String strAccount = "Account1";
		String strProject = "Project2";
		String strRole = "Role3";

		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		EditRoleRateForm rateForm = new EditRoleRateForm(getDriver());

		loginPage.AdminLogin();

		header.navigateAccounts();

		accounts.clickAccountLink(strAccount);

		accounts.clickProjectLink(strProject);

		roleRate = accounts.getRoleRateFromProjectPage(strRole);

		TestReporter.assertNotNull(roleRate,"Check that the test found the rate");

		accounts.clickRoleLink(strRole);

		TestReporter.assertTrue(accounts.verifyRoleRate(roleRate), "Check that the role rate from project page is correct.");

		accounts.clickEditRoleRate(roleRate);

		rateForm.testRate();

		rateForm.testComments();

		rateForm.clickSubmit();

		TestReporter.assertFalse(accounts.verifyRoleRate(roleRate), "Check that the role rate updated");
	}
}
