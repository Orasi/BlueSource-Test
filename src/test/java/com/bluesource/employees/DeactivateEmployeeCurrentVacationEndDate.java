/**
 * Tests Deactivate Employee_Current Vacation End Date_Issue462
 * This test checks when an employee with a vacation scheduled
 *  during the time you attempt to deactivate, the system will
 *  warn about the time off and deactivating the employee will
 *  change the end date of their time off to the current date.
 * @author Andrew McGrail
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

public class DeactivateEmployeeCurrentVacationEndDate extends WebBaseTest{	
		
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
			testStart("DeactivateEmployeeCurrentVacationEndDate");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testDeactivateEmployeeCurrentVacationEndDate()
	 {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
		 LocalDate localDate = LocalDate.now();
		 
		 String firstName = "Current";
		 String lastName = "Vacation";
		 String fullName = firstName+" "+lastName;
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 // Step 1 Preconditions: Identify or create a user that has a vacation request currently in progress.
		 	// Employee: Current Vacation, Must be reset to Active + Having a current vacation request each test run.
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
		 // Step 12 Click the Deactivate button.
		 employeePage.clickDeactivate();
		 TestReporter.assertTrue(employees.verifyEmployeeSearchExists(), fullName+" has been deactivated");
		 // Step 13 Click the OK button.
		 		// No longer relevant
		 // Step 14 Click on the deactivated employee's name and verify that their status has changed to "Inactive"
		 employees.employeeSearch(fullName);
		 employees.selectEmployeeByName(firstName);
		 TestReporter.assertTrue(employeePage.verifyEmployeeStatus("Inactive"), "Successfully landed on "+fullName+"'s employee page and verified they are Inactive");
		 // Step 15 Click the Edit button in the Time Off Info section.
		 employeePage.clickManageTimeOff();
		 // Step 16 Verify that the current vacation request has an end date as the current date.
		 TestReporter.assertTrue(employeePage.verifyFirstTimeOffEndDate(dtf.format(localDate)), "Verified the Time Off now has an end date of "+dtf.format(localDate));
	} 
}