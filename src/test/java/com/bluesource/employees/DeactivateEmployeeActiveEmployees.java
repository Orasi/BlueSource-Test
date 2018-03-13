/**
 * Tests Deactivate Employee_Active Employees_Issue462 & 940
 * This test checks when a manager of an active subordinate is 
 *  made inactive the system requires a manual change of manager
 *  and disallows the deactivation
 * @author Andrew McGrail
 */

package com.bluesource.employees;

import com.orasi.utils.TestReporter;

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

public class DeactivateEmployeeActiveEmployees extends WebBaseTest{	
		
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
			testStart("DeactivateEmployeeActiveEmployees");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testDeactivateEmployeeActiveEmployees()
	 {
		 String firstName = "HasActive";
		 String lastName = "Subordinates";
		 String fullName = firstName+" "+lastName;
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 // Step 1 Preconditions: Identify or create a user that has active and inactive subordinates
		 	// Employee: HasActive Subordinates
		 // Step 2 Open browser.
		 // Step 3 Navigate to http://10.238.242.236
		 // Step 4 Enter a valid username of a user with one of the following roles: Company Admin, Department Admin, department Head, Upper management
		 // Step 5 Enter a valid password.
		 // Step 6 Click the Login button or press Enter.
		 loginPage.AdminLogin();
		 // Step 7 Click an employee name.
		 employees.employeeSearch(fullName);
		 employees.selectEmployeeByName(firstName);
		 TestReporter.assertTrue(employeePage.verifyEmployeePage(fullName), "Successfully landed on "+fullName+"'s employee page");
		 // Step 8 Click the Edit button in the General Info section.
		 employeePage.editGeneralInfo();
		 TestReporter.assertTrue(employeePage.verifyDeactivateEmployeesButton(), fullName+"'s edit general modal has been brought up");
		 // Step 9 Verify the Deactivate Employee button is displayed at the bottom of the window.
		 TestReporter.assertTrue(employeePage.verifyDeactivateEmployeesButton(), "The edit general modal has a Deactivate Employee Button");
		 // Step 10 Click the Deactivate Employee button.
		 employeePage.clickDeactivateEmployee();
		 TestReporter.assertTrue(employeePage.verifyDeactivateButton(), "The Deactivate Employee Button was pushed");
		 // Step 11 Verify the Deactivate button is displayed and greyed out at the top right of the page.
		 TestReporter.assertTrue(employeePage.verifyInactiveDeactivateButton(), "The Deactivate Button is on the page and cannot be interacted with");
		 // Step 12 Verify the text under 'Manual Actions' is displayed as a list of the employee's active subordinates: "(Employee) must be reassigned to another manager."
		 TestReporter.assertTrue(employeePage.verifyActiveDeactivateMessage(), "Verified the manual actions warning message");
		 // Step 13 Hover over the 'Deactivate' button and verify there is a message you cannot deactivate employee until all manual actions are resolved.
		 TestReporter.assertTrue(employeePage.verifyDeactiveButtonMessage(), "The deactivate button displas a message when hovered over");
	} 
}