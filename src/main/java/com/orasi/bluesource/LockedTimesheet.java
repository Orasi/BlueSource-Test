package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class LockedTimesheet {

	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(linkText = "Admin") private Link lnkAdminTab;
	@FindBy(linkText = "Timesheet Locks") private Link lnkTimesheetLocks;
	@FindBy(linkText = "Logout") private Link lnkLogout;
	@FindBy(linkText = "Manage") private Link lnkManage;
	@FindBy(linkText = "February") private Link lnkChangeMonth;
	@FindBy(linkText = "Employee Reports") private Link lnkEmployeeReports;
	@FindBy(linkText = "Time by Project") private Link lnkTimeByProject;
	@FindBy(linkText = "Time by Role") private Link lnkTimeByRole;
	@FindBy(linkText = "Time by Time Sheet") private Link lnkTimeByTimeSheet;
	@FindBy(linkText = "Billing by Project") private Link lnkBillingByProject;
	@FindBy(linkText = "Billing by Role") private Link lnkBillingByRole;
	@FindBy(linkText = "Combined Total Hours") private Link lnkCombinedTotalHours;
	@FindBy(id = "date_month") private Listbox listMonths;
	@FindBy(id = "flavor") private Listbox listBillingType;
	@FindBy(xpath = "//*[@id='employee_list']/div/span/span[1]/span/span[2]") private Button btnDownArrow;
	@FindBy(xpath = "//input[@class='select2-search__field']") private Textbox txtEmployee;
	@FindBy(xpath = "//input[@value='Create Lock']") private Button btnCreateLock;
	@FindBy(xpath = "//*[@id='notification-area']/div") private Label lblSuccessAlert;
	@FindBy(xpath = "//*[@id='reportTable']//*[text()='No results returned by this report']") private Label lblEmployeeReport;	
	@FindBy(id = "select_date_range") private Button btnGo;
	@FindBy(name = "commit") private Button btnGenerateReport;
	@FindBy(xpath = "//*[@class=\"time-summary-table table\"]/tbody/tr[4]/td/a") private Button btnAdd;
	@FindBy(xpath = "//input[@type='submit' and @value='Submit']") private Button submitTimesheet;
	@FindBy(id = "start_date") private Textbox startDateSelector;
	@FindBy(id = "end_date") private Textbox endDateSelector;
	@FindBy(id = "DataTables_Table_0") private Webtable tblTimesheet;
	
	
	/**Constructor**/
	public LockedTimesheet(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/*
	 * Click on accounts tab and then click on timesheet locks
	 * author: Shahrukh Rehman
	 */
	public void openLockedTimesheet() {

		if (lnkAdminTab.isDisplayed() == true)
		{
			lnkAdminTab.click();
			lnkTimesheetLocks.click();
		}
	}
	
	/*
	 * Create a locked timesheet
	 * author: Shahrukh Rehman
	 */
	public void createLockedTimesheet(String month) {

		listMonths.syncVisible(5);
		listMonths.select(month);
		btnCreateLock.click();		
	}

	/*
	 * Verify successful timesheet lock
	 * author: Shahrukh Rehman
	 */
	public boolean verifySuccessAlert() {

		driver.switchTo().alert().accept();

		Boolean visible = true;

		lblSuccessAlert.syncVisible(10);

		if (lblSuccessAlert.isDisplayed()== visible) {
			System.out.println("Timesheet Locked");
		}
		else {
			visible = false;
		}

		return visible;
	}
	
	/*
	 * Click on logout tab
	 * author: Shahrukh Rehman
	 */
	public void logout() {
		lnkLogout.syncVisible(10);
		lnkLogout.click();
	}
	
	public void clickManageButton() {
		lnkManage.syncVisible(5);
		lnkManage.jsClick();
	}
	
	public void selectMonthWithLockedTimesheet(String month) {
		listMonths.syncVisible(5);
		listMonths.select(month);
		btnGo.click();		

	}
	
	/*
	 * Add new timesheet for the employee
	 * author: Shahrukh Rehman
	 */
	public void addNewTimesheet(String billingType) {

		btnAdd.syncVisible(10);
		btnAdd.click();

		listBillingType.syncVisible(10);
		listBillingType.select(billingType);

		for (int i = 2; i < 7; i++) {
			WebElement cellText = driver.findElement(By.xpath("//*[@class=\"time-row\"]/td[" + i + "]/div/input[2]"));
			cellText.click();
			cellText.sendKeys("8");
		}
		
		submitTimesheet.click();
	}
	
	/*
	 * Verify timesheet is created
	 * author: Shahrukh Rehman
	 */
	public boolean verifySecondSuccessAlert() {

		Boolean visible = true;

		lblSuccessAlert.syncVisible(10);

		if (lblSuccessAlert.isDisplayed()== visible) {
			System.out.println("Timesheet Submitted");
		}
		else {
			visible = false;
		}

		return visible;
	}
	
	/*
	 * Different Login
	 * author: Shahrukh Rehman
	 */
	public void newLogin (OrasiDriver driver){
		driver.get("http://10.238.243.127:8080/reporting/login");
	}
	
	public void clickEmployeeReportsTab() {

		lnkEmployeeReports.syncVisible(10);
		lnkEmployeeReports.click();
	}
	
	/*
	 * Check time by project
	 * author: Shahrukh Rehman
	 */
	public void checkTimeByProjectTab(String employeeFullName, String startDate, String endDate) {
		clickEmployeeReportsTab();
		lnkTimeByProject.syncVisible(10);
		lnkTimeByProject.click();
		generateReport(employeeFullName, startDate, endDate);
	}
	
	/*
	 * Check time by Role
	 * author: Shahrukh Rehman
	 */
	public void checkTimeByRoleTab(String employeeFullName, String startDate, String endDate) {
		clickEmployeeReportsTab();
		lnkTimeByRole.syncVisible(10);
		lnkTimeByRole.click();
		generateReport(employeeFullName, startDate, endDate);
	}
	
	/*
	 * Check time by Timesheet
	 * author: Shahrukh Rehman
	 */
	public void checkTimeByTimeSheetTab(String employeeFullName, String startDate, String endDate) {
		clickEmployeeReportsTab();
		lnkTimeByTimeSheet.syncVisible(10);
		lnkTimeByTimeSheet.click();
		generateReport(employeeFullName, startDate, endDate);
	}
	
	/*
	 * Check billing by project
	 * author: Shahrukh Rehman
	 */
	public void checkBillingByProjectTab(String employeeFullName, String startDate, String endDate) {
		clickEmployeeReportsTab();
		lnkBillingByProject.syncVisible(10);
		lnkBillingByProject.click();
		generateReport(employeeFullName, startDate, endDate);
	}
	
	/*
	 * Check billing by role
	 * author: Shahrukh Rehman
	 */
	public void checkBillingByRoleTab(String employeeFullName, String startDate, String endDate) {
		clickEmployeeReportsTab();
		lnkBillingByRole.syncVisible(10);
		lnkBillingByRole.click();
		generateReport(employeeFullName, startDate, endDate);
	}
	
	/*
	 * Check combined total hours
	 * author: Shahrukh Rehman
	 */
	public void checkCombinedTotalHoursTab(String employeeFullName, String startDate, String endDate) {
		clickEmployeeReportsTab();
		lnkCombinedTotalHours.syncVisible(10);
		lnkCombinedTotalHours.click();
		
		btnDownArrow.syncVisible(10);
		btnDownArrow.click();
		
		txtEmployee.set(employeeFullName);
		txtEmployee.sendKeys(Keys.RETURN);
		
		startDateSelector.set(startDate);

		endDateSelector.set(endDate);
		
		btnGenerateReport.click();
		
		tblTimesheet.syncVisible(10);
	}
	
	/*
	 * Generate reports for each selection
	 * author: Shahrukh Rehman
	 */
	public void generateReport(String employeeFullName, String startDate, String endDate) {

		btnDownArrow.syncVisible(10);
		btnDownArrow.click();
		
		txtEmployee.set(employeeFullName);
		txtEmployee.sendKeys(Keys.RETURN);
		
		startDateSelector.set(startDate);

		endDateSelector.set(endDate);
		
		btnGenerateReport.click();
		
		lblEmployeeReport.syncVisible(10);
		
		System.out.println("No reports found");
	}

}
