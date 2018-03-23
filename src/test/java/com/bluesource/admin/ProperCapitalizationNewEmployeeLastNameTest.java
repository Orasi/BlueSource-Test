package com.bluesource.admin;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class ProperCapitalizationNewEmployeeLastNameTest extends WebBaseTest{
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
		testStart("");
	}
    
    @AfterMethod
    public void close(ITestContext testResults){
    	endTest("TestAlert", testResults);
    }
    
    @Test(groups = {"smoke"} )
    public void testLastName() {
    	TestReporter.logStep("Login to application as an employee with Admin permission");
    	LoginPage loginPage = new LoginPage(getDriver());
    	loginPage.Login("ADMIN");
    	//McNeil case
    	TestReporter.logStep("Click on Add button from Employee page");
    	EmployeePage employeePage = new EmployeePage(getDriver());
    	employeePage.clickAddEmployee();
    	TestReporter.logStep("Add employee using the McNeil case");
    	//random number for a first name
    	String fName = ""+ (int)(Math.random()*500);
    	String lName = "McNeil";
    	employeePage.fillEmployeeInfo(fName, lName);
    	TestReporter.logStep("Use search bar to look for the recently created employee");
    	TestReporter.assertTrue(employeePage.verifyFoundEmployee(fName, lName),"Verify that the employee's last name is properly capitalized");
    	//James-Drew case
    	TestReporter.logStep("Navigate back to the Employees landing page");
    	employeePage.naviagateBackToEmployees();
    	TestReporter.logStep("Click on Add button from Employee page");
    	employeePage.clickAddEmployee();	
    	TestReporter.logStep("Add employee using the James-Drew case");
    	String lName2 = "James-Drew";//same first name
    	employeePage.fillEmployeeInfo(fName, lName2);
    	TestReporter.logStep("Use search bar to look for the recently created employee");
    	TestReporter.assertTrue(employeePage.verifyFoundEmployee(fName, lName2),"Verify that the employee's last name is properly capitalized");
    	
    }
}
