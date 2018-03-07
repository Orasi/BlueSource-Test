package com.bluesource.Admin;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.LockedTimesheet;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class Locked_Timesheet extends WebBaseTest {    
		
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
				testStart("Test Locked Timesheet");
		}

		@AfterMethod
		public void close(ITestContext testResults){
			endTest("TestAlert", testResults);
		}

		@Test(groups = {"smoke"} )
		public void testLockedTimesheet() {
			TestReporter.setDebugLevel(2);

			TestReporter.logScenario("Test Locked Timesheet");

			setPageURL("http://10.238.243.127/login");
			
			testStart("Test Locked Timesheet");

			LoginPage loginPage = new LoginPage(getDriver());
			LockedTimesheet lockedTimesheet = new LockedTimesheet(getDriver());
			
			TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Page Loaded");
			
			//admin login
			loginPage.LoginWithCredentials("company.admin", "anything");
			
			TestReporter.assertTrue(lockedTimesheet.clickTimesheetLocks(), "Timesheet Locks link clicked");
			TestReporter.assertTrue(lockedTimesheet.createLockedTimesheet("February"), "Lock Timesheet");
			TestReporter.assertTrue(lockedTimesheet.verifySuccessAlert(), "Timesheet Locked Successfully");
			lockedTimesheet.logout();
			TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Successfully logged out");
			
			//employee login
			loginPage.LoginWithCredentials("wwww", "anything");
			TestReporter.assertTrue(lockedTimesheet.clickManageButton(), "Manage button clicked");
			TestReporter.assertTrue(lockedTimesheet.selectMonthWithLockedTimesheet("February"), "Month Selected");
			lockedTimesheet.addNewTimesheet("Bench");
			TestReporter.assertTrue(lockedTimesheet.verifySecondSuccessAlert(), "Locked timesheet created");
			lockedTimesheet.logout();
			
			//Employee reports
			lockedTimesheet.newLogin(getDriver());
			loginPage.LoginWithCredentials("company.admin", "anything");
			
			lockedTimesheet.checkTimeByProjectTab("wwww bbbb", "02/05/2018", "02/11/2018");
			TestReporter.assertTrue(lockedTimesheet.noReportsMsgDisplayed(), "No results are displayed");
			lockedTimesheet.checkTimeByRoleTab("wwww bbbb", "02/05/2018", "02/11/2018");
			TestReporter.assertTrue(lockedTimesheet.noReportsMsgDisplayed(), "No results are displayed");
			lockedTimesheet.checkTimeByTimeSheetTab("wwww bbbb", "02/05/2018", "02/11/2018");
			TestReporter.assertTrue(lockedTimesheet.noReportsMsgDisplayed(), "No results are displayed");
			lockedTimesheet.checkBillingByProjectTab("wwww bbbb", "02/05/2018", "02/11/2018");
			TestReporter.assertTrue(lockedTimesheet.noReportsMsgDisplayed(), "No results are displayed");
			lockedTimesheet.checkBillingByRoleTab("wwww bbbb", "02/05/2018", "02/11/2018");
			TestReporter.assertTrue(lockedTimesheet.noReportsMsgDisplayed(), "No results are displayed");
			lockedTimesheet.checkCombinedTotalHoursTab("wwww bbbb", "02/05/2018", "02/11/2018");
			TestReporter.assertTrue(lockedTimesheet.totalHoursTableDisplayed(), "Table is displayed");
		}
}
