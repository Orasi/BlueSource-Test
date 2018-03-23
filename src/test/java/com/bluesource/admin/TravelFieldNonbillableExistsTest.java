package com.bluesource.admin;

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

public class TravelFieldNonbillableExistsTest extends WebBaseTest {
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
		testStart("");
	}

	@AfterMethod
	public void close(ITestContext testResults) {
		endTest("TestAlert", testResults);
	}

	@Test(groups = { "smoke" })
	public void testTravelField() {
		TestReporter.logStep("Login with admin access");
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.Login("ADMIN");
		TestReporter.logStep("Click the first name of the first employee");
		EmployeePage employeePage = new EmployeePage(getDriver());
		employeePage.clickFirstEmployee();
		TestReporter.logStep("Click the manage button under Project Info");
		employeePage.clickManageProject();
		TestReporter.logStep("CLick the add or edit button for a week");
		ReportedTimesSummary reportedTimesSummary = new ReportedTimesSummary(getDriver());
		reportedTimesSummary.clickOnTimesheet();
		TestReporter.logStep("Click on 'Add Non-Billable' and verify that 'Travel' is an option");
		TestReporter.assertTrue(reportedTimesSummary.verifyTravelOption(), "Verify that 'Travel' is an option");
	}
}
