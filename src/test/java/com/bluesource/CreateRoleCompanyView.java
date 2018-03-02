package com.bluesource;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Employees;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class CreateRoleCompanyView extends WebBaseTest{

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
		testStart("CreateRoleCompanyView");
	}
    
    @AfterMethod
    public void close(ITestContext testResults){
    	endTest("TestAlert", testResults);
    }
    
    @Test
    public void testCreateRoleCompanyView(){
    	
    	String firstName= "Change";
    	String lastName= "This";
    	String successMessage= "Employee successfully created";
    	
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	Employees employee = new Employees(getDriver());
    	
    	//Steps 1-5. Navigate to http://10.238.243.127 and login
    	loginPage.AdminLogin();
    	TestReporter.logStep("Successfully logged in as Admin to BlueSource QA");
    	//Step 6. Click the 'Add' button of the 'Employees Table' page
    	header.navigateEmployees();
    	employee.clickAddEmployee();
    	TestReporter.logStep("Opened the new employee form");
    	//Step 7. Enter a username in the format of firstname.lastname into the 'username' textbox
    	//Step 8. Enter a string of text in the 'first name' textbox
    	//Step 9. Enter a string of text in the 'last name' textbox
    	//Step 10. Click the PTO Permission dropdown and select 'Company View' role
    	//Step 11. Click 'Create Employee' button
    	employee.setPTOPermission("Company View");
    	employee.addEmployeeModal(firstName+"."+lastName,firstName,lastName);
    	TestReporter.assertEquals(employee.getSuccessMessage(), successMessage, "Verifying the employee added success message");
    	//Step 12. Click 'Logout'
    	header.navigateLogout();
    	TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Verified test has logged out of BlueSource QA");
    }
}