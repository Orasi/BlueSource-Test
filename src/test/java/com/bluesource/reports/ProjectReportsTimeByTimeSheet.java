package com.bluesource.reports;

import com.orasi.bluesource.*;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class ProjectReportsTimeByTimeSheet extends WebBaseTest {
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
	public void ProjectReportsTimeByTimeSheet() {
		//Test Variables
		String strProject = "Project1";
		String strStartDate = "10/01/2017";
		String strEndDate = "03/07/2018";
		String reportTitle = "Time by Time Sheet: " + strStartDate + "-" + strEndDate;

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		ReportingNavBar reportingNavBar = new ReportingNavBar(getDriver());
		ProjectTimeByTimeSheetForm projectTimeByTimeSheetForm = new ProjectTimeByTimeSheetForm(getDriver());
		Report report = new Report(getDriver());

		TestReporter.logStep("Navigating to BlueSourceReport");
		header.navigateReporting();

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying login page has loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.assertTrue(reportingNavBar.verifyHomePageIsDisplayed(),"Verifying Reporting Home Page is displayed");

		TestReporter.logStep("Clicking Projects Reports");
		reportingNavBar.clickProjectsReport();

		TestReporter.logStep("Clicking 'Time by Time Sheet' under Projects Reports");
		reportingNavBar.clickProjectsReportTimeByTimeSheet();

		TestReporter.assertTrue(projectTimeByTimeSheetForm.verifyFormIsLoaded(),"Verifying form has loaded");

		TestReporter.logStep("Selecting Project");
		projectTimeByTimeSheetForm.selectProject(strProject);

		TestReporter.logStep("Setting Start Date");
		projectTimeByTimeSheetForm.setStartDate(strStartDate);

		TestReporter.logStep("Setting End Date");
		projectTimeByTimeSheetForm.setEndDate(strEndDate);

		TestReporter.logStep("Clicking Generate Report");
		projectTimeByTimeSheetForm.clickGenerateReport();

		TestReporter.assertTrue(report.verifyReportIsLoaded(),"Verifying report has loaded");

		TestReporter.assertEquals(reportTitle,report.getTitle(),"Verifying report title");

		TestReporter.assertTrue(report.checkTotals(), "Verifying totals are correct");
	}
}