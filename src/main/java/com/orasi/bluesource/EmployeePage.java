package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class EmployeePage {
	private OrasiDriver driver = null;
		
	/**Page Elements**/
	@FindBy(xpath = "//tr[1]//a[@class='glyphicon glyphicon-pencil']") Button btnEditFirstProject;
	@FindBy(xpath = "//div[@id='panel_body_1']//table") Webtable tblProjectInfo;
	@FindBy(xpath = "//*[@id=\'accordion\']/div/div[7]/button") Button btnEditGeneral;
	@FindBy(partialLinkText = "Deactivate Employee") Button btnDeactivateEmployee;
	@FindBy(partialLinkText = "Deactivate") Button btnDeactivate;
	@FindBy(xpath = "//*[@id='accordion']/div/div[3]/h4/a") Button btnManageProjectInfo;
	@FindBy(xpath = "//*[@id='content']/div[6]/table/tbody/tr[6]/td/a") Button btnAddTimesheetWeek3;
	@FindBy(xpath = "//input[@value='Save']") Button btnSaveTimesheet;
	@FindBy(xpath = "(//*[@id='employee_reported_times___date_hours_hours'])[1]") Textbox txtFirstDayOfTimesheet;
	@FindBy(xpath = "//*[@id='comment']") Textbox txtCommentBox;
	@FindBy(xpath = "//span[@class='select2-selection__arrow']") Button btnTimesheetTypeArrow;
	@FindBy(xpath = "//input[@class='select2-search__field']") Textbox txtTimesheetType;
	@FindBy(xpath = "//div[@class='btn btn-primary btn-xs submit-comment']") Button btnOkTimesheetComment;
	@FindBy(xpath = "//*[@id='notification-area']/div") Label lblSaveCommentMessage;
	@FindBy(xpath = "//span[@class='approval-section edit-icon glyphicon glyphicon-pencil']") Button btnEditTimesheetWeek;
	@FindBy(xpath = "//a[@title='Delete']") Button btnDeleteTimesheetWeek;
	@FindBy(xpath = "//*[@id='time_modal']/div/div") Label lblTimesheetModal;
	@FindBy(xpath = "//span[@class='week_total_hours']") Label lblTimesheetTotalHours;
	
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
		btnDeactivateEmployee.syncVisible(2, true);
		btnDeactivateEmployee.click();
	}
	
	public void clickDeactivate(){
		btnDeactivateEmployee.syncVisible(2, true);
		btnDeactivate.click();
	}
	
	public void clickManageProjectInfo() {
		btnManageProjectInfo.syncVisible(2,true);
		btnManageProjectInfo.click();
	}
	
	/**
	 * Verifies the Manage Project Info button is visible on the employee page
	 * @return - True if visible
	 */
	public boolean verifyManageProjectInfo() {
		return btnManageProjectInfo.syncVisible(2,true);
	}
	
	public void clickAddTimesheet3() {
		btnAddTimesheetWeek3.syncVisible(2,true);
		btnAddTimesheetWeek3.click();
	}
	
	/**
	 * Verifies the third week's Add Timesheet button is visible
	 * @return - True if visible
	 */
	public boolean verifyAddTimesheet3() {
		return btnAddTimesheetWeek3.syncVisible(2,true);
	}
	
	public void clickSaveTimesheet() {
		btnSaveTimesheet.syncVisible(2,true);
		btnSaveTimesheet.click();
	}
	
	/**
	 * Verifies the Save Timesheet button is onscreen
	 * @return - True if visible
	 */
	public boolean verifySaveTimesheet() {
		return btnSaveTimesheet.syncVisible(2,true);
	}
	
	/**
	 * Right clicks the first day of a timesheet onscreen
	 * @author andrew.mcgrail
	 */
	public void rightClickFirstDayOfTimesheet() {
		Actions action = driver.actions();
		action.contextClick(driver.findElement(By.xpath("(//*[@id='employee_reported_times___date_hours_hours'])[1]"))).build().perform();
	}
	
	/**
	 * Chooses the "Bench" value for a non-billable timesheet onscreen
	 * @author andrew.mcgrail
	 */
	public void assignTimesheetType() {
		lblTimesheetModal.syncVisible(2,true);
		btnTimesheetTypeArrow.click();
		txtTimesheetType.sendKeys("Bench"+Keys.RETURN);
	}
	
	public boolean verifyCommentBox() {
		return txtCommentBox.syncVisible(2,true);
	}
	
	/**
	 * Populates the first comment box on an employees weekly timesheet
	 * @param comment - The comment to populate the box with
	 * @author andrew.mcgrail
	 */
	public void populateCommentBox(String comment) {
		txtCommentBox.sendKeys(comment);
		btnOkTimesheetComment.click();
	}
	
	/**
	 * Verifies the comment box shows the passed value
	 * @param comment - Comment to be checked for
	 * @return - True if comments match
	 * @author andrew.mcgrail
	 */
	public boolean verifyCommentBox(String comment) {
		return txtCommentBox.getText().equalsIgnoreCase(comment);
	}
	
	/**
	 * Verifies the message for successfully saving a timesheet
	 * @return - True if the save timesheet message is displayed
	 * @author andrew.mcgrail
	 */
	public boolean verifySaveTimesheetMessage() {
		return lblSaveCommentMessage.getText().substring(2,52).equalsIgnoreCase("Reported time for this week was saved successfully");
	}
	
	public void clickEditTimesheet() {
		btnEditTimesheetWeek.syncVisible(2,true);
		btnEditTimesheetWeek.click();
	}
	
	/**
	 * Checks that the passed value is equal to the total hours of the
	 *  the timesheet currently onscreen
	 * @return - True if both times match
	 * @param hours - A String of the hours you want e.g. "0"
	 * @author andrew.mcgrail
	 */
	public boolean verifyTimesheetTotalHours(String hours) {
		return lblTimesheetTotalHours.getText().equalsIgnoreCase(hours);
	}
	
	public void clickDeleteTimesheet() {
		btnDeleteTimesheetWeek.syncEnabled(2,true);
		lblTimesheetModal.syncHidden(2,true);
		btnDeleteTimesheetWeek.click();
	}
}