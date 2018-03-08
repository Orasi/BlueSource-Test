/**
 * Tests Issue #926 Pending time should be converted to approved time when month is locked
 * Uses a precondition employee (Glip Glop) to test that a timesheet saved in a month
 * that becomes Timesheet locked will go from saved to approved.
 * @author Andrew McGrail
 */

package com.bluesource;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Admin;
import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class PendingTimeShouldBeConverted extends WebBaseTest {
	
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
    public void testPendingTimeShouldBeConverted() {
    	String month = "March";
    	String successfulCreateMessage = "Timesheet Lock Created";
    	String successfulDestryoMessage = "Timesheet Lock destroyed";
    	String employeeName = "Glip Glop";
    	
    	Header header = new Header(getDriver());
    	LoginPage loginPage = new LoginPage(getDriver());
    	Employees employees = new Employees(getDriver());
    	Admin admin = new Admin(getDriver());
    	EmployeePage employeePage = new EmployeePage(getDriver());
	
    	// Step 1 Precondition: Have an employee that has time saved to a project but not submitted.
    		//GlipGlop
    	// Step 2 Open Browser
    	// Step 3 Navigate to testing URL: http://10.238.243.127
    	// Step 4 Log in as admin user
    	loginPage.AdminLogin();
    	// Step 5 Click 'Admin' in Nav bar
    	// Step 6 Select 'Timesheet Locks'
    	header.navigateTimesheetLocks();
    	TestReporter.assertTrue(admin.verifyCreateLock(), "Successfully landed on the Timesheet Lock Page");
    	// Step 7 Select month that the saved timesheet is in from the 'Month' dropdown
    	admin.selectMonth(month);
    	// Step 8 Click 'Create Lock' bar
    	admin.clickCreateLock();
    	TestReporter.assertTrue(admin.verifySuccessMessage(successfulCreateMessage), "Verifying the success message after creating a timesheet lock");
    	// Step 9 Click Employees from Nav Bar
    	header.navigateEmployees();
    	TestReporter.assertTrue(employees.verifyPageIsLoaded(), "Successfully landed on Employees page");
    	// Step 10 Search for Employee with saved timesheet
    	employees.employeeSearch(employeeName);
    	TestReporter.assertTrue(employees.verifySearchBar(employeeName), "Verifying "+employeeName+" was searched for");
    	// Step 11 Click Employees name
    	employees.clickFirstEmployeeName();
    	TestReporter.assertTrue(employeePage.verifyEmployeeName(employeeName), "Successfully landed on "+employeeName+"'s page");
    	// Step 12 Click Manage button for Project Info
    	employeePage.clickManageProject();
    	TestReporter.assertTrue(employeePage.verifyTimesheetPage(), "Successfully landed on "+employeeName+"'s timesheet page");
    	// Step 13 Select the month for the saved timsheet from the 'Month' dropdown
    	employeePage.setMonth(month);
    	// Step 14 Click Go button
    	employeePage.clickTimesheetGo();
    	TestReporter.assertTrue(employeePage.verifyTimesheetTitle(month), "Verifying the correct month's timesheet is being displayed");
    	// Step 15 Verify that timesheets are listed as locked
    	TestReporter.assertTrue(employeePage.verifyTimesheetLocked("Locked"), "Verifying all weeks of the timesheet are 'Locked'");
    	// Step 16 Click 'Admin' in Nav bar
    	// Step 17 Select 'Timesheet Locks'
    	header.navigateTimesheetLocks();
    	TestReporter.assertTrue(admin.verifyCreateLock(), "Successfully landed on the Timesheet Lock Page");
    	// Step 18 Click red X next to the month that was previously Locked
    	admin.clickCloseTimesheet(month);
    	TestReporter.assertTrue(admin.verifySuccessMessage(successfulDestryoMessage), "Verifying the success message after destroying a timesheet lock");
    	// Step 19 Click Employees from Nav Bar
    	header.navigateEmployees();
    	TestReporter.assertTrue(employees.verifyPageIsLoaded(), "Successfully landed on Employees page");
    	// Step 20 Search for Employee with saved timesheet
    	employees.employeeSearch(employeeName);
    	TestReporter.assertTrue(employees.verifySearchBar(employeeName), "Verifying "+employeeName+" was searched for");
    	// Step 21 Click Employees name
    	employees.clickFirstEmployeeName();
    	TestReporter.assertTrue(employeePage.verifyEmployeeName(employeeName), "Successfully landed on "+employeeName+"'s page");
    	// Step 22 Click Manage button for Project Info
    	employeePage.clickManageProject();
    	TestReporter.assertTrue(employeePage.verifyTimesheetPage(), "Successfully landed on "+employeeName+"'s timesheet page");
    	// Step 23 Select the month for the saved timsheet from the 'Month' dropdown
    	employeePage.setMonth(month);
    	// Step 24 Click Go button
    	employeePage.clickTimesheetGo();
    	TestReporter.assertTrue(employeePage.verifyTimesheetTitle(month), "Verifying the correct month's timesheet is being displayed");
    	// Step 25 Verify that timesheets are listed as Approved
    	TestReporter.assertTrue(employeePage.verifyTimesheetLocked("Approved"), "Verifying all weeks of the timesheet are 'Locked'");
    }
}