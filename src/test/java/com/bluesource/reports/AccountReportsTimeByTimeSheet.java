package com.bluesource.reports;

import com.orasi.bluesource.*;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class AccountReportsTimeByTimeSheet extends WebBaseTest {
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
	public void accountReportsTimeByTimeSheet() {
		//Test Variables
		String strAccount = "Account1";
		String strStartDate = "12/01/2017";
		String strEndDate = "03/07/2018";

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		ReportingNavBar reportingNavBar = new ReportingNavBar(getDriver());
		AccountTimeByTimeSheetForm accountTimeByTimeSheetForm = new AccountTimeByTimeSheetForm(getDriver());
		Report report = new Report(getDriver());

		TestReporter.logStep("Navigating to reports login page");
		header.navigateReporting();

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying reporting login page is displayed");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.assertTrue(reportingNavBar.verifyHomePageIsDisplayed(),"Verifying Reporting Home page is Displayed");

		TestReporter.logStep("Expanding Account Reports");
		reportingNavBar.clickAccountReportsDropdown();

		TestReporter.logStep("Clicking Time by Time Sheet");
		reportingNavBar.clickAccountTimeByTimeSheet();

		TestReporter.assertTrue(accountTimeByTimeSheetForm.verifyAccountTimeByTimeSheetFormLoaded(),"Verifying Time by Time Sheet form loaded");

		TestReporter.logStep("Selecting Account [" + strAccount + "]");
		accountTimeByTimeSheetForm.selectAccount(strAccount);

		TestReporter.logStep("Setting Start Date");
		accountTimeByTimeSheetForm.setStartDate(strStartDate);

		TestReporter.logStep("Setting End Date");
		accountTimeByTimeSheetForm.setEndDate(strEndDate);

		TestReporter.logStep("Clicking Generate Report");
		accountTimeByTimeSheetForm.clickGenerateReport();

		TestReporter.assertTrue(report.verifyReportIsLoaded(),"Verifying Report page is loaded");

		TestReporter.assertTrue(report.checkTotals(),"Verifying Report totals are correct");

		TestReporter.assertEquals(report.getTitle(),"Time by Time Sheet: "+strStartDate+"-"+strEndDate,"Verifying Report Title");
	}
}