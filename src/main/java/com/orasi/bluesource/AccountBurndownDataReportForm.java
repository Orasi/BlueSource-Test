package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class AccountBurndownDataReportForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//div[contains(text(),'Select All')]") private Element elmSelectAll;
	@FindBy(xpath = "//input[@name='commit']") private Element elmGenerateReport;
	@FindBy(xpath = "//select[@id='account_select']") private Listbox lstAccountSelect;

	/**
	 * Constructor
	 **/
	public AccountBurndownDataReportForm(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns true if key elements of the form are loaded
	 */
	public boolean verifyFormLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(),driver,elmGenerateReport,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,lstAccountSelect,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,elmSelectAll,5);
	}

	public void clickGenerateReport(){
		if (canInteract(elmGenerateReport))
			elmGenerateReport.click();
	}

	/**
	 * @author David Grayson
	 * @return {@link List<String>} Returns a List of all the accounts that can be selected
	 */
	public List<String> getAllAccounts(){
		ArrayList<String> allAccounts = new ArrayList<>();
		if (canInteract(lstAccountSelect)){
			for(WebElement elm:lstAccountSelect.getOptions()){
				allAccounts.add(elm.getText());
			}
		}
		return allAccounts;
	}

	public void clickSelectAll(){
		if (canInteract(elmSelectAll))
			elmSelectAll.click();
	}

	/**
	 * @author David Grayson
	 * @param elm {@link Element} The element to check
	 * @return {@link Boolean} Returns true if it can be interacted with, throws an error otherwise
	 */
	private boolean canInteract(Element elm){
		return elm.syncEnabled(5) && elm.syncVisible(5);
	}
}