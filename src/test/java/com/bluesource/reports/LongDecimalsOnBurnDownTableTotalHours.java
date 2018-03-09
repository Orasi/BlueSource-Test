package com.bluesource.reports;

import com.orasi.bluesource.*;
import com.orasi.web.webelements.Textbox;
import org.bouncycastle.jce.provider.symmetric.TEA;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.ITestContext;

import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class LongDecimalsOnBurnDownTableTotalHours extends WebBaseTest {
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
	public void longDecimalsOnBurnDownTableTotalHours() {
		//Test Variables
		String strAccount = "Account1";
		String strProject = "Project1";
		String filePath = "C:\\Users\\david.grayson\\Documents\\SOW.txt";
		String docName = "Project1-SOW";
		int budget = 549875625;
		double hours = 131.81;

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		AddDocumentForm addDocumentForm = new AddDocumentForm(getDriver());
		ReportingNavBar reportingNavBar = new ReportingNavBar(getDriver());
		AccountBurnDownDataReportForm burnDownDataReportForm = new AccountBurnDownDataReportForm(getDriver());
		Report report = new Report(getDriver());
		UpdateDocumentForm updateDocumentForm = new UpdateDocumentForm(getDriver());


		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountLink(strAccount),"Verifying account ["+strAccount+"] link");

		TestReporter.logStep("Clicking account ["+strAccount+"] link");
		accounts.clickAccountLink(strAccount);

		TestReporter.assertTrue(accounts.verifyAccountPageIsLoaded(strAccount),"Verifying ["+strAccount+"] page is loaded");

		TestReporter.logStep("Clicking project ["+strProject+"] link");
		accounts.clickProjectLink(strProject);

		TestReporter.assertTrue(accounts.verifyProjectPageIsLoaded(strAccount,strProject),"Verifying ["+strProject+"] page is loaded");

		TestReporter.logStep("Checking if document already exists");
		if (accounts.verifyDocumentExists(docName)){
			TestReporter.logStep("Document exists, deleting");
			accounts.clickEditDocument(docName);
			updateDocumentForm.clickDelete();
		} else {
			TestReporter.logStep("Document does not exist");
		}

		TestReporter.logStep("Clicking Add Document");
		accounts.clickAddDocument();

		TestReporter.assertTrue(addDocumentForm.verifyFormLoaded(),"Verifying Add Document form loaded");

		TestReporter.logStep("Setting File to upload");
		addDocumentForm.setFile(filePath);

		TestReporter.logStep("Setting Document Type");
		addDocumentForm.selectDocumentType("SOW");

		TestReporter.logStep("Getting Document Name");
		docName = addDocumentForm.getDocumentName();

		TestReporter.logStep("Setting Total Budget");
		addDocumentForm.setTotalBudget(budget);

		TestReporter.logStep("Setting Total Hours");
		addDocumentForm.setTotalHours(hours);

		TestReporter.logStep("Setting 'Signed' to checked");
		addDocumentForm.checkSigned();

		TestReporter.logStep("Clicking Create Document");
		addDocumentForm.clickCreateDocument();

		TestReporter.assertTrue(accounts.verifyDocumentExists(docName),"Verifying new Document is on page");

		TestReporter.logStep("Navigating to BlueSource Reporting");
		header.navigateReporting();

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying BlueSource Reporting login page loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.assertTrue(reportingNavBar.verifyHomePageIsDisplayed(),"Verifying Reporting home page is displayed");

		TestReporter.logStep("Clicking Account Reports");
		reportingNavBar.clickAccountReports();

		TestReporter.logStep("Clicking Burn Down Data");
		reportingNavBar.clickBurnDownData();

		TestReporter.assertTrue(burnDownDataReportForm.verifyFormLoaded(), "Verifying Account Burn Down Report form loaded");

		TestReporter.logStep("Selecting [" + strAccount + "] for report");
		burnDownDataReportForm.selectAccount(strAccount);

		TestReporter.logStep("Clicking Generate Report");
		burnDownDataReportForm.clickGenerateReport();

		TestReporter.assertFalse(report.doesReportHaveLongDecimals(),"Verifying that the report doesn't have long decimals in any field");
	}
}