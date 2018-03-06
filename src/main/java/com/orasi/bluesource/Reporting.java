package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;


public class Reporting {
	private OrasiDriver driver = null;
	
		
	/**Page Elements**/
	@FindBy(id = "employee_username") private Textbox txtUsername;
	@FindBy(id = "employee_password") private Textbox txtPassword;
	@FindBy(xpath = "//div[3]/input") private Button btnSubmitLogin;
	@FindBy(xpath = "/html/body/nav/div/div/a/span/img") private Button btnLogo;
	@FindBy(xpath = "/html/body/nav/div/div/ul/li[2]/a") private Link lnkCompany2;
	@FindBy(id = "welcome") private Button btnWelcome;
	@FindBy(xpath = "//*[@id=\"logout\"]/ul/li[2]/a") private Link lnkLogout;
	@FindBy(xpath = "/html/body/nav/div/div/a") private Link lnkCompanyDropdown;
	
	/**Constructor**/
	public Reporting(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	/**
	 * This method verifys there is a logo listed for the company
	 * @return boolean - If the logo exists
	 * @author Andrew McGrail
	 */
	public boolean checkLogo() {
		if(btnLogo.getAttribute("src").isEmpty())
			return false;
		return true;
	}
	
	/**
	 * This method logs into Blue Source Reporting
	 * @author Andrew McGrail
	 */
	public void adminLogin() {
		driver.get("http://10.238.243.127:8080/reporting/login");
		txtUsername.set("company.admin");
		txtPassword.set("123");
		btnSubmitLogin.click();
	}
	
	public void clickLogo() {
		lnkCompanyDropdown.click();
	}
	
	public void clickCompany2() {
		lnkCompany2.click();
	}
	
	public void logout() {
		btnWelcome.click();
		lnkLogout.syncVisible(2,true);
		lnkLogout.click();
	}
}