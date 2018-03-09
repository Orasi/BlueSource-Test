package com.bluesource.reports;

import com.orasi.bluesource.*;
import com.orasi.utils.TestReporter;
import com.orasi.web.PageLoaded;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.ITestContext;

import com.orasi.web.WebBaseTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CreateNewBurnDownReportFormat extends WebBaseTest {
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
		testStart("Create New Burn Down Report Format");
	}

	@AfterMethod
	public void close(ITestContext testResults) {
		endTest("TestAlert", testResults);
	}

	@Test
	public void createNewBurnDownReportFormat() {
		//Test Variables
		String bluesourceURL = "http://10.238.243.127/login";
		String strAccount = "Account1";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String strCurrentDate = sdf.format(new Date());
		HashMap<String,String[]> projectDataHashMap;


		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		ReportingNavBar reportingNavBar = new ReportingNavBar(getDriver());
		AccountBurnDownDataReportForm form = new AccountBurnDownDataReportForm(getDriver());
		Report report = new Report(getDriver());

		TestReporter.logStep("Navigating to BlueSource Reporting login");
		header.navigateReporting();

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource Reporting login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.assertTrue(reportingNavBar.verifyHomePageIsDisplayed(),"Verifying Reporting home page is displayed");

		TestReporter.logStep("Clicking 'Account Reports'");
		reportingNavBar.clickAccountReports();

		TestReporter.logStep("Clicking 'Burn Down Data'");
		reportingNavBar.clickBurnDownData();

		TestReporter.assertTrue(form.verifyFormLoaded(), "Verifying Account Burn Down Data Report form loaded");

		TestReporter.logStep("Selecting Account [" + strAccount + "]");
		form.selectAccount(strAccount);

		TestReporter.logStep("Clicking Generate Report");
		form.clickGenerateReport();

		TestReporter.assertTrue(report.verifyReportIsLoaded(),"Verifying report page loaded");

		TestReporter.assertEquals("Account Burn Down Data Report: " + strCurrentDate,report.getTitle(),"Verifying report title");

		TestReporter.logStep("Storing report data for comparison");
		projectDataHashMap = getReportData(report,strAccount);

		TestReporter.logStep("Navigating to BlueSource login");
		getDriver().get(bluesourceURL);

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource login page loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(),"Verifying Accounts page loaded");

		TestReporter.logStep("Clicking [" + strAccount + "] link");
		accounts.clickAccountLink(strAccount);

		for (String project:accounts.getAllProject()){
			TestReporter.assertTrue(projectDataHashMap.containsKey(project),"Verifying project [" + project + "] was in report");

			String[] projectData = projectDataHashMap.get(project);

			TestReporter.logStep("Clicking ["+project+"] link");
			accounts.clickProjectLink(project);

			TestReporter.assertTrue(PageLoaded.isDomComplete(getDriver()),"Verifying Project [" + project + "] page is completely loaded");

			TestReporter.assertEquals(projectData[0],accounts.getSOW(),"Verifying Project [" + project + "] SOW");

			TestReporter.assertEquals(projectData[1],accounts.getAllocatedHours(),"Verifying Budget Hours");

			TestReporter.assertEquals(projectData[2],accounts.getSubmittedHours(),"Verifying Reported Hours");

			TestReporter.assertEquals(projectData[3],accounts.getAllocatedBudget(),"Verifying Budget $");

			TestReporter.assertEquals(projectData[4],accounts.getSubmittedBudget(),"Verifying Reported $");

			for (String subProject:accounts.getAllSubProjects()){
				TestReporter.assertTrue(projectDataHashMap.containsKey(project+" / "+subProject),"Verifying sub project [" + subProject + "] was in report");

				TestReporter.logStep("Clicking ["+subProject+"] link");
				accounts.clickSubprojectLink(subProject);

				TestReporter.assertTrue(PageLoaded.isDomComplete(getDriver()),"Verifying Sub Project [" + subProject + "] page is completely loaded");

				String[] subProjectData = projectDataHashMap.get(project + " / " + subProject);

				TestReporter.assertEquals(subProjectData[0],accounts.getSOW(),"Verifying sub project [" + subProject + "] SOW");

				TestReporter.assertEquals(subProjectData[1],accounts.getAllocatedHours(),"Verifying sub project Budget Hours");

				TestReporter.assertEquals(subProjectData[2],accounts.getSubmittedHours(),"Verifying sub project Reported Hours");

				TestReporter.assertEquals(subProjectData[3],accounts.getAllocatedBudget(),"Verifying sub project Budget $");

				TestReporter.assertEquals(subProjectData[4],accounts.getSubmittedBudget(),"Verifying sub project Reported $");

				getDriver().navigate().back();
			}
			getDriver().navigate().back();
		}
	}

	/**
	 * @author David Grayson
	 * @param report {@link Report} The report page model to get data from
	 * @param strAccount {@link String} The account to get data for
	 * @return {@link HashMap<String,String[]>}Returns a HashMap where the keys are the accounts Project/SubProject names and the values are their respective rows data from the report as String[]
	 */
	private HashMap<String,String[]> getReportData(Report report, String strAccount) {
		HashMap<String,String[]> projectHashMap = new HashMap<>();
		for (String project: report.getAccountProjects(strAccount)){
			projectHashMap.put(project,report.getRowData(project));
		}
		return projectHashMap;
	}
}