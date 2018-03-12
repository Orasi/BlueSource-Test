package com.bluesource.reports;

import com.orasi.bluesource.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.ITestContext;

import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class LongDecimalsOnBurnDownTableEditRoleHours extends WebBaseTest {
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
	public void longDecimalsOnBurnDownTableEditRoleHours() {
		//Test Variables
		String strAccount = "Account1";
		String strProject = "Project2";
		String strRole = "Role3";
		String comments = "test";
		String docName, oldHours;

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		EditRoleHoursForm editRoleHoursForm = new EditRoleHoursForm(getDriver());
		AccountBurnDownDataReportForm burnDownDataReportForm = new AccountBurnDownDataReportForm(getDriver());
		Report report = new Report(getDriver());
		ReportingNavBar reportingNavBar = new ReportingNavBar(getDriver());

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(), "Verifying Accounts page is loaded");
		TestReporter.assertTrue(accounts.verifyAccountLink(strAccount),"Verifying Account ["+strAccount+"] link");

		TestReporter.logStep("Clicking Account ["+strAccount+"] link");
		accounts.clickAccountLink(strAccount);

		TestReporter.assertTrue(accounts.verifyAccountPageIsLoaded(strAccount),"Verifying Account ["+strAccount+"] page is loaded");
		TestReporter.assertTrue(accounts.verifyProjectLinkValid(strProject),"Verifying Project ["+strProject+"] link");

		TestReporter.logStep("Clicking Project "+strProject+" link");
		accounts.clickProjectLink(strProject);

		TestReporter.logStep("Getting SOW document name");
		docName = accounts.getSOWDocumentName();

		TestReporter.assertTrue(accounts.verifyProjectPageIsLoaded(strAccount,strProject),"Verifying Project ["+strProject+"] page is loaded");
		TestReporter.assertTrue(accounts.verifyRoleLink(strRole),"Verifying Role ["+strRole+"] link");

		TestReporter.logStep("Clicking Role ["+strRole+"] link");
		accounts.clickRoleLink(strRole);

		TestReporter.assertTrue(accounts.verifyRolePageIsLoaded(strAccount,strProject,strRole),"Verifying Role ["+strRole+"] page is loaded");

		TestReporter.logStep("Getting roles hours");
		oldHours = accounts.getRoleHours();

		TestReporter.logStep("Clicking Edit Hours");
		accounts.clickEditRoleHours();

		TestReporter.assertTrue(editRoleHoursForm.verifyFormLoaded(),"Verifying Edit Role Hours form loaded");

		TestReporter.logStep("Setting comments");
		editRoleHoursForm.setComments(comments);

		TestReporter.logStep("Setting hours");
		editRoleHoursForm.testSetHours();

		TestReporter.logStep("Selecting SOW Document");
		editRoleHoursForm.setDocument("SOW - "+docName.trim());

		TestReporter.logStep("Clicking Add Hours");
		editRoleHoursForm.clickAddHours();

		TestReporter.assertNotEquals(oldHours,accounts.getRoleHours(),"Verifying Role hours were changed");

		TestReporter.logStep("Navigating to BlueSource Reporting login page");
		header.navigateReporting();

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource Reporting login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.assertTrue(reportingNavBar.verifyHomePageIsDisplayed(),"Verifying Reporting home page is displayed");

		TestReporter.logStep("Clicking Account Reports");
		reportingNavBar.clickAccountReports();

		TestReporter.logStep("Clicking Burn Down Data");
		reportingNavBar.clickAccountBurnDownData();

		TestReporter.assertTrue(burnDownDataReportForm.verifyFormLoaded(),"verifying form loaded");

		TestReporter.logStep("Selecting Account ["+strAccount+"]");
		burnDownDataReportForm.selectAccount(strAccount);

		TestReporter.logStep("Clicking Generate Report");
		burnDownDataReportForm.clickGenerateReport();

		TestReporter.assertTrue(report.verifyReportIsLoaded(),"Verifying report loaded");

		TestReporter.assertFalse(report.doesReportHaveLongDecimals(),"Verifying report doesn't have any long decimals");
	}
}