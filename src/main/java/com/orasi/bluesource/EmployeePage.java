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
	@FindBy(xpath = "//div[24]/input") Button btnUpdateEmployee;
	@FindBy(xpath = "//*[@id='edit_employee_2']/div[24]/button") Button btnClose;
	@FindBy(xpath = "//div[2]/form/div//tbody/tr[2]/td[1]/span/span[1]/span/span[2]") Button btnNonbillable;
	@FindBy(xpath = "/html/body/span/span/span[1]/input") Textbox txtTimeSheetRole;
	@FindBy(xpath = "//td[@class=\"time-entry-cell\"][1]/div[@class=\"tooltop-container\"]/input[@id=\"employee_reported_times___date_hours_hours\"]") Textbox txtFirstHour;
	@FindBy(xpath = "//td[@class=\"time-entry-cell\"][2]/div[@class=\"tooltop-container\"]/input[@id=\"employee_reported_times___date_hours_hours\"]") Textbox txtSecondHour;
	@FindBy(xpath = "//td[@class=\"time-entry-cell\"][3]/div[@class=\"tooltop-container\"]/input[@id=\"employee_reported_times___date_hours_hours\"]") Textbox txtThirdHour;
	@FindBy(xpath = "//td[@class=\"time-entry-cell\"][4]/div[@class=\"tooltop-container\"]/input[@id=\"employee_reported_times___date_hours_hours\"]") Textbox txtFourthHour;
	@FindBy(xpath = "//td[@class=\"time-entry-cell\"][5]/div[@class=\"tooltop-container\"]/input[@id=\"employee_reported_times___date_hours_hours\"]") Textbox txtFifthHour;
	@FindBy(xpath = "//td[@class=\"time-entry-cell\"][6]/div[@class=\"tooltop-container\"]/input[@id=\"employee_reported_times___date_hours_hours\"]") Textbox txtSixthHour;
	@FindBy(xpath = "//td[@class=\"time-entry-cell\"][7]/div[@class=\"tooltop-container\"]/input[@id=\"employee_reported_times___date_hours_hours\"]") Textbox txtSeventhHour;
	@FindBy(xpath = "//li[@class='select2-results__option select2-results__option--highlighted']") Button btnOnboarding;
	@FindBy(xpath = "//div[2]/form/div[4]/input[2]") Button btnSubmitTimesheet;
	@FindBy(xpath = "//*[@id='notification-area']/div") Label lblConfirmation;

	
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
	
	/*Clicks the update employee button on the employee general info modal
	 * @author Andrew McGrail
	 */	
	public void clickUpdateEmployee() {
		btnUpdateEmployee.click();
	}
	
	/*Clicks the close button on the employee general info modal
	 * @author Andrew McGrail
	 */	
	public void clickClose() {
		btnClose.click();
		btnClose.syncHidden(2,true);
	}
	
	/*Checks the "Require Nonbillable Time Entry" checkbox on the employee general info modal
	 * @author Andrew McGrail
	 */
	public void clickNonbillableTime() {
		cbTimeEntryCheckbox.scrollIntoView();
		cbTimeEntryCheckbox.syncVisible(2,true);
		lblModal.syncVisible(2, true);
		cbTimeEntryCheckbox.check();
	}
	
	/*Unchecks the "Require Nonbillable Time Entry" checkbox on the employee general info modal
	 * @author Andrew McGrail
	 */
	public void unclickNonbillableTime() {
		cbTimeEntryCheckbox.scrollIntoView();
		cbTimeEntryCheckbox.syncVisible(2,true);
		lblModal.syncVisible(2, true);
		cbTimeEntryCheckbox.uncheck();
	}
	
	/*Verifys that the submit timesheet button is displayed on the employees page
	 * @author Andrew McGrail
	 */
	public boolean checkNonbillableRoles() {
			return btnSubmitTimesheet.isDisplayed();
	}
	
	/*Fills the nonbillable timesheet with a 50 hour work week doing Onboarding
	 * @author Andrew McGrail
	 */
	public void fillTimesheet() {
		txtFirstHour.set("10");
		txtSecondHour.set("10");
		txtThirdHour.set("10");
		txtFourthHour.set("10");
		txtFifthHour.set("10");
		txtSixthHour.set("0");
		txtSeventhHour.set("0");
		btnNonbillable.click();
		txtTimeSheetRole.sendKeys("Onboarding");
		btnOnboarding.click();
	}
	
	
	/*Clicks the "Submit" timesheet button
	 * @author Andrew McGrail
	 */
	public void sendTimesheet() {
		btnSubmitTimesheet.click();
	}
	
	/*Verify's the success message when submitting a timesheet correctly
	 * 
	 * @return The parsed success message
	 * @author Andrew McGrail
	 */
	public String checkSuccessMessage() {
		String parseThis = lblConfirmation.getText();
		String parsedString = parseThis.substring(2, 50); //92
		return parsedString;
	}
	
}