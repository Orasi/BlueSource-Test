package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class EmployeePage {
	private OrasiDriver driver = null;

	/** Page Elements **/
	@FindBy(xpath = "//tr[1]//a[@class='glyphicon glyphicon-pencil']") Button btnEditFirstProject;
	@FindBy(xpath = "//div[@id='panel_body_1']//table") Webtable tblProjectInfo;
	@FindBy(xpath = "//button[@data-target='#modal_1']") Button btnEditGeneral;
	@FindBy(xpath = "//div//a[contains(text(),'Deactivate Employee')]") Button btnDeactivateEmployee;
	@FindBy(xpath = "//div[@class='panel-heading']//a[contains(text(),'Deactivate')]") Button btnDeactivate;
	@FindBy(xpath = "//select[@id= 'employee_department_id']") private Listbox boxDepartment;
	@FindBy(xpath = "//input[@value='Update Employee']") private Button btnUpdateEmployee;
	@FindBy(xpath = "//div[@class = 'alert alert-success alert-dismissable']") private Element alertUpdate;
	@FindBy(xpath = "//td[@ng-show='showFirstName']/a") private Link lnkFirstEmployee;

	/** Constructor **/
	public EmployeePage(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/** Page Interactions **/
	public void clickFirstEditProjectButton() {
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

		if (strStartDate.equals(tblProjectInfo.getCellData(intProjectRow, intStartDateColumn))) {
			return true;
		} else {
			return false;
		}
	}

	public void editGeneralInfo() {
		PageLoaded.isDomComplete(driver);
		btnEditGeneral.click();

	}

	public void clickDeactivateEmployee() {
		btnDeactivateEmployee.click();
	}

	public void clickDeactivate() {
		btnDeactivate.click();
	}

	public void selectDepartment(String department) {
		boxDepartment.syncVisible(5);
		boxDepartment.select(department);
	}

	public void clickUpdateEmployee() {
		btnUpdateEmployee.click();
	}

	public boolean verifyUpdateAlert() {
		return alertUpdate.isDisplayed() && alertUpdate.getText().contains("Employee updated successfully");
	}

	public void clickFirstEmployee() {
		lnkFirstEmployee.click();
	}
}