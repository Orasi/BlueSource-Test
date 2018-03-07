package com.bluesource.Admin;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.AddRoleType;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class Add_Role_Type extends WebBaseTest {    
	
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
	public void addNewRoleType() {

		TestReporter.setDebugLevel(2);

		TestReporter.logScenario("Add Role Type");

		setPageURL("http://10.238.243.127/login");

		testStart("Add Role Type");

		LoginPage loginPage = new LoginPage(getDriver());
		Accounts accounts = new Accounts(getDriver());
		loginPage.LoginWithCredentials("company.admin", "anything");

		AddRoleType addNewRoleType = new AddRoleType(getDriver());
		addNewRoleType.openRoleType();
		addNewRoleType.click_AddNewRoleTypeButton();
		addNewRoleType.createNewRole("Automation Tester");

		if (addNewRoleType.verifyNewRole() == true) {
			accounts.click_accounts_tab("company.admin");

			accounts.clickFirstAccountLink();
			addNewRoleType.openFirstProject();
			addNewRoleType.clickNewRole();
			addNewRoleType.toggleBilling();
			addNewRoleType.assignNewRole();
		}
	}

}
