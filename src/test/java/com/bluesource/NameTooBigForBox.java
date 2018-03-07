package com.bluesource;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Reporting;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class NameTooBigForBox extends WebBaseTest {
	
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
    
    @Test
    public void testNameTooBigForBox() {
    	String employeeName = "Thisisforntesting thelongname1";
    	
    	Reporting reporting = new Reporting(getDriver());
    	
    	//Step 1 Open browser.
    	//Step 2 Navigate to http://10.238.242.236/reporting/login
    	//Step 3 Input a valid username into the 'Username' textbox.
    	//Step 4 Input a valid password into the 'Password' textbox.
    	//Step 5 Click the 'Login' button or press Enter.
    	reporting.adminLogin();
    	TestReporter.logStep("Successfully logged in as Admin to BlueSource Reporting.");
    	//Step 6 Click 'Employee Reports' from the Navigation bar.
    	//Step 7 Select the 'Time by Projects' under Employee Reports.
    	reporting.clickTimeByProject();
    	TestReporter.logStep("Navigated to the Employee Reports for Time by Project");
    	//Step 8 Select employee Thisisforntesting thelongname1
    	reporting.searchEmployee(employeeName);
    	TestReporter.logStep("Searched Employee Reports by Projects for the name "+employeeName);
    	//Step 9 Verify the employee name fits inside the box.
    	TestReporter.assertTrue(reporting.verifyFullNameShowing(employeeName), "Verifying the requested name is shown in full on the report.");
    }

}
