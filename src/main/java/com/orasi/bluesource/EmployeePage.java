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
	@FindBy(xpath = "//*[@id=\'accordion\']/div/div[7]/button") Button btnEditGeneral;
	@FindBy(partialLinkText = "Deactivate Employee") Button btnDeactivateEmployee;
	@FindBy(partialLinkText = "Deactivate") Button btnDeactivate;
	@FindBy(xpath = "//*[@id=\"modal_1\"]/div/div") Label lblModal;
	@FindBy(xpath = "//*[@id=\'employee_require_nonbillable\']") Button btnTimeEntryCheckbox;
	@FindBy(id = "employee_status") Listbox lstStatus;
	@FindBy(xpath = "//*[@id=\"edit_employee_256\"]/div[25]/input") Button btnUpdateEmployee;
	@FindBy(xpath = "//*[@id='edit_employee_2']/div[24]/button") Button btnClose;
	@FindBy(xpath = "//div[@id='modal_1']/div/div/div/button") Button btnCloseModal;
	

	
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
	
	/*Closes the edit general info modal and waits for the modal to fade.
	 * 
	 * @author Andrew McGrail
	 */
	public void clickCloseModal() {
		btnCloseModal.syncVisible(2,true);
		lblModal.syncVisible(2,true);
		btnCloseModal.syncVisible(2,true);
		btnCloseModal.click();
		lblModal.syncHidden(2,true);
	}
}