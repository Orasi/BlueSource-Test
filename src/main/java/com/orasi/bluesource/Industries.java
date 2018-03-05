package com.orasi.bluesource;


import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Industries {
	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(xpath = "//button[@data-target='#modal_1']") private Button btnAdd;
	@FindBy(xpath = "//tr[7]/td") private Label lblFinancial;
	@FindBy(xpath = "//div[@id='content']/a") private Button btnAddNewIndustry;
	@FindBy(xpath = "//input[@id='industry_name']") private Textbox txtNewIndustryName;
	@FindBy(xpath = "//form[@id='new_industry']/div[3]/input") private Button btnCreateIndustry;
	@FindBy(xpath = "//div[@id='notification-area']/div") private Label lblErrorMessage;
	@FindBy(xpath = "//tr[1]/td/div/a[1]/span") private Button btnEditFirstIndustry;
	@FindBy(xpath = "//tr[3]/td/div/a[1]/span") private Button btnEditThirdIndustry;
	@FindBy(xpath = "//form[@class='edit_industry']/div[3]/input") private Button btnUpdateIndustry;
	@FindBy(xpath = "//table/tbody") private Webtable wtIndustries;
	
	/**Constructor**/
	public Industries(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	
	/**
	 * This method retrieves an industry name from those already created.
	 * @return String - The duplicate Text name of the industry
	 * @author Andrew McGrail
	 */
	public String getIndustryName() {
		lblFinancial.getText();
		return lblFinancial.getText();
	}
	
	/**
	 * This method returns the error message at the top of the industries page
	 * @return String - The error message displayed at the top of the page.
	 * @author Andrew McGrail
	 */
	public String getErrorMessage() {
		lblErrorMessage.syncVisible(2,true);
		return lblErrorMessage.getText().substring(2);
	}
	
	/**
	 * This method takes in a name and sets it into the new industry textbox
	 * @param name - The name set in the new industry textbox
	 * @author Andrew McGrail
	 */
	public void setNewIndustryName(String name) {
		txtNewIndustryName.syncVisible(2,true);
		txtNewIndustryName.jsSet(name);
	}
	
	/**
	 * This method returns the name of the industry before editting it.
	 * @return String - The Industry name before being changed at the Edit screen
	 */
	public String getPreEditName() {
		txtNewIndustryName.syncVisible(2,true);
		return txtNewIndustryName.getText();
	}
	
	/**
	 * This method clicks the edit button for the last row of the Industry table
	 * @author Andrew McGrail
	 */
	public void clickEditLastIndustry() {
		driver.findButton(By.xpath("//tr["+getIndustryRowCount()+"]/td/div/a[1]/span")).syncVisible(2,true);
		driver.findButton(By.xpath("//tr["+getIndustryRowCount()+"]/td/div/a[1]/span")).click();
	}
	
	/**
	 * This method finds the total rows in the Industry table
	 * @return int - The total rows in the Industry table
	 * @author Andrew McGrail
	 */
	public int getIndustryRowCount() {
		wtIndustries.syncVisible(2,true);
		return wtIndustries.getRowCount();
	}
	
	public void clickAddNewIndustry() {
		btnAddNewIndustry.syncVisible(2,true);
		btnAddNewIndustry.click();
	}
	
	public void clickCreateIndustry() {
		btnCreateIndustry.syncVisible(2,true);
		btnCreateIndustry.click();
	}
	
	public void clickEditFirstIndustry() {
		btnEditFirstIndustry.syncVisible(2,true);
		btnEditFirstIndustry.click();
	}
	
	public void clickEditThirdIndustry() {
		btnEditThirdIndustry.syncVisible(2,true);
		btnEditThirdIndustry.click();
	}
	
	public void clickUpdateIndustry() {
		btnUpdateIndustry.syncVisible(2,true);
		btnUpdateIndustry.click();
	}
	
}