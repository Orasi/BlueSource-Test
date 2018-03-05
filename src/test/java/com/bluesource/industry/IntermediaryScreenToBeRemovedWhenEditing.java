/**
 * This class tests that there is no intermediary screen when
 * editting an industries title
 * @author Andrew McGrail
 */
package com.bluesource.industry;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Header;
import com.orasi.bluesource.Industries;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class IntermediaryScreenToBeRemovedWhenEditing extends WebBaseTest{
	
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
    public void testIntermediaryScreenToBeRemovedWhenEditing()
    {
    	String preEditName=null;
    	String successMessage=null;
    	
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	Industries industries = new Industries(getDriver());
    	
    	// Step 1 Open browser
    	// Step 2 Navigate to testing URL http://10.238.243.127
    	// Step 3 Enter valid username and password credentials to login
    	loginPage.AdminLogin();
    	TestReporter.logStep("Successfully logged into BlueSource QA as admin");
    	// Step 4 Select 'Admin' dropdown arrow
    	// Step 5 Select 'Industries'
    	header.navigateIndustries();
    	TestReporter.logStep("Successfully landed on Industries page");
    	// Step 6 Select pencil icon for one of the existing Industry names
    	industries.clickEditFirstIndustry();
    	// Step 7 Edit the industry name. Ensure original value is noted to revert at end of test
    	preEditName=industries.getPreEditName();
    	industries.setNewIndustryName(preEditName+"2");
    	successMessage="Industry "+preEditName+"2 updated";
    	TestReporter.logStep("Successfully landed on the Industry edit page");
    	// Step 8 Click 'Update Industry' button
    	industries.clickUpdateIndustry();
    	// Step 9 Verify no intermediary screen displays between clicking the 'Update Industry button, and seeing validation message
    	TestReporter.assertEquals(industries.getErrorMessage(), successMessage, "Verifying the Industries page is displayed with a success message");
    	// Step 10 Repeat edit steps to return Industry name to original value
    	industries.clickEditLastIndustry();
    	industries.setNewIndustryName(preEditName);
    	industries.clickUpdateIndustry();
    	successMessage="Industry "+preEditName+" updated";
    	TestReporter.assertEquals(industries.getErrorMessage(), successMessage, "Verifying the Industries page is displayed with the old Industry name success message");
    	
    }
    
}