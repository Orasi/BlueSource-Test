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

public class CreateNewBurndownReportFormat extends WebBaseTest {
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
	public void createNewBurndownReportFormat() {
		//Test Variables
		String bluesourceURL = "http://10.238.243.127/login";
		String strAccount = "Account1";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String strCurrentDate = sdf.format(new Date());
		HashMap<String,String[]> projectDataHashMap = null;


		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		ReportingNavBar reportingNavBar = new ReportingNavBar(getDriver());
		AccountBurndownDataReportForm form = new AccountBurndownDataReportForm(getDriver());
		Report report = new Report(getDriver());

		header.navigateReporting();

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource Reporting login page is loaded");

		loginPage.AdminLogin();

		TestReporter.assertTrue(reportingNavBar.verifyHomePageIsDisplayed(),"Verifying Reporting home page is displayed");

		reportingNavBar.clickAccountReports();

		reportingNavBar.clickBurnDownData();

		TestReporter.assertTrue(form.verifyFormLoaded(), "Verifying Account Burndown Data Report form loaded");

		form.selectAccount(strAccount);

		form.clickGenerateReport();

		TestReporter.assertTrue(report.verifyReportIsLoaded(),"Verifying report page loaded");

		TestReporter.assertEquals("Account Burndown Data Report: " + strCurrentDate,report.getTitle(),"Verifying report title");

		projectDataHashMap = getReportData(report,strAccount);

		getDriver().get(bluesourceURL);

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource login page loaded");

		loginPage.AdminLogin();

		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(),"Verifying Accounts page loaded");

		accounts.clickAccountLink(strAccount);

		for (String project:accounts.getAllProject()){
			TestReporter.assertTrue(projectDataHashMap.containsKey(project),"Verifying project [" + project + "] was in report");

			String[] projectData = projectDataHashMap.get(project);

			accounts.clickProjectLink(project);
			PageLoaded.isDomComplete(getDriver());

			TestReporter.log("["+projectData[0]+"]");
			TestReporter.log("["+accounts.getSOW()+"]");

			TestReporter.assertEquals(projectData[0],accounts.getSOW(),"Verifying Project [" + project + "] SOW");

			TestReporter.log("["+projectData[1]+"]");
			TestReporter.log("["+accounts.getAllocatedHours()+"]");

			TestReporter.assertEquals(projectData[1],accounts.getAllocatedHours(),"Verifying Budget Hours");

			TestReporter.log("["+projectData[2]+"]");
			TestReporter.log("["+accounts.getSubmittedHours()+"]");

			TestReporter.assertEquals(projectData[2],accounts.getSubmittedHours(),"Verifying Reported Hours");

			TestReporter.log("["+projectData[3]+"]");
			TestReporter.log("["+accounts.getAllocatedBudget()+"]");

			TestReporter.assertEquals(projectData[3],accounts.getAllocatedBudget(),"Verifying Budget $");

			TestReporter.log("["+projectData[4]+"]");
			TestReporter.log("["+accounts.getSubmittedBudget()+"]");

			TestReporter.assertEquals(projectData[4],accounts.getSubmittedBudget(),"Verifying Reported $");

			for (String subProject:accounts.getAllSubProjects()){
				TestReporter.assertTrue(projectDataHashMap.containsKey(project+" / "+subProject),"Verifying sub project [" + subProject + "] was in report");

				accounts.clickSubprojectLink(subProject);
				PageLoaded.isDomComplete(getDriver());

				String[] subProjectData = projectDataHashMap.get(project + " / " + subProject);

				TestReporter.assertEquals(subProjectData[0],accounts.getSOW(),"Verifying sub project [" + subProject + "] SOW");

				TestReporter.log("["+subProjectData[1]+"]");
				TestReporter.log("["+accounts.getAllocatedHours()+"]");

				TestReporter.assertEquals(subProjectData[1],accounts.getAllocatedHours(),"Verifying sub project Budget Hours");

				TestReporter.log("["+subProjectData[2]+"]");
				TestReporter.log("["+accounts.getSubmittedHours()+"]");

				TestReporter.assertEquals(subProjectData[2],accounts.getSubmittedHours(),"Verifying sub project Reported Hours");

				TestReporter.log("["+subProjectData[3]+"]");
				TestReporter.log("["+accounts.getAllocatedBudget()+"]");

				TestReporter.assertEquals(subProjectData[3],accounts.getAllocatedBudget(),"Verifying sub project Budget $");

				TestReporter.log("["+subProjectData[4]+"]");
				TestReporter.log("["+accounts.getSubmittedBudget()+"]");

				TestReporter.assertEquals(subProjectData[4],accounts.getSubmittedBudget(),"Verifying sub project Reported $");

				getDriver().navigate().back();
			}
			getDriver().navigate().back();
		}

	}

	private HashMap<String,String[]> getReportData(Report report, String strAccount) {
		HashMap<String,String[]> projectHashMap = new HashMap<>();
		for (String project: report.getProjects(strAccount)){
			projectHashMap.put(project,report.getProjectData(project));
			for (String subProject:report.getSubProjects(project)){
				projectHashMap.put(subProject,report.getSubProjectData(subProject));
			}
		}
		return projectHashMap;
	}
}