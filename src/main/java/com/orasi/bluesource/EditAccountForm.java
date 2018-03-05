package com.orasi.bluesource;

import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EditAccountForm {
	private OrasiDriver driver = null;
	/**Page Elements**/
	@FindBy(xpath = "//form[@class='edit_account']//input[@id='account_name']") private Textbox txtAccountName;
	@FindBy(xpath = "//span[@id='select2-account_industry_id-container']") private Element elmIndustrySelect;
	@FindBy(xpath = "//input[@value='Update Account']") private Element elmUpdateAccount;
	@FindBy(xpath = "//span[@class='select2-results']/ul/li") private List<Element> industryOptions;

	/**Constructor**/
	public EditAccountForm(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Test methods**/
	public String testRenameAccount(){
		String newName = txtAccountName.getText()+"test";
		renameAccount(newName);
		return newName;
	}

	public int testChangeIndustry(){
		int selectionNumber;
		String selectionText;
		elmIndustrySelect.syncEnabled(3);
		elmIndustrySelect.click();
		//List<Element> industryOptions = driver.findElements(By.xpath("//span[@class='select2-results']/ul/li"));
		if (industryOptions.size()>0){
			if (industryOptions.get(1).getAttribute("aria-selected").equals("true")) {
				selectionNumber = 2;
			}
			else
				selectionNumber = 1;
		} else {
			TestReporter.log("size = " + industryOptions.size());
			return -1;
		}
		selectionText = industryOptions.get(selectionNumber).getText();
		elmIndustrySelect.click();
		changeIndustry(selectionText);

		return ++selectionNumber;
	}

	/**Page Interactions**/

	public void renameAccount(String strNewName){
		txtAccountName.syncEnabled(3);
		txtAccountName.clear();
		txtAccountName.set(strNewName);
	}

	public void changeIndustry(String strSelection){
		elmIndustrySelect.syncEnabled(3);
		elmIndustrySelect.click();
		for (Element elm:industryOptions){
			if (elm.getText().equals(strSelection) && elm.isDisplayed()){
				elm.click();
				break;
			}
		}
	}

	public void clickUpdateAccount(){
		elmUpdateAccount.click();
	}

	public String getAccountName(){
		return txtAccountName.getText();
	}

	public int getIndustry(){
		elmIndustrySelect.syncEnabled(3);
		elmIndustrySelect.click();
		TestReporter.log("size = " + industryOptions.size());
		for (int i=1;i<industryOptions.size();i++){
			if (industryOptions.get(i).getAttribute("aria-selected").equals("true")) {
				elmIndustrySelect.click();
				return ++i;
			}
		}
		elmIndustrySelect.click();
		return -1;
	}
}
