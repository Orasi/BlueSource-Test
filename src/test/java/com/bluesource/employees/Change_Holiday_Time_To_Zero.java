/*
 * Test for changing the holiday time to zero.
 * @author: Zach Apple 
 */
package com.bluesource.employees;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.ReportedTimesSummary;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class Change_Holiday_Time_To_Zero extends WebBaseTest {
	@BeforeMethod
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion", "operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest, String browserVersion,
			String operatingSystem, String environment) {
		setApplicationUnderTest("BLUESOURCE");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setEnvironment(environment);
		setThreadDriver(true);
		testStart("Change_Holiday_Time_To_Zero");
	}

	@AfterMethod
	public void close(ITestContext testResults) {
		endTest("TestAlert", testResults);
	}

	@Test(groups = { "smoke" })
	public void change_holiday_to_zero() {
		TestReporter.logStep("Log in as a project user");
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.Login("PROJECT_USER");
		TestReporter.logStep("Click the 'Manage' button in the 'Project Info' section");
		EmployeePage employeePage = new EmployeePage(getDriver());
		employeePage.clickManageProject();
		TestReporter.logStep("Find a timesheet with holiday time, change it to zero, and verify the change shows hours zeroed out.");
		ReportedTimesSummary reportedTimesSummary = new ReportedTimesSummary(getDriver());
		TestReporter.assertTrue(reportedTimesSummary.changeHolidayTimeToZeroAndVerify(), "Verify that the timesheet with holiday time shows zero when set to zero");
	}
}
