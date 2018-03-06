package com.orasi.bluesource;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;


public class Reporting {
	private OrasiDriver driver = null;
	
		
	/**Page Elements**/
	@FindBy(xpath = "//*[@id='employee_username']") private Textbox txtUsername;
	@FindBy(xpath = "//*[@id='employee_password']") private Textbox txtPassword;
	@FindBy(xpath = "//div[3]/input") private Button btnLogin;
	@FindBy(xpath = "//*[@id='nav-side-menu']/ul/li[3]/a") private Button btnAccountReports;
	@FindBy(xpath = "//li[3]/div/ul/li[5]/a") private Link lnkBurnDownData;
	@FindBy(xpath = "//option[1]") private Button btnFirstAccountListed;
	@FindBy(xpath = "//*[@id='report_type_project']") private Button btnProject;
	@FindBy(xpath = "//div[4]/input") private Button btnGenerateReports;
	@FindBy(xpath = "//tr/td[1]") private Label lbReportAccountName;
	@FindBy(xpath = "//*[@id='welcome']") private Button btnWelcome;
	@FindBy(xpath = "//li[@id='logout']/ul/li[2]/a") private Button btnLogout;
	@FindBy(xpath = "//*[@id='nav-side-menu']/ul/li[4]/a") private Button btnProjectsReports;
	@FindBy(xpath = "//li[4]/div/ul/li[9]/a") private Link lnkBurnDown;
	@FindBy(xpath = "//*[@id='report_level_detail']") private Button btnDetail;
	@FindBy(xpath = "//*[@id=\"project_list\"]/div/div[1]/span/span[1]/span/span[2]") private Button btnAccountDropDown;
	@FindBy(xpath = "//*[@id='select2-account-container']") private Listbox lbAccountSelection;
	@FindBy(xpath = "/html/body/span/span/span[1]/input") private Textbox txtAccountSelection;
	@FindBy(xpath = "//*[@id='project_']/option[1]") private Label lbAccountName;
	@FindBy(xpath = "//tbody/tr[1]/th") private Label lbReportProjectAccountName;
	@FindBy(xpath = "//*[@id='blank_modal']/div/div") private Label lbModal;
	@FindBy(xpath = "//*[@id='report_level_summary']") private Button btnSummary;
	@FindBy(xpath = "//li[3]/div/ul/li[4]/a") private Link lnkAccountReportsBurnDown;
	
	
 	
	/**Constructor**/
	public Reporting(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	public void navigateReports() {
		driver.get("http://10.238.243.127:8080/reporting");
	}
	
	/**
	 * Returns the Account name selected during a Project Burn Down Report
	 * @return String - Account name
	 * @author Andrew McGrail
	 */
	public String chooseAccount() {
		btnAccountDropDown.syncVisible(2,true);
		btnAccountDropDown.click();
		txtAccountSelection.sendKeys("1111222223333344445555LongTestName"+Keys.RETURN);
		lbAccountName.click();
		return lbAccountName.getText().substring(0,34);
	}
	
	/**
	 * This method logs into 'http://10.238.243.127:8080/reporting'
	 * @author Andrew McGrail
	 */
	public void adminLogin() {
		txtUsername.syncVisible(2,true);
		txtUsername.set("company.admin");
		txtPassword.set("123");
		btnLogin.click();
	}
	
	/**
	 * This method retrieves the account name chosen to make the BurnDown
	 * @return String - The account name chosen to create a report
	 * @author Andrew McGrail
	 */
	public String getFirstAccountName() {
		btnFirstAccountListed.syncVisible(2,true);
		return btnFirstAccountListed.getText();
	}
	
	/**
	 * This method retrieves the first account name from the report
	 * @return String - The Account name listed in the BurnDown report.
	 * @author Andrew McGrail
	 */
	public String getReportAccountName() {
		lbReportAccountName.syncVisible(2,true);
		return lbReportAccountName.getText();
	}
	
	/**
	 * This method logs out of the BlueSource Reporting
	 * @author Andrew McGrail
	 */
	public void logout() {
		btnWelcome.syncVisible(2,true);
		btnWelcome.click();
		btnLogout.syncVisible(2,true);
		btnLogout.click();
	}
	
	/**
	 * This method returns the Account name listed on the Project Burn Down Report
	 * @return String - Account name from the Report
	 * @author Andrew McGrail
	 */
	public String getReportProjectAccountName() {
		return lbReportProjectAccountName.getText();
	}
	
	public void clickAccountReports() {
		btnAccountReports.syncVisible(2,true);
		btnAccountReports.click();
	}
	
	public void clickBurnDownData() {
		lnkBurnDownData.syncVisible(2,true);
		lnkBurnDownData.syncEnabled(2,true);
		lnkBurnDownData.click();
	}
	
	public void clickFirstAccountListed() {
		btnFirstAccountListed.syncVisible(2,true);
		btnFirstAccountListed.click();
	}
	
	public void clickProjectButton() {
		btnProject.syncVisible(2,true);
		btnProject.click();
	}
	
	public void clickGenerateReport() {
		btnGenerateReports.syncVisible(2,true);
		btnGenerateReports.click();
	}
	
	public void clickFirstAccountName() {
		btnFirstAccountListed.syncVisible(2,true);
		btnFirstAccountListed.click();
	}
	
	public void clickProjectsReports() {
		btnProjectsReports.syncVisible(2,true);
		btnProjectsReports.click();
	}
	
	public void clickBurnDown() {
		lnkBurnDown.syncVisible(2,true);
		lnkBurnDown.syncEnabled(2,true);
		lnkBurnDown.click();
	}
	
	public void clickDetail() {
		lbModal.syncVisible(2,true);
		btnDetail.syncVisible(2,true);
		btnDetail.syncEnabled(2,true);
		btnDetail.click();
	}
	
	public void clickProjectsGenerateReport() {
		btnLogin.syncVisible(2,true);
		btnLogin.click();
	}
	
	public void clickSummary() {
		lbModal.syncVisible(2,true);
		btnSummary.syncVisible(2,true);
		btnSummary.syncEnabled(2,true);
		btnSummary.click();
	}
	
	public void clickAccountReportsBurnDown() {
		lnkAccountReportsBurnDown.syncVisible(2,true);
		lnkAccountReportsBurnDown.syncEnabled(2,true);
		lnkAccountReportsBurnDown.click();
	}
	
	public void clickAccountReportGenerateReport() {
		btnLogin.syncVisible(2,true);
		btnLogin.click();
	}
}