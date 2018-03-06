/**
 * This class tests Issue 888 - Create BurnDown report w/o summary
 * Creates a BurnDown Report and ensures its validity
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

public class CreateBurnDownReportNoSummary extends WebBaseTest {

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
    public void testCreateBurnDownReportNoSummary() {
    	String FirstAccountName=null;
    	String FirstReportName=null;
    	
    	Reporting reporting = new Reporting(getDriver());
    	    	
    	// Step 1 Open browser.
    	// Step 2 Navigate to http://10.238.243.127:8080/reporting/login
    	// Step 3 Input valid username into the 'Username' textbox ie company.admin
    	// Step 4 Input valid password into the 'Password' textbox.
    	// Step 5 Click the 'Login' button or press Enter.
    	reporting.navigateReports();
    	reporting.adminLogin();
    	TestReporter.logStep("Logged into 'http://10.238.243.127:8080/reporting/login' as Admin");
    	// Step 6 Click 'Account Reports' dropdown
    	reporting.clickAccountReports();
    	// Step 7 Click 'Burn Down Data'
    	reporting.clickBurnDownData();
    	TestReporter.logStep("Opened BurnDown Data Report modal");
    	// Step 8 Select the 'project' raido button if not already selected
    	reporting.clickProjectButton();
    	// Step 9 Select an account that has a project with a long name
    	reporting.clickFirstAccountName();
    	FirstAccountName=reporting.getFirstAccountName();
    	// Step 10 Click 'Generate report'
    	reporting.clickGenerateReport();
    	TestReporter.logStep("Generated BurnDown Report");
    	// Step 11 Verify there is 1 line pr full project name and information displays correctly in the report
    	FirstReportName=reporting.getReportAccountName();
    	TestReporter.assertEquals(FirstAccountName, FirstReportName, "Verifying the Account name's for the report and the requested are the same");
    	// Step 12 	Click on the 'Welcome' located at the top right of page
    	reporting.logout();
    	
    	
    	
    }

}
