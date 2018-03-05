/**
 * Test for adding an alternative email to a contractor.
 * @author: Andrew McGrail
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

public class EmployeesAddAlternativeEmail extends WebBaseTest{	
				
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
			testStart("testEmployeesAddAlternativeEmail");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testEmployeesAddAlternativeEmail()
	 {
		 String AltEmail = "AltEmail@aol.com";
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 // Steps 1&2 Open browser navigate to http://10.238.243.127
		 // Step 3 Input username of a Department Head or Admin or higher
		 loginPage.AdminLogin();
		 header.navigateEmployees();
		 // Step 4 Click on the first or last name of a contractor.
		 employees.selectEmployeeFirstname(employees.findFirstContractorInTable(), 1);
		 TestReporter.logStep("Landed on the first contractor's page");
		 // Step 5 If applicable, click the 'x' button in the Message Center pop up modal.
		 // Step 6 Click 'Edit' in the 'General Info' section
		 employeePage.editGeneralInfo();
		 // Step 7 input an arbitrary email into the 'Alternate Email' text box
		 employeePage.sendAltEmail(AltEmail);
		 TestReporter.assertEquals(AltEmail, employeePage.checkAltEmail(), "Verifying the Alternate email is set to the desired text");
		 // Step 8 Click 'Update Employee'
		 employeePage.clickUpdateEmployee();
		 TestReporter.logStep("Employee has been updated.");
		 // Step 9 Click 'Logout'
		 header.navigateLogout();
		 TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Verified successul logout");
	 }
}