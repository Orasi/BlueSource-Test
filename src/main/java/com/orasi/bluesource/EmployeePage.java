package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class EmployeePage {
	private OrasiDriver driver = null;
		
	/**Page Elements**/
	@FindBy(xpath = "//tr[1]//a[@class='glyphicon glyphicon-pencil']") Button btnEditFirstProject;
	@FindBy(xpath = "//div[@id='panel_body_1']//table") Webtable tblProjectInfo;
	@FindBy(xpath = "//*[@id='accordion']/div/div[7]/button") Button btnEditGeneral;
	@FindBy(partialLinkText = "Deactivate Employee") Button btnDeactivateEmployee;
	@FindBy(partialLinkText = "Deactivate") Button btnDeactivate;
	@FindBy(xpath = "//*[@id='modal_1']/div/div") Label lblModal;
	@FindBy(xpath = "//*[@id='employee_require_nonbillable']") Checkbox cbTimeEntryCheckbox;
	@FindBy(id = "employee_status") Listbox lstStatus;
	@FindBy(xpath = "//input[@value='Update Employee']") Button btnUpdateEmployee;
	@FindBy(xpath = "(//div[@class='form-group modal-footer']/button)[1]") Button btnClose;
	@FindBy(xpath = "(//span[@class='select2-selection__arrow'])[last()]") Button btnNonbillable;
	@FindBy(xpath = "//input[@class='select2-search__field']") Textbox txtTimeSheetRole;
	@FindBy(xpath = "(//input[@id='employee_reported_times___date_hours_hours'])[last()-6]") Textbox txtFirstHour;
	@FindBy(xpath = "(//input[@id='employee_reported_times___date_hours_hours'])[last()-5]") Textbox txtSecondHour;
	@FindBy(xpath = "(//input[@id='employee_reported_times___date_hours_hours'])[last()-4]") Textbox txtThirdHour;
	@FindBy(xpath = "(//input[@id='employee_reported_times___date_hours_hours'])[last()-3]") Textbox txtFourthHour;
	@FindBy(xpath = "(//input[@id='employee_reported_times___date_hours_hours'])[last()-2]") Textbox txtFifthHour;
	@FindBy(xpath = "(//input[@id='employee_reported_times___date_hours_hours'])[last()-1]") Textbox txtSixthHour;
	@FindBy(xpath = "(//input[@id='employee_reported_times___date_hours_hours'])[last()]") Textbox txtSeventhHour;
	@FindBy(xpath = "//li[@class='select2-results__option select2-results__option--highlighted']") Button btnHighlightedNonBillable;
	@FindBy(xpath = "(//input[@value='Submit'])[last()]") Button btnSubmitTimesheet;
	@FindBy(xpath = "//*[@id='notification-area']/div") Label lblConfirmation;
	@FindBy(xpath = "//*[@id='content']/h1") Label lblEmployeename;
	@FindBy(xpath = "//div[@id='panel_body_1']") Label lblProjectInfo;

	
	/**Constructor**/
	public EmployeePage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public void clickFirstEditProjectButton(){
		btnEditFirstProject.click();
	}

	public boolean verifyProjectAssign(String strProject) {
		// verify project is in project column
		// get project column
		Integer intColumn = tblProjectInfo.getColumnWithCellText("Project", 1);
		Integer intRow = tblProjectInfo.getRowWithCellText(strProject, intColumn);
		
		if (strProject.equals(tblProjectInfo.getCellData(intRow, intColumn))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifyStartDate(String strStartDate, String strProject) {
		Integer intProjectColumn = tblProjectInfo.getColumnWithCellText("Project", 1);
		Integer intProjectRow = tblProjectInfo.getRowWithCellText(strProject, intProjectColumn);
		
		Integer intStartDateColumn = tblProjectInfo.getColumnWithCellText("Start Date", 1);
		
		if (strStartDate.equals(tblProjectInfo.getCellData(intProjectRow, intStartDateColumn))){
			return true;
		} else {
			return false;
		}
	}

	public void editGeneralInfo() {
		btnEditGeneral.syncVisible(3, true);
		btnEditGeneral.click();
		
	}		
	
	public void clickDeactivateEmployee() {
		lblModal.syncVisible(2, true);
		cbTimeEntryCheckbox.scrollIntoView();
		btnDeactivateEmployee.syncVisible(2, true);
		btnDeactivateEmployee.click();
	}
	
	public void clickDeactivate(){
		btnDeactivateEmployee.syncVisible(2, true);
		btnDeactivate.click();
	}
	
	public void selectStatus(String strName) {
		cbTimeEntryCheckbox.scrollIntoView();
		lstStatus.syncVisible(2,true);
		lstStatus.select(strName);
	}
	
	public void clickUpdateEmployee() {
		btnUpdateEmployee.click();
	}
	
	public void clickClose() {
		btnClose.click();
		btnClose.syncHidden(2,true);
	}
	
	/**
	 * Checks the "Require Nonbillable Time Entry" checkbox on the employee general info modal
	 * @author Andrew McGrail
	 */
	public void clickNonbillableTime() {
		lblModal.syncVisible(2, true);
		cbTimeEntryCheckbox.check();
	}
	
	/**
	 * Unchecks the "Require Nonbillable Time Entry" checkbox on the employee general info modal
	 * @author Andrew McGrail
	 */
	public void unclickNonbillableTime() {
		lblModal.syncVisible(2, true);
		cbTimeEntryCheckbox.uncheck();
	}
	
	/**
	 * Verifys that the submit timesheet button is displayed on the employees page
	 * @author Andrew McGrail
	 */
	public boolean verifySubmitTimesheetButton() {
			return btnSubmitTimesheet.isDisplayed();
	}
	
	/**
	 * Fills the nonbillable timesheet with a 40 hour work week doing Onboarding
	 * @author Andrew McGrail
	 */
	public void fillTimesheet() {
		txtFirstHour.set("8");
		txtSecondHour.set("8");
		txtThirdHour.set("8");
		txtFourthHour.set("8");
		txtFifthHour.set("8");
		txtSixthHour.set("0");
		txtSeventhHour.set("0");
		btnNonbillable.click();
		txtTimeSheetRole.sendKeys("Onboarding");
		btnHighlightedNonBillable.click();
	}
	
	public void sendTimesheet() {
		btnSubmitTimesheet.click();
	}
	
	/**
	 * Verify's the success message when submitting a timesheet correctly
	 * @return The parsed success message
	 * @author Andrew McGrail
	 */
	public String checkSuccessMessage() {
		String parseThis = lblConfirmation.getText();
		String parsedString = parseThis.substring(2, parseThis.length());
		return parsedString;
	}
	
	/**
	 * Verifies the General Info modal is visible
	 * @return - True if modal is visible
	 */
	public boolean verifyEditButton() {
		return lblModal.syncVisible(2,true);
	}
	
	/**
	 * Verifies the page is for a specified employee name
	 * @param name - Name to check if the correct page is brought up
	 * @return - True if employee page name matchs passed value
	 */
	public boolean verifyEmployeeName(String name) {
		return lblEmployeename.getText().equalsIgnoreCase(name);
	}
	
	/**
	 * Verifies there are no timesheets shown on an employee page
	 * @return - True if no timesheets appear on the page
	 */
	public boolean verifyNoTimeSheets() {
		return lblProjectInfo.getText().equalsIgnoreCase("No Recent Projects");
	}
}