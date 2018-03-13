package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
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
	@FindBy(xpath = "//*[@id='content']/h1") Label lblEmployeeNameHeader;
	@FindBy(xpath = "//a[contains(text(),'Deactivate Employee')]") Button btnDeactivateEmployee;
	@FindBy(xpath = "//a[contains(text(),'Deactivate')]") Button btnDeactivate;
	@FindBy(xpath = "//*[@id=\"panel_body_1\"]/div/ul/li") Label lblDeactivateWarningMessage;
	@FindBy(xpath = "//*[@id=\"select2-employee_manager_id-container\"]") Textbox txtEmployeeManager;
	@FindBy(xpath = "//*[@id='panel_body_1']/div/ul[1]/li/div") Label lblDeactivateActiveWarnings;
	@FindBy(xpath = "//*[@id='accordion']/div/div[5]/div[1]/a") Button btnManageTimeOff;
	@FindBy(xpath = "//tr[@class='vacation-row']") Label lblFirstTimeOffOnTable;
	@FindBy(xpath = "//td[contains(text(),'Status:')]/../td[2]") Label lblEmployeeStatus;
	@FindBy(xpath = "//tr[@class='vacation-row']/td[3]/span") Label lblFirstTimeOffEndDate;
	
	
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
		btnDeactivate.syncVisible(2, true);
		btnDeactivate.click();
	}
	
	/**
	 * Checks that the employee page you are currently on is for the
	 *  employee you pass
	 * @param fullName - Name of employee you wish to check for
	 * @return - True if the name on the page matchs the passed value
	 */
	public boolean verifyEmployeePage(String fullName) {
		return lblEmployeeNameHeader.getText().equalsIgnoreCase(fullName);
	}
	
	public boolean verifyDeactivateEmployeesButton() {
		return btnDeactivateEmployee.syncVisible(2,true);
	}
	
	public boolean verifyDeactivateButton() {
		return btnDeactivate.syncVisible(2,true);
	}
	
	public boolean verifyEmployeesLoseManagerMessage() {
		return lblDeactivateWarningMessage.syncVisible(2,true);
	}
	
	/**
	 * This method returns the name of the employee that will be
	 *  left without a manager due to the deactivation message
	 * @return - The name of the employee
	 */
	public String getEmployeeLosingmanager() {
		return lblDeactivateWarningMessage.getText().substring(0, lblDeactivateWarningMessage.getText().indexOf(" will have "));
	}
	
	/**
	 * This method checks if the employee page currently brought up
	 *  has a manager listed
	 * @return - True if no manager listed
	 */
	public boolean verifyManagerIsEmpty() {
		return txtEmployeeManager.getAttribute("title").isEmpty();
	}
	
	/**
	 * This method checks if the Deactivate Button is onscreen,
	 *  but cannot be clicked due to a manual warning.
	 * @return - True if Deactivate Button is onscreen but not interactable
	 */
	public boolean verifyInactiveDeactivateButton() {
		try{
			btnDeactivate.click();
		}
		catch(WebDriverException e) {
			// This shows the Button cannot be clicked, if it could it would no longer be visible.
		}
		return btnDeactivate.syncVisible(2,true);
	}
	
	/**
	 * Checks if the error message from attempting to deactivate an employee
	 *  correctly warns about subordinates.
	 * @return - True if the warning message matchs what it should
	 */
	public boolean verifyActiveDeactivateMessage() {
		return lblDeactivateActiveWarnings.getText().substring(lblDeactivateActiveWarnings.getText().indexOf(' ', 
				lblDeactivateActiveWarnings.getText().indexOf(' ')+1)).equalsIgnoreCase(" must be reassigned to another manager.");
	}
	
	/**
	 * This method verifies a message is displayed when the mouse hovers over
	 *  the inactive deactivate button (due to manual warnings)
	 * @return - True if Deactivate button is onscreen but no interactable
	 */
	public boolean verifyDeactiveButtonMessage() {
		Actions action = driver.actions();
		action.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Deactivate')]"))).build().perform();
		return driver.findLabel(By.xpath("//div[@class='tooltip fade top in']")).syncInFrame(2,true);
	}
	
	/**
	 * Checks if the warning message displays the time off
	 *  request that will be deleted.
	 * @return - True if the warning message is correct
	 */
	public boolean verifyEmployeesVacationDeactivation() {
		boolean checker = false;
		if(lblDeactivateWarningMessage.getText().contains(" from ") && lblDeactivateWarningMessage.getText().contains(" will be deleted."))
			checker=true;
		return checker;
	}
	
	public void clickManageTimeOff() {
		btnManageTimeOff.syncVisible(2, true);
		btnManageTimeOff.click();
	}
	
	/**
	 * Verifies there is no time off listed for the employee
	 * @return - True if no time off
	 */
	public boolean verifyNoTimeOff() {
		boolean checker = false;
		try {
			lblFirstTimeOffOnTable.isDisplayed();
		}
		catch(NoSuchElementException e) {
			checker = true;
		}
		return checker;
	}
	
	/**
	 * Checks for a specified status on the employee page
	 *  (Active, Permanent, Contractor)
	 * @param status - String of the status to check for
	 * @return - True if the status matchs
	 */
	public boolean verifyEmployeeStatus(String status) {
		return lblEmployeeStatus.getText().equalsIgnoreCase(status);
	}
	
	/**
	 * Verifies the first time off listed for an employee has
	 *  the correct (passed) end date listed
	 * @param endDate - In form "Month DD, YYYY"
	 * @return - True if the end date on the form matchs the passed
	 */
	public boolean verifyFirstTimeOffEndDate(String endDate) {
		return lblFirstTimeOffEndDate.getText().equalsIgnoreCase(endDate);
	}
}