/**
 * This tests Issue 491 Deactivate Employee_Filled Role End Date
 * The test chooses a preidentified employee on a project who is Active,
 *  verifies there is a warning message when deactivating the employee
 *  then verifies the end date for the project role is updated.
 *  @author Andrew McGrail
 */
package com.bluesource.employees;

import com.orasi.utils.TestReporter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.LoginPage;
import com.orasi.web.WebBaseTest;

public class DeactiveEmployeeFilledRoleEndDate extends WebBaseTest{	
		
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
			testStart("DeactiveEmployeeFilledRoleEndDate");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testDeactiveEmployeeFilledRoleEndDate()
	 {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		 LocalDate localDate = LocalDate.now();
		 String currentDate = dtf.format(localDate);
		 
		 // Variables to be Set
		 String firstName = "Issue";
		 String lastName = "491";
		 String employeeName = firstName+" "+lastName;
		 String deactiveMessage = "Employee successfully deactivated";
		 String accountName = "Account for Testing";
		 String projectName = "Project1";
		 String roleName = "Project Manager Onsite";
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		// Step 1 Preconditions: Identify or create a user that is assigned a role on a project.
		 	//	Employee: Issue 491
		// Step 2 Open browser.
		// Step 3 Navigate to http://10.238.242.236
		// Step 4 Enter a valid username of a user with one of the following roles: Company Admin, Department Admin, Department Head, Upper management
		// Step 5 Enter a valid password.
		// Step 6 Click the Login button or press Enter.
		 loginPage.AdminLogin();
		 TestReporter.assertTrue(employees.verifyPageIsLoaded(), "Successfully landed on the employees page");
		// Step 7 Click an employee name.
		 employees.employeeSearch(employeeName);
		 employees.selectEmployeeByName(lastName);
		 TestReporter.assertTrue(employeePage.verifyEmployeeName(employeeName), "Successfully landed on "+employeeName+"'s employee page");
		// Step 8 Click the Edit button in the General Info section.
		 employeePage.editGeneralInfo();
		 TestReporter.assertTrue(employeePage.verifyEditGeneralModal(), "Verifying the Edit General Modal is present");
		// Step 9 Verify the Deactivate Employee button is displayed at the bottom of the window.
		 TestReporter.assertTrue(employeePage.verifyDeactivateEmployee(), "The Deactivate Employee button is present");
		// Step 10 Click the Deactivate Employee button.
		 employeePage.clickDeactivateEmployee();
		// Step 11 Verify the Deactivate button is displayed at the top right of the page.
		 TestReporter.assertTrue(employeePage.verifyDeactivate(), "The Deactivate Employee button was pushed");
		// Step 12 Verify that project role end date is displayed with the text: "(Account) - (Project) - (Role) will be set to end XX/XX/XXXX."
		 TestReporter.assertTrue(employeePage.verifyDeactivateMessage(accountName, projectName, roleName, currentDate),
				 "Verified the page warns the end date of the project role will be changed to today's date");
		// Step 13 Click the Deactivate button.
		 employeePage.clickDeactivate();
		 TestReporter.assertTrue(employees.verifySuccessMessage(deactiveMessage), "Verified "+employeeName+" was deactivated");
		// Step 14 Click on the deactivated employee's name and verify that their status has changed to "Inactive"
		 employees.employeeSearch(employeeName);
		 employees.selectEmployeeByName(lastName);
		 TestReporter.assertTrue(employeePage.verifyEmployeeStatus("Inactive"), "Verified that "+employeeName+" has been set to Inactive");
		// Step 15 Verify that the project end date is displayed as the current date.
		 TestReporter.assertTrue(employeePage.verifyFirstRoleEndDate(currentDate), "Verified the role end date has been updated");
	} 
}