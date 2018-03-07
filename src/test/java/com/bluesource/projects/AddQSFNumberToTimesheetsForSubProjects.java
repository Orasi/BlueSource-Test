package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.SubProjectTimeSheets;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class AddQSFNumberToTimesheetsForSubProjects extends WebBaseTest {
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
		testStart("Add_QSF_Number_to_Timesheets_for_Sub_Projects");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test
	public void addQSFNumberToTimeSheetsForSubProjects(){
		/**Test Variables**/
		String strAccount = "AccountOnlySubs1";
		String strProject = "Project1";
		String strSubProject = "SubProject1";
		String strSOW;

		/**Page Models**/
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		SubProjectTimeSheets subProjectTimeSheets = new SubProjectTimeSheets(getDriver());

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(),"Verifying the Accounts page is loaded");

		TestReporter.assertTrue(accounts.verifyAccountLink(strAccount),"Verifying [" + strAccount + "] link");

		TestReporter.logStep("Clicking " + strAccount + " account link");
		accounts.clickAccountLink(strAccount);

		TestReporter.assertTrue(accounts.verifyProjectLinkValid(strProject),"Verifying [" + strProject + "] link");

		TestReporter.logStep("Click " + strProject + " project link");
		accounts.clickProjectLink(strProject);

		TestReporter.assertTrue(accounts.verifyProjectPageIsLoaded(strAccount,strProject),"Verifying [" + strProject + "] page is loaded");

		TestReporter.logStep("Getting Project SOW");
		strSOW = accounts.getProjectSOW();

		TestReporter.assertTrue(accounts.verifySubProjectLink(strSubProject),"Verifying [" + strSubProject + "] link");

		TestReporter.logStep("Clicking " + strSubProject + " sub project link");
		accounts.clickSubprojectLink(strSubProject);

		TestReporter.logStep("Click sub project time sheets");
		accounts.clickProjectTimeSheets();

		while(!subProjectTimeSheets.hasTimeSheet()){
			TestReporter.logStep("Going back one month");
			subProjectTimeSheets.goBackOneMonth();
		}

		TestReporter.assertEquals(strSOW, subProjectTimeSheets.getSOW(), "Verifying sub project time sheet SOW matches sub projects");
	}
}
