/*
 * Test to make sure inactive users do not show up while
 * selecting an employee's manager.
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
	
	 @Test//(dataProvider = "login")
	 public void testRateUpdateWithProjectTime()
	 {
		 String errorChecker = "Role rate failed to be updated. [[\"Role budget start date must"
		 		+ " be between project start date";
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 Accounts account = new Accounts(getDriver());
		 
		 // Steps 1-5. Log in to http://10.238.243.127 as company.admin
		 loginPage.AdminLogin();
		 // Step 6 Click on a account name that has a project that has a rate
		 header.navigateAccounts();
		 account.clickAccountLink("16JGPcNUiG");
		 // Step 7 Click on Project name located in the table
		 account.clickFirstProject();
		 // Step 8 Scroll down the page to the Project Roles section of the table
		 // Step 9 Click on the Role name in the table
		 account.clickFirstRole();
		 // Step 10 Scroll down the page to the Rates section
		 // Step 11 Click on the 'pencil' icon
		 account.clickEditFirstRate();
		 // Step 12 Click the checkbox titled 'Start date' if checked
		 account.uncheckStartDate();
		 // Step 13 If checkbox is deselected, click in the textbox area to enter date manually. 
		 // (Select a date completely outside of the original bounds ie project was originally 8/14 - 8/18 and was changed to 8/21 - 8/25)
		 account.enterStartDate("552020");
		 // Step 14 Click the checkbox titled 'End date' if checked
		 account.uncheckEndDate();
		 // Step 15 If checkbox is deselected, click in the textbox area to enter date manually
		 // (Select a date completely outside of the original bounds ie project was originally 8/14 - 8/18 and was changed to 8/21 - 8/25)
		 account.enterEndDate("662020");
		 // Step 16 Click in textbox under 'Rate'
		 // Step 17 Enter numeric value
		 account.enterRate("15.50");
		 // Step 18 Click in the textbox under 'Comments'
		 // Step 19 Enter desired text in the area
		 account.enterComment("This is a comment, I hope");
		 // Step 20 Click 'Update Role budget'
		 account.clickUpdateComment();
		 // Step 21 Verify messages display that indicate that dates were not changed so it no longer fails silently.
		 // (ie Project updated, but some children failed to update - project updated - failed to update start and end date for role)
		 TestReporter.assertEquals(account.checkError(), errorChecker, "Checking the beginning of the date failed error message");
		 // Step 22 Click 'Logout' button
		 header.navigateLogout();
		 
		 
		 
	 }
}