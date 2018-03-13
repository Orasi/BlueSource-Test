/**
 * Tests Deactivate Employee_Inactive Employees_Issue462
 * This test checks when a manager of an inactive subordinate is also
 *  made inactive the subordinate loses the manager.
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

public class DeactivateEmployeeInactiveEmployees extends WebBaseTest{	
		
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
			testStart("DeactivateEmployeeInactiveEmployees");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testDeactivateEmployeeInactiveEmployees()
	 {
		 String firstName = "HasInactive";
		 String lastName = "Subordinates";
		 String fullName = firstName+" "+lastName;
		 String subordinateName =null;
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 // Step 1 Preconditions: Identify or create a user that has inactive subordinates
		 	// Employee: HasInactive Subordinates, Must be reset to Active + Having inactive subordinates each test run.
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
		 // Step 11 Verify the Deactivate button is displayed at the top right of the page.
		 TestReporter.assertTrue(employeePage.verifyDeactivateButton(), "The Deactivate Button is on the page");
		 // Step 12 "Verify the text under 'Automated Actions' is displayed as a list of the employee's inactive subordinates: ""(Employee) will have no manager."""
		 TestReporter.assertTrue(employeePage.verifyEmployeesLoseManagerMessage(), "Warning message for deactivating "+fullName+" is displayed, showing subordinates");
		 subordinateName = employeePage.getEmployeeLosingmanager();
		 // Step 13 Click the Deactivate button
		 employeePage.clickDeactivate();
		 TestReporter.assertTrue(employees.verifyEmployeeSearchExists(), fullName+" has been deactivated");
		 // Step 14 Click the OK button.
		 	// No longer relevant
		 // Step 15 Click on the name of the inactive subordinate previously listed.
		 employees.employeeSearch(subordinateName);
		 employees.selectEmployeeByName(subordinateName.substring(0, subordinateName.indexOf(' ')));
		 // Step 16 Verify that the employee has no manager listed.
		 TestReporter.assertTrue(employeePage.verifyManagerIsEmpty(), subordinateName+" has no manager selected");
	} 
}