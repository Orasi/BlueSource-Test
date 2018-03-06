package com.bluesource.reports;

import com.orasi.bluesource.AccountTimeByTimeSheetForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.ReportsNavBar;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class AccountReportsTimeByTimesheet extends WebBaseTest {
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
	public void accountReportsTimeByTimesheet() {
		//Test Variables
		String strAccount = "";
		String strProject = "";

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		ReportsNavBar reportsNavBar = new ReportsNavBar(getDriver());
		AccountTimeByTimeSheetForm accountTimeByTimeSheetForm = new AccountTimeByTimeSheetForm(getDriver());

		header.navigateReporting();

		loginPage.AdminLogin();

		reportsNavBar.clickAccountReportsDropdown();

		reportsNavBar.clickAccountTimeByTimeSheet();


	}
}