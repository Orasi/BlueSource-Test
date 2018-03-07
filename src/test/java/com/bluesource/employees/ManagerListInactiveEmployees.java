/** 
 * Test to make sure inactive users do not show up while
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
	
	 @Test
	 public void testManagerListInactiveEmployees()
	 {
		 String successMessage="Employee successfully deactivated";
		 String fullName=null;
		 int rowNum=0;
		 
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 
		 // Steps 1&2. Log in to 10.238.243.127 as company.admin
		 loginPage.AdminLogin();
		 header.navigateEmployees();
		 TestReporter.assertTrue(employees.verifyPageIsLoaded(), "The page listing employees has been reached.");
		 // Step 3. Select the first Active employee on the list
		 rowNum=employees.findFirstActiveInTable();
		 employees.selectEmployeeFirstname(rowNum, 1);
		 TestReporter.assertTrue(employeePage.verifyPageIsLoaded(), "Successfully landed on the first active employee's page");
		 // Step 4. Click the user's Edit button over general info
		 employeePage.editGeneralInfo();
		 fullName=employeePage.getFullName();
		 TestReporter.assertTrue(employeePage.verifyModalPopup(), "Edit General Modal brought up");
		 // Step 5. Click Deactive Employee
		 employeePage.clickDeactivateEmployee();
		 // Step 6. Click Deactive
		 employeePage.clickDeactivate();
		 TestReporter.assertEquals(employees.getSuccessMessage(), successMessage, "Verifying "+fullName+" was successfully made inactive.");
		 // Step 7. Navigate to the employees page
		 header.navigateEmployees();
		 TestReporter.assertTrue(employees.verifyPageIsLoaded(), "The page listing employees has been reached.");
		 // Step 8. Click the add employees button
		 employees.clickAddEmployee();
		 TestReporter.assertTrue(employees.verifyModalPopup(), "Opened the 'add employees' Modal Popout");
		 // Step 9. Scroll down to the 'Manager' dropdown list
		 employees.scrollTo1stManager();
		 // Step 10. Verify the Employee that was just deactivated is not appearing in the list.
		 TestReporter.assertFalse(employees.checkManagerOption(fullName), "Asserting "+fullName+" isn't listed as a manager.");
		 // Step 11. Click the 'Close' button.
		 employees.clickClose();
		 TestReporter.assertFalse(employees.verifyModalPopupGone(), "Closed the 'add employees' Modal Popout");
		 // Step 12. Click on the name of the second Employee.
		 employees.clickSecondName();
		 TestReporter.assertTrue(employeePage.verifyPageIsLoaded(), "Successfully landed on the second employee's page");
		 // Step 13. Inside the 'General Info' panel click the 'Edit' button.
		 employeePage.editGeneralInfo();
		 TestReporter.assertTrue(employeePage.verifyModalPopup(), "Edit General Modal brought up");
		 // Step 14. Verify in the 'Manager' dropdown list, the Inactive Employee does not appear.
		 TestReporter.assertFalse(employees.checkManagerOption(fullName), "Asserting "+fullName+" still isn't listed as a manager.");
		 // Step 15. Click the 'Close' button.
		 employeePage.clickClose();
		 TestReporter.assertFalse(employeePage.verifyModalPopupGone(), "Edit General Modal closed");
		 // Step 16. Click on 'Employees' in the header.
		 header.navigateEmployees();
		 TestReporter.assertTrue(employees.verifyPageIsLoaded(), "The page listing employees has been reached.");
		 // Step 17. Click on the name of the Inactive Employee.
		 employees.selectEmployeeByFirstname(fullName.substring(0, fullName.indexOf(' ')));
		 TestReporter.assertEquals(employeePage.getFullName(), fullName, "Successfully landed on the initial employee's page");
		 // Step 18. Inside the 'General Info' panel click the 'Edit' button.
		 employeePage.editGeneralInfo();
		 TestReporter.assertTrue(employeePage.verifyModalPopup(), "Edit General Modal brought up");
		 // Step 19. Inside the 'Status' dropdown list, select 'Permanent' and click the 'Update Employee' button.
		 employeePage.selectStatus("Permanent");
		 employeePage.clickUpdateEmployee();
		 TestReporter.assertEquals(employees.getSuccessMessage(), "Employee updated successfully", "Verifying "+fullName+" was successfully made Permanent.");
	 }
}
