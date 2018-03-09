/**
 * Test for User Guide AddIn_Issue627
 * Creates a user, then assigned that user to a known project role.
 * Then checks the MailCatcher for an email to the new user explaining
 *  their new role and giving them a .pdf
 * @author Andrew McGrail
 */
package com.bluesource.employees;

import com.orasi.utils.TestReporter;
import com.orasi.utils.dataHelpers.personFactory.*;
import com.orasi.utils.date.DateTimeConversion;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.FilledRoleForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MailCatcher;
import com.orasi.bluesource.MessageCenter;
import com.orasi.bluesource.ProjectEmployees;
import com.orasi.bluesource.ReportedTimesSummary;
import com.orasi.web.WebBaseTest;

public class UserGuideAddIn extends WebBaseTest{	
			
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
			testStart("UserGuideAddIn");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 
	 @Test
	 public void testUserGuideAddIn()
	 {
		 String firstName = "Issue43";
		 String lastName = "627";
		 String employeeSuccessMessage = "Employee successfully created";
		 String accountName = "Issue 627";
		 String roleSuccessMessage = "Filled Role created.";
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 Accounts accounts = new Accounts(getDriver());
		 MailCatcher mailCatcher = new MailCatcher(getDriver());
		 
		// Step 1 Open browser.
		// Step 2 Navigate to http://10.238.242.236
		// Step 3 Enter a valid username of a user with the Company Admin role.
		// Step 4 Enter a valid password.
		// Step 5 Click the Login button or press Enter.
		 loginPage.AdminLogin();
		// Step 6 Click the Add button to add a new employee.
		 header.navigateEmployees();
		 TestReporter.assertTrue(employees.verifyProjectEmployeesPage(), "Successfully landed on Employees Page");
		 employees.clickAddEmployee();
		 TestReporter.assertTrue(employees.verifyAddEmployeeModal(), "Add Employee Modal has appeared");
		// Step 7 Enter the required fields to create a new employee and click the Create Employee button.
		 employees.addEmployeeModal(firstName+"."+lastName, firstName, lastName);
		 TestReporter.assertTrue(employees.verifySuccessMessage(employeeSuccessMessage), "Successfully created an employee with the email "+firstName+"."+lastName);
		// Step 8 Click the Accounts top navigation.
		 header.navigateAccounts();
		 TestReporter.assertTrue(accounts.verifyAccountsPage(), "Successfully landed on Accounts page");
		// Step 9 Click an account name.
		 accounts.searchAccountName(accountName);
		 accounts.clickAccountLink(accountName);
		 TestReporter.assertTrue(accounts.verifyAccountName(accountName), "Successfully Landed on "+accountName+ "'s account page");
		// Step 10 Click a project name.
		 accounts.clickFirstProject();
		 TestReporter.assertTrue(accounts.verifyProjectPage(), "Successfully landed on project page");
		// Step 11 Click a role name under Project Roles.
		 accounts.clickFirstRole();
		 TestReporter.assertTrue(accounts.verifyRolePage(), "Successfully landed on Project Roles page");
		// Step 12 Click the Assign Employee button under the Filled Roles section.
		 accounts.clickAssignEmployee();
		 TestReporter.assertTrue(accounts.verifyAssignEmployee(), "Successfully landed on Assign Employee Modal");
		// Step 13 Select the name of the newly created employee and click the Create Filled Role button.
		 accounts.assignEmployee(firstName+" "+lastName);
		 TestReporter.assertTrue(accounts.verifySuccessMessage(roleSuccessMessage), firstName+" "+lastName+" was assigned to the Role");
		// Step 14 Navigate to MailCatcher http://10.238.242.236:1080/
		 accounts.navigateMailCatcher();
		 TestReporter.assertTrue(mailCatcher.verifyPageIsLoaded(), "Successfully landed in Mail Catcher");
		// Step 15 Locate the email sent to the employee.
		 mailCatcher.findRecipient(firstName+"."+lastName);
		 TestReporter.assertTrue(mailCatcher.verifyEmailRecipient(firstName+" "+lastName), "Selected the email to "+accountName);
		// Step 16 Verify that the email informs the employee of their new filled role and the BlueSource_User Guide-Timekeeping file is attached.
		 TestReporter.assertTrue(mailCatcher.verifyEmailRole(accountName), "Verifying the email tells the recipient their role");
		// Step 17 Click the email attachment.
		 mailCatcher.clickAttachment();
	} 
}