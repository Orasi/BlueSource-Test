package com.bluesource.employees;

import com.orasi.utils.TestReporter;
import com.orasi.utils.date.DateTimeConversion;

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
	
	 @Test//(dataProvider = "login")
	 public void testDeactiveEmployeeFilledRoleEndDate()
	 {
		 String firstName = "Issue";
		 String lastName = "491";
		 String employeeName = firstName+" "+lastName;
		 
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
		 
		// Step 13 Click the Deactivate button.
//		 employeePage.clickDeactivate();
		 
		// Step 14 Click on the deactivated employee's name and verify that their status has changed to "Inactive"
		 
		// Step 15 Verify that the project end date is displayed as the current date.
		 
	} 
}