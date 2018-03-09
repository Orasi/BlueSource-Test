package com.bluesource.reports;

import com.orasi.bluesource.*;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.bouncycastle.jce.provider.symmetric.TEA;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		testStart("Select all for Projects and Accounts BlueSource Reports");
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
		int columnBudget = 6;
		int columnReported = 7;

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		ReportingNavBar reportingNavBar = new ReportingNavBar(getDriver());
		AccountBurndownDataReportForm form = new AccountBurndownDataReportForm(getDriver());
		Report report = new Report(getDriver());

		TestReporter.logStep("Navigating to BlueSource Reporting");
		header.navigateReporting();

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.assertTrue(reportingNavBar.verifyHomePageIsDisplayed(),"Verifying BlueSource Reporting home page is displayed");

		TestReporter.logStep("Clicking 'Account Reports'");
		reportingNavBar.clickAccountReports();

		TestReporter.logStep("Clicking 'Burn Down Data'");
		reportingNavBar.clickAccountBurnDownData();

		TestReporter.assertTrue(form.verifyFormLoaded(),"Verifying Account Burndown Data Report form loaded");

		TestReporter.logStep("Storing all selectable accounts");
		allAccounts = form.getAllAccounts();

		TestReporter.logStep("Clicking Select All");
		form.clickSelectAll();

		TestReporter.logStep("Clicking Generate Report");
		form.clickGenerateReport();

		TestReporter.assertTrue(report.verifyReportIsLoaded(),"Verifying report is loaded");

		TestReporter.assertEquals(reportTitle,report.getTitle(),"Verifying report title");

		TestReporter.assertFalse(report.doesColumnHaveEmptyValues(columnReported),"Verifying 'Reported $' column has no empty values");

		TestReporter.assertFalse(report.doesColumnHaveEmptyValues(columnBudget),"Verifying 'Budget $' column has no empty values");

		TestReporter.assertTrue(checkAccounts(allAccounts,report.getAllAccounts()),"Verifying all accounts are displayed");
	}

	/**
	 * @author David Grayson
	 * @param reference {@link List<String>} The selectable accounts
	 * @param displayed {@link List<String>} The account displayed in the report
	 * @return {@link Boolean} Returns true if no accounts were missing from the report
	 * OR if all the missing accounts don't have active projects
	 */
	private boolean checkAccounts(List<String> reference, List<String> displayed) {
		ArrayList<String> missing = new ArrayList<>();
		for (String s : reference) {
			if (!displayed.contains(s)) {
				TestReporter.log("displayed didn't contain: " + s);
				missing.add(s);
			}
		}
		return missing.size() <= 0 || checkMissing(missing);
	}

	/**
	 * @author David Grayson
	 * @param missing {@link ArrayList<String>} the Accounts that were selectable but not displayed in the burn down report
	 * @return {@link Boolean} Returns true if the missing accounts don't have active projects
	 */
	private boolean checkMissing(ArrayList<String> missing) {
		TestReporter.logStep("Checking that missing Accounts don't have Projects");

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());

		TestReporter.logStep("Navigating to BlueSource");
		getDriver().get("http://10.238.243.127/login");

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(), "Verifying Accounts page is loaded");

		for (String s:missing){
			TestReporter.assertTrue(accounts.verifyAccountLink(s),"Verifying account ["+s+"] link");
			TestReporter.assertFalse(accounts.doesAccountHaveActiveProjects(s),"Verifying account ["+s+"] doesn't have projects");
		}

		return true;
	}
}