/**
 * Tests that you cannot update a projects role to a date
 *  that doesn't fall inside the projects dates.
 * @author: Andrew McGrail
 */

package com.bluesource.rates;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class RateUpdateWithProjectTime extends WebBaseTest{	
		
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
			testStart("testRateUpdateWithProjectTime");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testRateUpdateWithProjectTime()
	 {
		 String errorChecker = "Role rate failed to be updated. [[\"Role budget start date must"
		 		+ " be between project start date";
		 String accountName = "16JGPcNUiG";
		 String startDate = "05052020";				//In Format of MMDDYYYY
		 String endDate = "06062020";				//In Format of MMDDYYYY
		 String rate = "15.50";
		 String comment = "This is a comment!";
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Accounts account = new Accounts(getDriver());
		 
		 // Steps 1-5. Log in to http://10.238.243.127 as company.admin
		 loginPage.AdminLogin();
		 // Step 6 Click on a account name that has a project that has a rate
		 header.navigateAccounts();
		 TestReporter.assertTrue(account.verifyAddButtonIsVisible(), "Landed on Accounts page.");
		 account.clickAccountLink(accountName);
		 TestReporter.assertTrue(account.verifyAccountPage(), "Landed on the account page for "+accountName);
		 // Step 7 Click on Project name located in the table
		 account.clickFirstProject();
		 TestReporter.assertTrue(account.verifyProjectPage(), "Landed on Project page");
		 // Step 8 Scroll down the page to the Project Roles section of the table
		 // Step 9 Click on the Role name in the table
		 account.clickFirstRole();
		 TestReporter.assertTrue(account.verifyRolePage(), "Landed on first Role page");
		 // Step 10 Scroll down the page to the Rates section
		 // Step 11 Click on the 'pencil' icon
		 account.clickEditFirstRate();
		 TestReporter.assertTrue(account.verifyRoleEditModal(), "Opened Role Edit modal");
		 // Step 12 Click the checkbox titled 'Start date' if checked
		 account.uncheckStartDate();
		 TestReporter.assertTrue(account.verifyStartDateEditable(), "Inherit Start date is unchecked");
		 // Step 13 If checkbox is deselected, click in the textbox area to enter date manually. 
		 // (Select a date completely outside of the original bounds ie project was originally 8/14 - 8/18 and was changed to 8/21 - 8/25)
		 account.enterStartDate(startDate);
		 TestReporter.assertTrue(account.verifyStartDateContent(startDate), "Verified the Start Date");
		 // Step 14 Click the checkbox titled 'End date' if checked
		 account.uncheckEndDate();
		 TestReporter.assertTrue(account.verifyEndDateEditable(), "Inherit End date is unchecked");
		 // Step 15 If checkbox is deselected, click in the textbox area to enter date manually
		 // (Select a date completely outside of the original bounds ie project was originally 8/14 - 8/18 and was changed to 8/21 - 8/25)
		 account.enterEndDate(endDate);
		 TestReporter.assertTrue(account.verifyEndDateContent(endDate), "Verified the End Date");
		 // Step 16 Click in textbox under 'Rate'
		 // Step 17 Enter numeric value
		 account.enterRate(rate);
		 TestReporter.assertTrue(account.verifyRate(rate), "Verified the Rate field says "+rate);
		 // Step 18 Click in the textbox under 'Comments'
		 // Step 19 Enter desired text in the area
		 account.enterComment(comment);
		 TestReporter.assertTrue(account.verifyTextCommentContent(comment), "Verified the Comment field says "+comment);
		 // Step 20 Click 'Update Role budget'
		 account.clickUpdateComment();
		 // Step 21 Verify messages display that indicate that dates were not changed so it no longer fails silently.
		 // (ie Project updated, but some children failed to update - project updated - failed to update start and end date for role)
		 TestReporter.assertEquals(account.checkError(), errorChecker, "Checking the beginning of the date failed error message");
		 // Step 22 Click 'Logout' button
		 header.navigateLogout();
		 TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Logged Out of Blue Source"); 
	 }
}