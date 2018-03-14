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
	
	public void populateCompanyName(String companyName) {
		lblAddCompanyModal.syncVisible(2,true);
		txtCompanyName.sendKeys(companyName);
	}
	
	public void setCompanyLogo(String logoPath) {
		btnCompanyLogo.sendKeys(logoPath);
	}
	
	public void clickCreateCompany() {
		lblAddCompanyModal.syncVisible(2,true);
		btnCreateCompany.click();
	}
	
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
	
	public void updateCompanyLogo(String logoPath) {
		btnChangeLogo.syncVisible(2,true);
		btnChangeLogo.click();
		btnEditCompanyLogo.sendKeys(logoPath);
	}
	
	public void clickUpdateCompany() {
		btnUpdateCompany.click();
	}
	
	public void updateCompanyName(String newCompanyName) {
		txtEditCompanyName.syncVisible(2,true);
		txtEditCompanyName.set(newCompanyName);
	}
	
	public boolean verifyThirdCompanyName(String companyName) {
		return lblThirdCompanyName.getText().equalsIgnoreCase(companyName);
	}
	
	public boolean verifyThirdCompanyLogo(String logoName) {
		return lblThirdCompanyLogo.getAttribute("alt").equalsIgnoreCase(logoName);
	}
}