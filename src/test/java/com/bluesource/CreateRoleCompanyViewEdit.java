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

public class CreateRoleCompanyViewEdit extends WebBaseTest{

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
		testStart("CreateRoleCompanyViewEdit");
	}
    
    @AfterMethod
    public void close(ITestContext testResults){
    	endTest("TestAlert", testResults);
    }
    
    @Test
    public void testCreateRoleCompanyViewEdit(){
    	
    	String username = "company admin";
    	
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	Employees employee = new Employees(getDriver());
    	EmployeePage employeePage = new EmployeePage(getDriver());
    	
    	//Steps 1-5. Navigate to http://10.238.243.127 and login
    	loginPage.AdminLogin();
    	TestReporter.logStep("Successfully logged in as Admin to BlueSource QA");
    	//Step 6. Type in the 'Logged in user name' in the 'Search here...' textbox
    	header.navigateEmployees();
    	employee.employeeSearch(username);
    	TestReporter.logStep("The name "+username+" has been searched");
    	//Step 7. Click the first or last name of any employee that has the logged in username in the 'Supervisor' column of the Employee's Table.
    	employee.checkSupervisors(username);
    	TestReporter.logStep("Accessing employee page who has "+username+" as a supervisor");
    	//Step 8. Click 'Edit' in the 'General Info'
    	employeePage.editGeneralInfo();
    	TestReporter.logStep("Edit general info modal has been accessed");
    	//Step 9. Click the 'x' button in the top right corner of the modal
    	employeePage.clickCloseModal();
    	TestReporter.logStep("Edit general info modal has been closed");
    	//Step 10. Click 'Logout'
    	header.navigateLogout();
    	TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Verified test has logged out of BlueSource QA");
    }
}