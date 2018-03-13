package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
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
	@FindBy(xpath = "//*[@id='accordion']/div/div[3]/h4/a") Button btnManageProjectInfo;
	@FindBy(xpath = "//*[@id='content']/div[6]/table/tbody/tr[6]/td/a") Button btnAddTimesheetWeek3;
	@FindBy(xpath = "//input[@value='Save']") Button btnSaveTimesheet;
	@FindBy(xpath = "(//*[@id='employee_reported_times___date_hours_hours'])[1]") Textbox txtFirstDayOfTimesheet;
	@FindBy(xpath = "//*[@id='comment']") Textbox txtCommentBox;
	
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
	
	public boolean verifyManageProjectInfo() {
		return btnManageProjectInfo.syncVisible(2,true);
	}
	
	public void clickAddTimesheet3() {
		btnAddTimesheetWeek3.syncVisible(2,true);
		btnAddTimesheetWeek3.click();
	}
	
	public boolean verifyAddTimesheet3() {
		return btnAddTimesheetWeek3.syncVisible(2,true);
	}
	
	public void clickSaveTimesheet() {
		btnSaveTimesheet.syncVisible(2,true);
		btnSaveTimesheet.click();
	}
	
	public boolean verifySaveTimesheet() {
		return btnSaveTimesheet.syncVisible(2,true);
	}
	
	public void rightClickFirstDatOfTimesheet() {
		Actions action = driver.actions();
		action.contextClick(driver.findElement(By.xpath("(//*[@id='employee_reported_times___date_hours_hours'])[1]"))).build().perform();
	}
	
	public boolean verifyCommentBox() {
		return txtCommentBox.syncVisible(2,true);
	}
}