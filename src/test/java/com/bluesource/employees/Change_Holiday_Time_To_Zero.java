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
		LoginPage loginPage = new LoginPage(getDriver());
		EmployeePage employeePage = new EmployeePage(getDriver());
		ReportedTimesSummary reportedTimesSummary = new ReportedTimesSummary(getDriver());
		// 1. Navigate to the login page
		// 2. Login with base employee username
		loginPage.Login("PROJECT_USER");
		// 3. Click the 'Manage' button in the 'Project Info' section
		employeePage.clickManageProject();
		// 4. Find a timesheet period with time charged toward 'Company Holiday' or
		// 'Holiday'.
		// Look through the current periods table for a project that is editable, search
		// it for holiday billing
		TestReporter.assertTrue(reportedTimesSummary.changeHolidayTimeToZeroAndVerify(), "Verify that the timesheet with holiday time shows zero when set to zero");

		System.out.println("wait");

	}
}
