package com.orasi.bluesource;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;


public class Reporting {
	private OrasiDriver driver = null;
	
		
	/**Page Elements**/
	@FindBy(id = "employee_username") private Textbox txtUsername;
	@FindBy(id = "employee_password") private Textbox txtPassword;
	@FindBy(xpath = "//div[3]/input") private Button btnSubmitLogin;
	@FindBy(xpath = "//*[@id='nav-side-menu']/ul/li[1]/a") private Button btnEmployeeReports;
	@FindBy(xpath = "//li[1]/div/ul/li[1]/a") private Link lnkEmployeeTimeByProject;
	@FindBy(xpath = "//span[1]/span/span[2]") private Button btnSearchEmployeeArrow;
	@FindBy(xpath = "/html/body/span/span/span[1]/input") private Textbox txtEmployeeSearch;
	@FindBy(xpath = "//*[@id='employee_report_list']/div") private Label lblReportEmployeeName;
	@FindBy(xpath = "//*[@id='blank_modal']/div/div") private Label lblModal;
	
	
	/**Constructor**/
	public Reporting(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	/**
	 * This method logs into Blue Source Reporting as Admin
	 * @author Andrew McGrail
	 */
	public void adminLogin() {
		driver.get("http://10.238.243.127:8080/reporting/login");
		txtUsername.set("company.admin");
		txtPassword.set("123");
		btnSubmitLogin.click();
	}
	
	/**
	 * This method searchs the Employee Reports for Time by Project
	 *  for the param name.
	 * @param name - The employee name to be searched
	 * @author Andrew McGrail
	 */
	public void searchEmployee(String name) {
		lblModal.syncVisible(2,true);
		btnSearchEmployeeArrow.syncVisible(2,true);
		btnSearchEmployeeArrow.click();
		txtEmployeeSearch.syncEnabled(2,true);
		txtEmployeeSearch.sendKeys(name+Keys.RETURN);
	}
	
	/**
	 * This method checks the employee report by project time for the
	 *  provided param name.
	 * @param expectedName - The employee name to be verified
	 * @return True if the name displayed matches the param, false if not
	 */
	public boolean verifyFullNameShowing(String expectedName) {
		if(lblReportEmployeeName.getText().substring(0, (lblReportEmployeeName.getText().length())-2).equalsIgnoreCase(expectedName))
			return true;
		return false;
	}
	
	public void clickTimeByProject() {
		btnEmployeeReports.syncVisible(2,true);
		btnEmployeeReports.click();
		lnkEmployeeTimeByProject.syncVisible(2,true);
		lnkEmployeeTimeByProject.click();
	}
}