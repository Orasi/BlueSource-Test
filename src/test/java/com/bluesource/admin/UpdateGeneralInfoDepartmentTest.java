package com.bluesource.admin;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class UpdateGeneralInfoDepartmentTest extends WebBaseTest {
	@BeforeMethod
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion", "operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest, String browserVersion,
			String operatingSystem, String environment) {
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
	public void close(ITestContext testResults) {
		endTest("TestAlert", testResults);
	}

	@Test(groups = { "smoke" })
	public void testDepartmentChange() {
		TestReporter.logStep("Login as an admin");
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.Login("ADMIN");
		TestReporter.logStep("Close 'Message Center' popup if needed");
		MessageCenter messageCenter = new MessageCenter(getDriver());
		messageCenter.closeMessageCenter();
		TestReporter.logStep("Click on an employee from the table");
		EmployeePage employeePage = new EmployeePage(getDriver());
		employeePage.clickFirstEmployee();
		TestReporter.logStep("Click on the 'Edit' button from the 'General Info' section");
		employeePage.editGeneralInfo();
		TestReporter.logStep("Select the 'Department1' option from the department dropdown");
		employeePage.selectDepartment("Department1");
		TestReporter.logStep("Click the 'Update Employee' button");
		employeePage.clickUpdateEmployee();
		TestReporter.logStep("Verify the alert for 'Employee successfully updated.' is shown");
		TestReporter.assertTrue(employeePage.verifyUpdateAlert(), "Verify the alert is shown");
		TestReporter.logStep("Change the department to another department, and update the employee again");
		employeePage.editGeneralInfo();
		employeePage.selectDepartment("Department2");
		employeePage.clickUpdateEmployee();
		TestReporter.logStep("Verify the alert for 'Employee successfully updated' is shown again");

	}
}
