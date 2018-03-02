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

public class EmployeesViewInactiveDate extends WebBaseTest{	
				
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
			testStart("EmployeesViewInactiveDate");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testEmployeesViewInactiveDate() {
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 MessageCenter messageCenter = new MessageCenter(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 
		//Steps 1-5. Login to BlueSource QA as admin or highest
		 loginPage.AdminLogin();
		 //Step 6. Click on a name of an employee that have been marked as inactive (ex. user4.base or user6.base)
		 header.navigateEmployees();
		 employees.select_employee(employees.findFirstInactiveInTable(), 1);
		 //Step 7. Click 'Edit' button in the 'General Info' section
		 employeePage.editGeneralInfo();
		 //Step 8. Verify that 'Inactive Date' has a date present in the box.
		 
		 //Step 9. Click the 'x' button in the top right corner.
		 
		 //Step 10. Click 'Logout'
		 header.navigateLogout();
		 TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Verifying successful logout");
	 }
}