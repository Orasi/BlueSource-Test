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
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataHelpers.personFactory.Person;
import com.orasi.web.WebBaseTest;

public class EmployeesMarkingInactive extends WebBaseTest{	
				
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
			testStart("EmployeesMarkingInactive");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testEmployeesMarkingInactive() {
		 
		 String successMessage="Employee successfully deactivated";
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 
		 //Steps 1-5. Login to BlueSource QA as admin or highest
		 loginPage.AdminLogin();
		 //Step 6. Click on the first or last name of an employee in the employee table.
		 header.navigateEmployees();
		 employees.select_employee(employees.findFirstActiveInTable(), 1);
		 TestReporter.logStep("Reached page for an employee not marked as inactive");
		 //Step 7. Click the 'Edit' button in the 'General Info' section
		 employeePage.editGeneralInfo();
		 //Step 8. Click the red 'Deactivate Employee' button in the lower left corner
		 employeePage.clickDeactivateEmployee();
		 //Step 9. Click the red 'Deactivate' button in the top right corner
		 employeePage.clickDeactivate();
		 //Step 10. Click 'OK'
		 TestReporter.assertEquals(employees.getSuccessMessage(), successMessage, "Verifying the employee was successfully made inactive.");
		 //Step 11. Click 'Update Employee' (No longer needed through the system)
		 //Step 12. Click Logout
		 header.navigateLogout();
		 TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Verifying successful logout");
	 }
}