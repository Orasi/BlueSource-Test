/**
 * This class tests Issue947_RemoveOrasiLogoFromReports
 * This test logs into BlueSource Reporting, chooses a company
 * 	and ensures the logo changed
 * @author Andrew McGrail
 */
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

public class RemoveOrasiLogoFromReports extends WebBaseTest {
	
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
    public void testRemoveOrasiLogoFromReports() {
    	Reporting reporting = new Reporting(getDriver());
    	
    	//Step 1 Open Browser
    	//Step 2 Navigate to Bluesource reporting
    	//Step 3 Login to Reporting
    	reporting.adminLogin();
    	TestReporter.logStep("Successfully logged into BlueSource Reporting as Admin");
    	//Step 4 Click on the Company icon, located at the top left of the page
    	reporting.clickLogo();
    	//Step 5 Choose one of the items from the list
    	reporting.clickCompany2();
    	TestReporter.logStep("Selected a Company");
    	TestReporter.assertTrue(reporting.checkLogo(), "Verifying the Logo appeared when choosing a company");
    	//Step 6 Click on the username, located at the top right of the page to log out
    	reporting.logout();
    	
    	    	
    }
}