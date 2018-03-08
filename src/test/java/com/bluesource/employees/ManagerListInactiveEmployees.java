/*
 * Test for to make sure inactive users do not show up while
 * selecting an employee's manager.
 * @author: Andrew McGrail
 */

package com.bluesource.employees;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class ManagerListInactiveEmployees extends WebBaseTest{	
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
			testStart("testManagerListInactiveEmployees");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test//(dataProvider = "login")
	 public void testManagerListInactiveEmployees()
	 {
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 // Steps 1&2. Log in to 10.238.243.127 as company.admin
		 loginPage.AdminLogin();
		 header.navigateEmployees();
		 
		 // Step 3. Select the employee with the username "a.a"
		 employees.clickFirstName();
		 // Step 4. Click the user's Edit button over general info
		 employeePage.editGeneralInfo();
		 // Step 5. Click Deactive Employee
		 employeePage.clickDeactivateEmployee();		 
		 // Step 6. Click Deactive
		 employeePage.clickDeactivate();		 
		 // Step 7. Navigate to the employees page
		 header.navigateEmployees();
		 // Step 8. Click the add employees button
		 employees.clickAddEmployee();
		 // Step 9. Scroll down to the 'Manager' dropdown list
		 employees.scrollTo1stManager();
		 // Step 10. Verify the Employee that was just deactivated is not appearing in the list.
		 TestReporter.assertFalse(employees.checkManagerOption("a a"), "Asserting the inactive employee isn't listed as an option.");
		 // Step 11. Click the 'Close' button.
		 employees.clickClose();
		 // Step 12. Click on the name of another Employee.
		 employees.clickSecondName();
		 // Step 13. Inside the 'General Info' panel click the 'Edit' button.
		 employeePage.editGeneralInfo();
		 // Step 14. Verify in the 'Manager' dropdown list, the Inactive Employee does not appear.
		 TestReporter.assertFalse(employees.checkManagerOption("a a"), "Asserting the inactive employee isn't listed as an option.");
		 // Step 15. Click the 'Close' button.
		 employeePage.clickClose();
		 // Step 16. Click on 'Employees' in the header.
		 header.navigateEmployees();
		 // Step 17. Click on the name of the Inactive Employee.
		 employees.clickFirstName();
		 // Step 18. Inside the 'General Info' panel click the 'Edit' button.
		 employeePage.editGeneralInfo();
		 // Step 19. Inside the 'Status' dropdown list, select 'Permanent' and click the 'Update Employee' button.
		 employeePage.selectStatus("Permanent");
		 employeePage.clickUpdateEmployee();
	 }
}
