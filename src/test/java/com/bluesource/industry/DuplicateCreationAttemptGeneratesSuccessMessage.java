/**
 * This class tests the error message received when attempting to
 * create an industry with the same name as one already created.
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

public class DuplicateCreationAttemptGeneratesSuccessMessage extends WebBaseTest{
	
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
    public void testDuplicateCreationAttemptGeneratesSuccessMessage()
    {
    	String industryName=null;
    	
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
    	// Step 6 Select an existing Industry name to use from list
    	industryName = industries.getIndustryName();
    	String errorMessage="Industry "+industryName+" failed to be created. [[\"Name has already been taken\"]]";
    	// Step 7 Click 'Add New Industry' button
    	industries.clickAddNewIndustry();
    	// Step 8 Enter the industry name selected earlier. Click 'Create Industry' button
    	industries.setNewIndustryName(industryName);
    	industries.clickCreateIndustry();
    	// Step 9 Verify the validation message displays with a red background instead of green
    	TestReporter.assertEquals(industries.getErrorMessage(), errorMessage, "Verifying the error message shown is correct");
    	
    }
    
}