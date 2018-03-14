package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Admin {
	private OrasiDriver driver = null;
		
	/**Page Elements**/
	@FindBy(xpath = "//div[@data-target='#modal_1']") Button btnAddCompany;
	@FindBy(xpath = "//*[@id='modal_1']/div/div") Label lblAddCompanyModal;
	@FindBy(xpath = "//*[@id='company_name']") Textbox txtCompanyName;
	@FindBy(xpath = "//*[@id='company_logo']") Button btnCompanyLogo;
	@FindBy(xpath = "//input[@value='Create Company']") Button btnCreateCompany;
	@FindBy(xpath = "//*[@id='content']/div[3]/div[2]/table/tbody/tr[3]/td[3]/a/span") Button btnEditThirdCompany;
	@FindBy(xpath = "(//*[@id='company_name'])[2]") Textbox txtEditCompanyName;
	@FindBy(xpath = "//span[@class='btn btn-link change-logo']") Button btnChangeLogo;
	@FindBy(xpath = "(//*[@id='company_logo'])[2]") Button btnEditCompanyLogo;
	@FindBy(xpath = "//input[@value='Update Company']") Button btnUpdateCompany;
	@FindBy(xpath = "//*[@id='content']/div[3]/div[2]/table/tbody/tr[3]/td[2]") Label lblThirdCompanyName;
	@FindBy(xpath = "//*[@id='content']/div[3]/div[2]/table/tbody/tr[3]/td[1]/img") Label lblThirdCompanyLogo;
	
	/**Constructor**/
	public Admin(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	
	public void clickAddCompany() {
		btnAddCompany.syncVisible(2,true);
		btnAddCompany.click();
	}
	
	public boolean verifyAddCompany() {
		return btnAddCompany.syncVisible(2,true);
	}
	
	public boolean verifyAddCompanyModal() {
		return lblAddCompanyModal.syncVisible(2,true);
	}
	
	/**
	 * Sets the company name in the create company modal
	 * @param companyName - Name of company
	 */
	public void populateCompanyName(String companyName) {
		lblAddCompanyModal.syncVisible(2,true);
		txtCompanyName.sendKeys(companyName);
	}
	
	/**
	 * Sets the logo path in the create company modal
	 * @param logoPath - Path on computer to logo
	 */
	public void setCompanyLogo(String logoPath) {
		btnCompanyLogo.sendKeys(logoPath);
	}
	
	public void clickCreateCompany() {
		lblAddCompanyModal.syncVisible(2,true);
		btnCreateCompany.click();
	}
	
	/**
	 * Attempts to check the list of companies for a specified name and logo
	 * @param companyName - Name of company to check for
	 * @param logoName - Name of logo to check for
	 * @return - True if both exist
	 */
	public boolean verifyCreatedCompanyNameLogo(String companyName, String logoName) {
		boolean checker = false;
		if(driver.findLabel(By.xpath("//td[contains(text(),'"+companyName+"')]")).getText().equalsIgnoreCase(companyName) 
				&& driver.findLabel(By.xpath("//img[@alt='"+logoName+"']")).syncVisible(1))
			checker = true;
		return checker;
	}
	
	public void clickEditThirdCompany() {
		btnEditThirdCompany.syncVisible(2,true);
		btnEditThirdCompany.click();
	}
	
	/**
	 * Updates the Edit Company modal to the passed logo
	 * @param logoPath - Path on computer to logo file
	 */
	public void updateCompanyLogo(String logoPath) {
		btnChangeLogo.syncVisible(2,true);
		btnChangeLogo.click();
		btnEditCompanyLogo.sendKeys(logoPath);
	}
	
	public void clickUpdateCompany() {
		btnUpdateCompany.click();
	}
	
	/**
	 * Updates the Edit Company modal to the passed name
	 * @param newCompanyName - Name to be changed to
	 */
	public void updateCompanyName(String newCompanyName) {
		txtEditCompanyName.syncVisible(2,true);
		txtEditCompanyName.set(newCompanyName);
	}
	
	/**
	 * Verifies the name of the third company is the company name passed
	 * @param companyName - Name of the company
	 * @return - True if they match
	 */
	public boolean verifyThirdCompanyName(String companyName) {
		return lblThirdCompanyName.getText().equalsIgnoreCase(companyName);
	}
	
	/**
	 * Verifies the logo of the third company is the logo name passed
	 * @param logoName - Name of the logo
	 * @return - True if they match
	 */
	public boolean verifyThirdCompanyLogo(String logoName) {
		return lblThirdCompanyLogo.getAttribute("alt").equalsIgnoreCase(logoName);
	}
}