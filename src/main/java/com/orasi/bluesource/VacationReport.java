package com.orasi.bluesource;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class VacationReport {

	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(linkText = "Employee Reports") private Link lnkEmployeeReports;
	@FindBy(linkText = "Vacation") private Link lnkVacation;
	@FindBy(xpath = "//*[@id='employee_list']/div/span/span[1]/span/span[2]") private Button btnDownArrow;
	@FindBy(xpath = "//input[@class='select2-search__field']") private Textbox txtEmployee;
	@FindBy(id = "start_date") private Textbox startDateSelector;
	@FindBy(id = "end_date") private Textbox endDateSelector;
	@FindBy(name = "commit") private Button btnGenerateReport;
	@FindBy(id = "DataTables_Table_0") private Webtable tblVacationTimesheet;

	/**Constructor**/
	public VacationReport(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	public boolean clickEmployeeReportsTab() {

		if (lnkEmployeeReports.syncVisible(10) == true) {
			lnkEmployeeReports.click();
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Check Vacation report tab is clicked
	 * @author shahrukh.rehman
	 */
	public boolean clickVacationTab() {

		if (lnkVacation.syncVisible(10) == true) {
			lnkVacation.click();
			return true;	
		}
		else {
			return false;
		}	
	}

	/**
	 * @param employeeFullName {@link String} employee's full name
	 * @param startDate {@link String} Date format MM/DD/YYYY
	 * @param endDate {@link String} Date format MM/DD/YYYY
	 * Check Vacation report
	 * @author shahrukh.rehman
	 */
	public void generateReport(String employeeFullName, String startDate, String endDate) {

		btnDownArrow.syncVisible(10);
		btnDownArrow.click();

		txtEmployee.set(employeeFullName);
		txtEmployee.sendKeys(Keys.RETURN);

		startDateSelector.set(startDate);

		endDateSelector.set(endDate);

		btnGenerateReport.click();
	}

	/**
	 * Check if Vacation Report Table is displayed
	 * @author shahrukh.rehman
	 */
	public boolean vacationTimesheetTableVisible() {
		if (tblVacationTimesheet.syncVisible(10) == true) {

			return true;
		}
		else {
			return false;
		}
	}
}
