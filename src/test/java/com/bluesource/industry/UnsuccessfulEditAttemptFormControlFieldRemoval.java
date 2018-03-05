 /**
  * This class tests that when attempting to rename an industry with
  * the name of another existing industry there is an error message
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

 public class UnsuccessfulEditAttemptFormControlFieldRemoval extends WebBaseTest{
 	
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
     public void testUnsuccessfulEditAttemptFormControlFieldRemoval()
     {
     	String firstIndustryName=null;
     	String errorMessage=null;
     	
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
     	// Step 6 View the list and note a value for an industry name to use in later steps
     	industries.clickEditThirdIndustry();
     	firstIndustryName=industries.getPreEditName();
     	header.navigateIndustries();
     	TestReporter.logStep("Stored Industry name "+firstIndustryName+" for future use.");
     	// Step 7 Select pencil icon for a different one of the existing Industry names
     	industries.clickEditLastIndustry();
     	TestReporter.logStep("Clicked the edit button for the last Industry listed (different than stored name)");
     	// Step 8 Write over existing value in 'Name' field with the other Industry name noted in earlier step
     	industries.setNewIndustryName(firstIndustryName);    	
     	// Step 9 Click 'Update Industry' button
     	industries.clickUpdateIndustry();
     	// Step 10 Verify validation message displays and no error form/control message displays on 'Name' field
     	errorMessage="Industry "+firstIndustryName+" failed to be updated. [[\"Name has already been taken\"]]";
     	TestReporter.assertEquals(industries.getErrorMessage(), errorMessage, "Verifying the Industries page is displayed with the correct error message");
     }
     
 }