/**
 * Tests that an admin can require a nonbillable timesheet
 * from an employee, and that the timesheet won't
 * show up after unchecking the requirement
 * @author Andrew McGrail
 */
package com.bluesource.employees;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class EmployeesProjectTimesheetRequireNonbillableAdmin extends WebBaseTest{	
			
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
			testStart("Edit_Filled_Roles_from_Employee_Show_Page");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test//(dataProvider = "login")
	 public void testEmployeesProjectTimesheetRequireNonbillableAdmin()
	 {
		 String employeeUpdateMessage = "Employee updated successfully";
		 String confirmationMessage = "Reported Time for this week successfully logged.";
		 String employeeName = "No Project";
		 String employeePassword = "123";
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 // Step 1 Open the browser and navigate to "http://10.238.243.127".
		 // Step 2 Login as "company.admin".
		 loginPage.AdminLogin();
		 // Step 3 Click on 'Employees'.
		 header.navigateEmployees();
		 TestReporter.assertTrue(employees.verifyPageIsLoaded(), "Landed on Employees page");
		 // Step 4 Click on an Employee from the list that is NOT on a project and who has not submitted a timesheet this week or last week.
		 employees.employeeSearch(employeeName);
		 TestReporter.assertTrue(employees.verifyEmployeeSearchField(employeeName), "Searched for the employee "+employeeName);
		 employees.clickFirstName();
		 TestReporter.assertTrue(employeePage.verifyEmployeeName(employeeName), "Landed on "+employeeName+"'s page");
		 // Step 5 Click on the 'Edit' button inside the 'General Info' pane's header.
		 employeePage.editGeneralInfo();
		 TestReporter.assertTrue(employeePage.verifyEditButton(), "Edit General Modal has been brought up");
		 // Step 6 Select the 'Require Nonbillable Time Entry' checkbox.
		 employeePage.clickNonbillableTime();
		 // Step 7 Click the 'Update Employee' button.
		 employeePage.clickUpdateEmployee();
		 TestReporter.assertEquals(employeePage.checkSuccessMessage(), employeeUpdateMessage, "Employee was Updated to require Nonbillable Time Entry");
		 // Step 8 Logout of Bluesource.
		 header.navigateLogout();
		 TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Successfully logged out of Company.Admin");
		 // Step 9 Log back in as the Employee who was just updated.
		 loginPage.LoginWithCredentials(employeeName, employeePassword);
		 TestReporter.assertTrue(employeePage.verifyEmployeeName(employeeName), "Succefully logged in as "+employeeName);
		 // Step 10 Verify the user has a nonbillable timesheet listed.
		 TestReporter.assertTrue(employeePage.verifySubmitTimesheetButton(), "Verifying nonbillable timesheet is shown");
		 // Step 11 Verify the user is able to submit the timesheet.
		 employeePage.fillTimesheet();
		 employeePage.sendTimesheet();
		 TestReporter.assertEquals(employeePage.checkSuccessMessage(), confirmationMessage, "Confirming the timesheet was sent");
		 // Step 12 Logout of Bluesource and log back in as company.admin.
		 header.navigateLogout();
		 loginPage.AdminLogin();
		 // Step 13 Navigate back to an Employee's page who is not on a project and who has not submitted a timesheet for this week or last week.
		 header.navigateEmployees();
		 TestReporter.assertTrue(employees.verifyPageIsLoaded(), "Landed on Employees page");
		 employees.employeeSearch(employeeName);
		 TestReporter.assertTrue(employees.verifyEmployeeSearchField(employeeName), "Searched for the employee "+employeeName);
		 employees.clickFirstName();
		 TestReporter.assertTrue(employeePage.verifyEmployeeName(employeeName), "Landed on "+employeeName+"'s page");
		 // Step 14 Click the 'Edit' button inside the 'General Info' panel's header.
		 employeePage.editGeneralInfo();
		 TestReporter.assertTrue(employeePage.verifyEditButton(), "Edit General Modal has been brought up");
		 // Step 15 Click the 'Require Nonbillable Time Entry' checkbox.
		 employeePage.unclickNonbillableTime();
		 // Step 16 Click the 'Update Employee' button.
		 employeePage.clickUpdateEmployee();
		 TestReporter.assertEquals(employeePage.checkSuccessMessage(), employeeUpdateMessage, "Employee was Updated to no longer require Nonbillable Time Entry");
		 // Step 17 Logout of Bluesource and log back in as the Employee who was just updated.
		 header.navigateLogout();
		 loginPage.LoginWithCredentials(employeeName, employeePassword);
		 TestReporter.assertTrue(employeePage.verifyEmployeeName(employeeName), "Succefully logged in as "+employeeName);
		 // Step 18 Verify the user does NOT see a nonbillable timesheet.
		 TestReporter.assertTrue(employeePage.verifyNoTimeSheets(), "Verifying nonbillable timesheet is not shown");
	 }
}