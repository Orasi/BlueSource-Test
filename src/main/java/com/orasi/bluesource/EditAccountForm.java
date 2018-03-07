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

	/**
	 * Automatically appends the current name with "test"
	 * @author David Grayson
	 * @return {@link String} Returns the new name of the account
	 */
	public String testRenameAccount(){
		String newName = txtAccountName.getText()+"test";
		renameAccount(newName);
		return newName;
	}

	/**
	 * automatically changes the industry between 2 values for test purposes
	 * @author David Grayson
	 * @return {@link Integer} Returns the number internally associated with the newly selected Industry
	 */
	public int testChangeIndustry(){
		int selectionNumber;
		String selectionText, selectionID;
		elmIndustrySelect.syncEnabled(3);
		elmIndustrySelect.click(); //opens the 'dropdown', elements don't exist if it isn't open
		if (industryOptions.size()>0){
			if (industryOptions.get(1).getAttribute("aria-selected").equals("true"))
				selectionNumber = 2;
			else
				selectionNumber = 1;
		} else {
			return -1;
		}
		selectionText = industryOptions.get(selectionNumber).getText();
		selectionID = industryOptions.get(selectionNumber).getAttribute("id");
		elmIndustrySelect.click(); //closes the 'dropdown'
		changeIndustry(selectionText);

		String[] split = selectionID.split("[-]");

		return Integer.parseInt(split[split.length-1]);
	}

	/**Page Interactions**/

	public void renameAccount(String strNewName){
		txtAccountName.syncEnabled(3);
		txtAccountName.clear();
		txtAccountName.set(strNewName);
	}

	public void changeIndustry(String strSelection){
		elmIndustrySelect.syncEnabled(3);
		elmIndustrySelect.click(); //opens the 'dropdown', elements don't exist if it isn't open
		for (Element elm:industryOptions){
			if (elm.getText().equals(strSelection) && elm.isDisplayed()){
				TestReporter.log(elm.getText());
				elm.click(); //selects the element
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

	/**
	 * @author David Grayson
	 * @return {@link Integer} Returns the number associated with the currently selected industry
	 */
	public int getIndustry(){
		elmIndustrySelect.syncEnabled(3);
		elmIndustrySelect.click(); //opens the 'dropdown', elements don't exist if it isn't open
		TestReporter.log(String.valueOf(industryOptions.size()));
		for (int i = 1; i <= industryOptions.size(); i++){
			if (industryOptions.get(i).getAttribute("aria-selected").equals("true")) {
				String[] split = industryOptions.get(i).getAttribute("id").split("[-]");
				elmIndustrySelect.click(); //closes the 'dropdown'
				return Integer.parseInt(split[split.length-1]);
			}
		}
		elmIndustrySelect.click(); //closes the 'dropdown'
		return -1;
	}
}
