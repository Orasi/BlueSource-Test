package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.SubProjectTimeSheets;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class Add_QSF_Number_to_Timesheets_for_Sub_Projects extends WebBaseTest {
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
	public void add_QSF_Number_to_Timesheets_for_Sub_Projects(){
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

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.logStep("Clicking " + strAccount + " account link");
		accounts.clickAccountLink(strAccount);

		TestReporter.logStep("Click " + strProject + " project link");
		accounts.clickProjectLink(strProject);

		TestReporter.logStep("Getting Project SOW");
		strSOW = accounts.getProjectSOW();

		TestReporter.logStep("Clicking " + strSubProject + " sub project link");
		accounts.clickSubprojectLink(strSubProject);

		TestReporter.logStep("Click sub project timesheets");
		accounts.clickProjectTimeSheets();

		while(!subProjectTimeSheets.hasTimeSheet()){
			TestReporter.logStep("Going back one month");
			subProjectTimeSheets.goBackOneMonth();
		}

		TestReporter.assertEquals(strSOW, subProjectTimeSheets.getSOW(), "Verifying sub project timesheet SOW matches sub projects");
	}
}
