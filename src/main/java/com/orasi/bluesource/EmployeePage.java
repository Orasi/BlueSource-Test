package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
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
	@FindBy(xpath = "//*[@id=\"modal_1\"]/div/div") Label lblModal;
	@FindBy(xpath = "//*[@id=\'employee_require_nonbillable\']") Button btnTimeEntryCheckbox;
	@FindBy(id = "employee_status") Listbox lstStatus;
	@FindBy(xpath = "(//div[@class='form-group modal-footer']/input)[1]") Button btnUpdateEmployee;
	@FindBy(xpath = "(//div[@class='form-group modal-footer']/button)[1]") Button btnClose;
	@FindBy(xpath = "//div[@id='modal_1']/div/div/div/button[@class='close']") Button btnCloseModal;
	@FindBy(xpath = "//a[@href='1']") Link lnkManager;
	

	
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
		btnTimeEntryCheckbox.scrollIntoView();
		btnDeactivateEmployee.syncVisible(2, true);
		btnDeactivateEmployee.click();
	}
	
	public void clickDeactivate(){
		btnDeactivateEmployee.syncVisible(2, true);
		btnDeactivate.click();
	}
	
	public void selectStatus(String strName) {
		btnTimeEntryCheckbox.scrollIntoView();
		lstStatus.syncVisible(2,true);
		lstStatus.select(strName);
	}
	
	public void clickUpdateEmployee() {
		btnTimeEntryCheckbox.scrollIntoView();
		btnUpdateEmployee.syncVisible(2,true);
		btnUpdateEmployee.click();
	}
	
	public void clickClose() {
		btnTimeEntryCheckbox.scrollIntoView();
		btnClose.click();
		btnClose.syncHidden(2,true);
	}
	
	/**
	 * Closes the edit general info modal and waits for the modal to fade.
	 * @author Andrew McGrail
	 */
	public void clickCloseModal() {
		lblModal.syncVisible(2,true);
		btnCloseModal.syncEnabled(2,true);
		btnCloseModal.click();
		lblModal.syncHidden(2,true);
	}
	
	/**
	 * This method check if the edit button beside general info exists
	 * @return boolean - If the edit button exists beside general info
	 * @author Andrew McGrail
	 */
	public boolean verifyEditButton() {
		return driver.findElement(By.xpath("//*[@id='accordion']/div/div")).isDisplayed();
	}
	
	public boolean verifyManager(String name) {
		return lnkManager.getText().equalsIgnoreCase(name);
	}
	
	public boolean verifyEditModal() {
		return lblModal.syncVisible(2);
	}
	
	public boolean verifyEditModalGone() {
		return lblModal.isDisplayed();
	}
}