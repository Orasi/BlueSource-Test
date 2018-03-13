/**
 * Tests Employees_Timesheet_CommentsBlank_Issue #613
 * Test checks that a department head can add comments to
 *  an employees timesheet without adding hours, and the
 *  comment will show up along with a 0 hour timesheet
 * @author Andrew McGrail
 */

package com.bluesource.employees;

import java.util.ResourceBundle;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.FilledRoleForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.Constants;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.utils.date.DateTimeConversion;
import com.orasi.utils.date.SimpleDate;
import com.orasi.web.WebBaseTest;

public class EmployeesTimesheetCommentsBlank extends WebBaseTest{	
	// ************* *
	// Data Provider
	// **************
	/*@DataProvider(name = "login", parallel=true)
	public Object[][] scenarios() {
	return new ExcelDataProvider("/testdata/blueSource_Users.xlsx", "Sheet1").getTestData();
	}*/
			
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
			testStart("EmployeesTimesheetCommentsBlank");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test
	 public void testEmployeesTimesheetCommentsBlank()
	 {
		 String firstName = "Issue";
		 String lastName = "613";
		 String fullName = firstName+" "+lastName;
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 // Step 1 Open a browser
		 // Step 2 Navigate to http://10.238.242.236
		 // Step 3 Enter the username of a departmenthead in the username field and a password in the password field. Then click the login button.
		 loginPage.AdminLogin();
		 // Step 4 Click the first name of an Employee in the Employee table.
		 employees.employeeSearch(fullName);
		 employees.selectEmployeeByName(firstName);
		 TestReporter.assertTrue(employeePage.verifyManageProjectInfo(), "Successfully landed on "+fullName+"'s page");
		 // Step 5 Click the Manage button in the Project Info section
		 employeePage.clickManageProjectInfo();
		 TestReporter.assertTrue(employeePage.verifyAddTimesheet3(), "The Project info for "+fullName+" is displayed");
		 // Step 6 On one of the timesheets, click the Add button.
		 employeePage.clickAddTimesheet3();
		 TestReporter.assertTrue(employeePage.verifySaveTimesheet(), "Successfully opened the timesheet modal");
		 // Step 7 Right-Click in one of the time fields.
		 employeePage.rightClickFirstDatOfTimesheet();
		 TestReporter.assertTrue(employeePage.verifyCommentBox(), "The comment box is being displayed");
		 // Step 8 Enter text into the comment box and click the OK button.
		 
		 // Step 9 Click the Save button
		 employeePage.clickSaveTimesheet();
		 
		 // Step 10 If the message of a locked time sheet is displayed, Click the unlock button and then Click the pencil Icon
		 
		 // Step 11 Repeat steps 7 - 9 and verify that the speech bubble is present and the field is populated with "0"
	} 
}