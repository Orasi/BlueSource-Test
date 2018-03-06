/**
 * Test for Issue 859 - Add Daily Total Hours on Time Report Screen
 * Fills in an employees timesheet and checks the page updates reflecting that
 * @author Andrew McGrail
 */

package com.bluesource;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class AddDailyTotalHoursOnTimeReportScreen extends WebBaseTest {

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
    public void testAddDailyTotalHoursOnTimeReportScreen() {
    	Header header = new Header(getDriver());
    	LoginPage loginPage = new LoginPage(getDriver());
    	
    	// Step 1 Open browser.
    	// Step 2 Navigate to http://10.238.243.127
    	// Step 3 Input valid username into the 'Username' textbox of employee that has an open timesheet (not submitted).
    	// Step 4 Input valid password into the 'Password' textbox.
    	// Step 5 Click the 'Login' button or press Enter.
    	loginPage.LoginWithCredentials("this test", "123");
    	TestReporter.logStep("Successfully logged into BlueSource");
    	// Step 6 Enter time for various days including other rows such as bench or training.
    	loginPage.clickTimesheetDropdown();
    	loginPage.setTimesheetDropdown("Onboarding");
    	loginPage.setTimesheetHours();
    	TestReporter.logStep("Hours have been set in the timesheet");
    	// Step 7 Verify the daily totals display on the timesheet at the bottom.
    	TestReporter.assertTrue(loginPage.checkTimesheetHours(), "Verifying the hours entered in the timesheet appear below it.");
    	// Step 8 Click 'Logout' button.
    	header.navigateLogout();
    	TestReporter.logStep("Successfully logged out of BlueSource");
    }
}