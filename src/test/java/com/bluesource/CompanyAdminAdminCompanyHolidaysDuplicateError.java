/**
 * Tests Company Admin_Admin_Company Holidays_Duplicate_Error - Issue #748
 * Test attempts to create a Holiday with the same Name and Date as an
 *  already existing holiday, then verifies the duplicate holiday is
 *  not created and the proper error message is shown
 *  @author andrew.mcgrail
 */
package com.bluesource;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Admin;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class CompanyAdminAdminCompanyHolidaysDuplicateError extends WebBaseTest{	
			
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
			testStart("CompanyAdminAdminCompanyHolidaysDuplicateError");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testCompanyAdminAdminCompanyHolidaysDuplicateError()
	 {
		 String holidayName, holidayDate = null;
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Admin adminPage = new Admin(getDriver());
		 
		 // Step 1 Open the browser and navigate to "http://10.238.243.127".
		 // Step 2 Login as 'company.admin'.
		 loginPage.AdminLogin();
		 // Step 3 Click on 'Admin' -> 'Company Holidays'.
		 header.navigateCompanyHolidays();
		 TestReporter.assertTrue(adminPage.verifyAddNewHoliday(), "Successfully landed on Company Holidays page");
		 // Step 4 Notate the name and date of an existing Holiday.
		 holidayName = adminPage.getFirstHolidayName();
		 holidayDate = adminPage.getFirstHolidayDate();
		 String failMessage = "Holiday "+holidayName+" failed to be created. [[\"Date has already been taken\"]]";
		 // Step 5 Click the 'Add New Holiday' button.
		 adminPage.clickAddNewHoliday();
		 TestReporter.assertTrue(adminPage.verifyNewHolidayName(), "Successfully landed on New Holiday page");
		 // Step 6 Inside the 'Name' textbox, enter in the Name of an existing Holiday.
		 adminPage.populateHolidayname(holidayName);
		 // Step 7 Select the Date of the existing Holiday inside the 'Date' field.
		 adminPage.populateHolidayDate(holidayDate);
		 // Step 8 Click the 'Create Holiday' button.
		 adminPage.clickCreateHoliday();
		 // Step 9 Verify the Error Message displays on the page the user is currently displaying.
		 TestReporter.assertTrue(adminPage.verifyFailureMessage(failMessage), "Verified the Holiday couldn't be created and the error message is correct");
	} 
}