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

//		@BeforeMethod
//		@Parameters({ "runLocation", "browserUnderTest", "browserVersion",
//		"operatingSystem", "environment" })
//			public void setup(@Optional String runLocation, String browserUnderTest,
//				String browserVersion, String operatingSystem, String environment) {
//				setApplicationUnderTest("BLUESOURCE");
//				setBrowserUnderTest(browserUnderTest);
//				setBrowserVersion(browserVersion);
//				setOperatingSystem(operatingSystem);
//				setRunLocation(runLocation);
//				setEnvironment(environment);
//				setThreadDriver(true);
//				testStart("Add Role Type");
//		}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(groups = {"smoke"} )
	public void addNewRoleType() {

		String roleType = "Automation Tester";

		TestReporter.setDebugLevel(2);

		TestReporter.logScenario("Add Role Type");

		setPageURL("http://10.238.243.127/login");

		testStart("Add Role Type");

		LoginPage loginPage = new LoginPage(getDriver());
		Accounts accounts = new Accounts(getDriver());
		AddRoleType addNewRoleType = new AddRoleType(getDriver());
		
		//create new role type
		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "User is taken to login page");
		loginPage.LoginWithCredentials("company.admin", "anything");
		loginPage.check_login("company.admin");
		TestReporter.assertTrue(addNewRoleType.openRoleType(), "User is taken to the Listing Role Types page");
		TestReporter.assertTrue(addNewRoleType.clickAddNewRoleTypeButton(), "New Role Type page displays");
		addNewRoleType.createNewRole(roleType);
		TestReporter.assertTrue(addNewRoleType.verifyNewRole(roleType), "Green banner displays with role type created");
		
		//assign new role type
		accounts.click_accounts_tab("company.admin");
		accounts.clickFirstAccountLink();
		TestReporter.assertTrue(addNewRoleType.openFirstProject(), "First Project is clicked");
		TestReporter.assertTrue(addNewRoleType.clickNewRole(), "User is on project page");
		addNewRoleType.toggleBilling();
		TestReporter.assertTrue(addNewRoleType.assignNewRole(roleType), "Add Project Role info box displays");
		TestReporter.assertTrue(addNewRoleType.newRoleSelected(roleType), "	Checkpoint: Newly created role type displays in the dropdown");
	}

}
