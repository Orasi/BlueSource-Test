package com.bluesource.reports;

import com.orasi.bluesource.*;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SelectAllForProjectsAndAccountsBlueSourceReports extends WebBaseTest {
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
	public void selectAllForProjectsAndAccountsBlueSourceReports() {
		//Test Variables
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String reportTitle = "Account Burndown Data Report: " + sdf.format(new Date()); //comparison title with current date
		List<String> allAccounts;

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		ReportingNavBar reportingNavBar = new ReportingNavBar(getDriver());
		AccountBurndownDataReportForm form = new AccountBurndownDataReportForm(getDriver());
		Report report = new Report(getDriver());

		header.navigateReporting();

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying login page is loaded");

		loginPage.AdminLogin();

		TestReporter.assertTrue(reportingNavBar.verifyHomePageIsDisplayed(),"Verifying BlueSource Reporting home page is displayed");

		reportingNavBar.clickAccountReports();

		reportingNavBar.clickBurnDownData();

		TestReporter.assertTrue(form.verifyFormLoaded(),"Verifying Account Burndown Data Report form loaded");

		allAccounts = form.getAllAccounts();

		form.clickSelectAll();

		form.clickGenerateReport();

		TestReporter.assertTrue(report.verifyReportIsLoaded(),"Verifying report is loaded");

		TestReporter.assertEquals(reportTitle,report.getTitle(),"Verifying report title");

		TestReporter.assertFalse(report.doesColumnHaveEmptyValues(7),"Verifying 'Reported $' column has no empty values");

		TestReporter.assertFalse(report.doesColumnHaveEmptyValues(6),"Verifying 'Budget $' column has no empty values");

		TestReporter.assertTrue(checkAccounts(allAccounts,report.getAllAccounts()),"Verifying all accounts are displayed");
	}

	private boolean checkAccounts(List<String> reference, List<String> displayed){
		if (reference.size() == displayed.size()){
			for (String s:reference){
				if (!displayed.contains(s)){
					TestReporter.log("displayed didn't contain: " + s);
					return false;
				}
			}
			return true;
		} else {
			TestReporter.log("The reference and displayed sizes are different");
			TestReporter.log("reference size = " + reference.size());
			TestReporter.log("displayed size = " + displayed.size());
			return false;
		}
	}
}