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
				testStart("Create_Basic_Employee");
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
			
			loginPage.LoginWithCredentials("company.admin", "anything");
			lockedTimesheet.openLockedTimesheet();
			lockedTimesheet.createLockedTimesheet("February");
			lockedTimesheet.verifySuccessAlert();
			lockedTimesheet.logout();
			loginPage.verifyPageIsLoaded();
			loginPage.LoginWithCredentials("bbbb", "anything");
			lockedTimesheet.clickManageButton();
			lockedTimesheet.selectMonthWithLockedTimesheet("February");
			lockedTimesheet.addNewTimesheet("Bench");
			lockedTimesheet.verifySecondSuccessAlert();
			lockedTimesheet.logout();
			lockedTimesheet.newLogin(getDriver());
			loginPage.LoginWithCredentials("company.admin", "anything");
			lockedTimesheet.checkTimeByProjectTab("bbbb bbbb", "02/05/2018", "02/11/2018");
			lockedTimesheet.checkTimeByRoleTab("bbbb bbbb", "02/05/2018", "02/11/2018");
			lockedTimesheet.checkTimeByTimeSheetTab("bbbb bbbb", "02/05/2018", "02/11/2018");
			lockedTimesheet.checkBillingByProjectTab("bbbb bbbb", "02/05/2018", "02/11/2018");
			lockedTimesheet.checkBillingByRoleTab("bbbb bbbb", "02/05/2018", "02/11/2018");
			lockedTimesheet.checkCombinedTotalHoursTab("bbbb bbbb", "02/05/2018", "02/11/2018");
		}
}
