package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
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
	@FindBy(xpath = "//*[@id='content']/h1") Label lblEmployeeName;
	@FindBy(xpath = "//*[@id='accordion']/div/div[3]/h4/a") Button btnManageProjectInfo;
	@FindBy(xpath = "//*[@id='date_month']") private Listbox lbTimesheetMonth;
	@FindBy(id = "select_date_range") private Button btnTimesheetGo;
	@FindBy(xpath = "//*[@id='content']/h3") private Label lblTimesheetTitle;
	
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
	
	public boolean verifyEmployeeName(String name) {
		return lblEmployeeName.getText().equalsIgnoreCase(name);
	}
	
	public void clickManageProject() {
		btnManageProjectInfo.syncVisible(2,true);
		btnManageProjectInfo.click();
	}
	
	/**
	 * This method checks if the driver is on the Timesheet Lock page
	 * @return - True if Timesheet Page is loaded
	 * @author Andrew McGrail
	 */
	public boolean verifyTimesheetPage(){
		return PageLoaded.isElementLoaded(this.getClass(), driver, lbTimesheetMonth);	
	}
	
	/**
	 * This method selects the month passed into it for the employee time sheet display month
	 * @param month - String month to be set to
	 * @author Andrew McGrail
	 */
	public void setMonth(String month) {
		lbTimesheetMonth.syncVisible(2,true);
		lbTimesheetMonth.select(month);
	}
	
	public void clickTimesheetGo() {
		btnTimesheetGo.syncVisible(2,true);
		btnTimesheetGo.click();
	}
	
	/**
	 * This method verifies the employee's timesheet page report to be the same
	 * as the month passed in.
	 * @param month - String month to be checked
	 * @return - True if matchs
	 * @author Andrew McGrail
	 */
	public boolean verifyTimesheetTitle(String month) {
		try {
			lblTimesheetTitle.syncVisible(2,true);
			int subStringPoint = lblTimesheetTitle.getText().indexOf('-');
			return lblTimesheetTitle.getText().substring(subStringPoint+2, subStringPoint+2+month.length()).equalsIgnoreCase(month);
		}
		catch(StaleElementReferenceException e) {
			lblTimesheetTitle.syncVisible(2,true);
			int subStringPoint = lblTimesheetTitle.getText().indexOf('-');
			return lblTimesheetTitle.getText().substring(subStringPoint+2, subStringPoint+2+month.length()).equalsIgnoreCase(month);
		}
	}
	
	/**
	 * This method checks an employee's timesheet for the approval status passed in,
	 * returning true if ALL weeks match the passed String
	 * @param status - Status of the employee's timesheet weeks
	 * @return - True if all weeks match the passed string
	 * @author Andrew McGrail
	 */
	public boolean verifyTimesheetLocked(String status) {
		boolean verify = false;
		for(int i=1;i<5;i++) {
			try{
				String checkThis = driver.findLabel(By.xpath("(//div[@class='approval-status'])["+i+"]")).getText();
				if(checkThis.equals(status))
					verify=true;
				else
					verify=false;
				}
			catch(NoSuchElementException e) {
				break;
			}
		}
		return verify;
	}	
}