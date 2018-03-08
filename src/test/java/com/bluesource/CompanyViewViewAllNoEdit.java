/**
 * This test logs into a user with Company View permission then
 * checks an employee to ensure no 'edit general' button is shown
 * @author Andrew McGrail
 */
package com.bluesource;

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

public class CompanyViewViewAllNoEdit extends WebBaseTest{

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
		testStart("CompanyViewViewAllNoEdit");
	}
    
    @AfterMethod
    public void close(ITestContext testResults){
    	endTest("TestAlert", testResults);
    }
    
    @Test
    public void testCompanyViewViewAllNoEdit(){
    	
    	String username = "change.this";
    	
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	Employees employee = new Employees(getDriver());
    	EmployeePage employeePage = new EmployeePage(getDriver());
    	
    	//Steps 1-5. Navigate to http://10.238.243.127 and login with a user who has Company View
    	loginPage.LoginWithCredentials(username, "123");
    	TestReporter.logStep("Successfully logged in as "+username+" to BlueSource QA");
    	//Step 6. Click on the first or last name of an employee in the 'Employees Table'.
    	header.navigateEmployees();
    	TestReporter.assertTrue(employee.verifyPageIsLoaded(), "Landed on employee page");
    	employee.clickFirstName();
    	TestReporter.logStep("Clicked the first name of the first employee listed.");
    	//Step 7. Verify there is no 'Edit' button present in the 'General Info' section
    	TestReporter.assertFalse(employeePage.verifyEditButton(), "Verifying the Edit button beside 'General Info' is not present.");
    	//Step 8. Click 'Logout'
    	header.navigateLogout();
    	TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Verified test has logged out of BlueSource QA");
    }
}