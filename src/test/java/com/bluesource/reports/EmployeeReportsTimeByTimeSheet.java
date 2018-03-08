package com.bluesource.reports;

import com.orasi.bluesource.*;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.util.List;

public class EmployeeReportsTimeByTimeSheet extends WebBaseTest {

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
	public void employeeReportsTimeByTimeSheet() {
		//Test Variables
		String firstName = "Holly";
		String lastName = "Barnett";
		String employee = firstName + " " + lastName;
		String startDate = "12/01/2017";
		String endDate = "03/07/2018";
		List<String> employees;

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		ReportingNavBar reportingNavBar = new ReportingNavBar(getDriver());
		EmployeeTimeByTimeSheetForm employeeTimeByTimeSheetForm = new EmployeeTimeByTimeSheetForm(getDriver());
		Report report = new Report(getDriver());

		TestReporter.logStep("Navigating to BlueSource Reporting login page");
		header.navigateReporting();

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.assertTrue(reportingNavBar.verifyHomePageIsDisplayed(),"Verifying BlueSource Reporting home page is displayed");

		TestReporter.logStep("Expanding 'Employee Reports' in side nav bar");
		reportingNavBar.clickEmployeeReportsDropDown();

		TestReporter.logStep("Clicking 'Time by Time Sheet' option under 'Employee Reports'");
		reportingNavBar.clickEmployeeTimeByTimeSheet();

		TestReporter.assertTrue(employeeTimeByTimeSheetForm.verifyFormLoaded(),"Verifying Employee Time by Time Sheet form is loaded");

		TestReporter.logStep("Selecting Employee");
		employeeTimeByTimeSheetForm.selectEmployee(employee);

		TestReporter.logStep("Setting the Start Date");
		employeeTimeByTimeSheetForm.setStartDate(startDate);

		TestReporter.logStep("Setting the End Date");
		employeeTimeByTimeSheetForm.setEndDate(endDate);

		TestReporter.logStep("Clicking Generate Form");
		employeeTimeByTimeSheetForm.clickGenerateReport();

		TestReporter.assertTrue(report.verifyReportIsLoaded(),"Verifying Report is loaded");

		TestReporter.assertEquals(report.getTitle(),"Time by Time Sheet: "+startDate+"-"+endDate,"Verifying Report is for correct time period");

		TestReporter.assertTrue(report.checkTotals(),"Verifying report totals");

		TestReporter.logStep("Getting Employees listed in report");
		employees = report.getEmployees();

		employee = lastName + ", " + firstName; //refactoring for comparison

		TestReporter.assertTrue(employees.size() == 1,"Verifying there is only one employee in report");

		TestReporter.assertEquals(employees.get(0),employee,"Verifying employee in report");
	}
}