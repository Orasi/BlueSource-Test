package com.bluesource.Admin;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.VacationReport;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class Vacation_Report extends WebBaseTest {
	
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
			testStart("Pull Vacation Report");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(groups = {"smoke"} )
	public void testLockedTimesheet() {
		TestReporter.setDebugLevel(2);

		TestReporter.logScenario("Pull Vacation Report");

		setPageURL("http://10.238.243.127:8080/reporting/login");
		
		testStart("Pull Vacation Report");

		LoginPage loginPage = new LoginPage(getDriver());
		VacationReport vacationReport = new VacationReport(getDriver());
		
		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Page Loaded");
		
		loginPage.LoginWithCredentials("company.admin", "anything");

		TestReporter.assertTrue(vacationReport.clickEmployeeReportsTab(), "Employee Reports Tab is clicked");

		TestReporter.assertTrue(vacationReport.clickVacationTab(), "Vacation Tab is clicked");
		
		vacationReport.generateReport("bbbb bbbb", "03/05/2018", "03/07/2018");
		
		TestReporter.assertTrue(vacationReport.vacationTimesheetTableVisible(), "Vacation Report is generated");
	}
}
