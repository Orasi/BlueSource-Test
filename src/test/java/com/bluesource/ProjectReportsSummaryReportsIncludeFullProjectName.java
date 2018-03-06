/**
 * This class tests Issue 887 - Project Reports Summary - Reports - Include Full Project Name in Burn Down Reports
 * Creates a Project BurnDown Report and ensures its validity
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

public class ProjectReportsSummaryReportsIncludeFullProjectName extends WebBaseTest {

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
    public void testProjectReportsSummaryReportsIncludeFullProjectName() {
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
    	// Step 6 Click 'Projects Reports' dropdown
    	reporting.clickProjectsReports();
    	// Step 7 Click 'Burn Down'
    	reporting.clickBurnDown();
    	TestReporter.logStep("Opened Projects - BurnDown Report modal");
    	// Step 8 Select the 'summary' raido button if not already selected
    	reporting.clickSummary();
    	// Step 9 Select an account that has a project with a long name
    	// Step 10 Select Project
    	FirstAccountName=reporting.chooseAccount();
    	TestReporter.logStep("Chose the Account: "+FirstAccountName);
    	// Step 11 Click 'Generate report'
    	reporting.clickProjectsGenerateReport();
    	// Step 12 	Verify the full project name displays in the report
    	FirstReportName=reporting.getReportProjectAccountName();
    	TestReporter.assertEquals(FirstAccountName, FirstReportName, "Verifying the Account name's for the report and the requested are the same");
    	//Step 13 Click on the 'Welcome' located at the top right of page
    	reporting.logout();
    }
}