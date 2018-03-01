package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.EditProjectForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class Add_QSF_Number_to_all_Sub_Projects extends WebBaseTest {
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
		testStart("");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test
	public void add_QSF_Number_to_all_Sub_Projects(){
		String strAccount = "AccountOnlySubs1";
		String strProject = "Project1";
		String strSubProject = "SubProject";
		String strSOW;

		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		EditProjectForm editProjectForm = new EditProjectForm(getDriver());

		loginPage.AdminLogin();

		header.navigateAccounts();

		accounts.clickAccountLink(strAccount);

		accounts.clickProjectLink(strProject);

		accounts.editProject();

		editProjectForm.testEditSOWNumber();

		editProjectForm.testSetComments();

		editProjectForm.clickButtonSubmit();

		strSOW = accounts.getProjectSOW();

		for (String sow:accounts.getAllSubProjectSOWs()){
			TestReporter.assertEquals(strSOW,sow,"Checking sub project SOW's");
		}
	}
}
