package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.Constants;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class ShowRateOnProjectShowPageVerify extends WebBaseTest {
	//*****************
	// Data Provider
	//*****************
	@DataProvider(name = "show_project_rate_verify", parallel = true)
	public Object[][]scenarions(){
		return new ExcelDataProvider(Constants.EXCEL_SHEETS + "/ShowRateOnProjectVerify.xlsx","Sheet1").getTestData();
	}

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
		testStart("Show_Rate_on_Project_Show_Page_Verify");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "show_project_rate_verify")
	public void showRateOnProjectShowPageVerify(String strScenario, String strAccount, String strProject, String strRole)
	{
		TestReporter.logScenario("Scenario: [" + strScenario + "] START");

		//Test Variables
		String rateFromProjectPage;

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(),"Verifying Login Page is loaded");

		TestReporter.logStep("Logging in to application as Account Viewer");
		loginPage.Login("ACCOUNT_VIEWER");

		TestReporter.logStep("Navigating to the Accounts page");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(),"Verifying Accounts Page is loaded");

		TestReporter.assertTrue(accounts.verifyAccountLink(strAccount),"Verifying [" + strAccount + "] link");

		TestReporter.logStep("Clicking link for [" + strAccount + "]");
		accounts.clickAccountLink(strAccount);

		TestReporter.assertTrue(accounts.verifyAccountPageIsLoaded(strAccount),"Verifying [" + strAccount + "] Page is loaded");

		TestReporter.logStep("Clicking [" + strProject + "] link in [" + strAccount + "] page");
		accounts.clickProjectLink(strProject);

		TestReporter.assertTrue(accounts.verifyProjectPageIsLoaded(strAccount,strProject),"Verifying [" + strProject + "] Page is loaded");

		TestReporter.logStep("Get and store [" + strRole + "] rate");
		rateFromProjectPage = accounts.getRoleRateFromProjectPage(strRole);

		TestReporter.assertNotEquals(rateFromProjectPage,"","Verifying valid rate returned from project page");

		TestReporter.logStep("Clicking [" + strRole + "] link");
		accounts.clickRoleLink(strRole);

		TestReporter.assertTrue(accounts.verifyRolePageIsLoaded(strAccount,strProject,strRole),"Verifying Role page is loaded");

		TestReporter.assertTrue(accounts.verifyRoleRate(rateFromProjectPage),
				"Verify Role rate from Project page exists on Role page in scenario [" + strScenario + "]");

		TestReporter.logScenario("Scenario [" + strScenario + "] END");
	}
}
